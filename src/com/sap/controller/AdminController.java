package com.sap.controller;

import com.sap.model.*;
import com.sap.service.*;
import com.sap.service.impl.TeamCalendarComparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class AdminController {

    @Resource
    private UserService userService;

    @Resource
    private TeamCalendarService teamCalendarService;

    @Resource
    private DayService dayService;

    @Resource
    private UserDayRelationService userDayRelationService;

    @Resource
    private TeamMessageService teamMessageService;

    @Resource
    private TeamService teamService;

    @Resource
    private UserOnShiftNotificationService userOnShiftNotificationService;

    @RequestMapping(value = "/admin")
    public String listUsers(Model model) {

        User admin = userService.getCurrentUser();
        List<TeamMessage> normalMessages = teamService.getNormalMessages(admin.getTeam());

        model.addAttribute("user", new User());
        model.addAttribute("users", userService.getTeamMates(admin));
        model.addAttribute("admin", admin);
        model.addAttribute("normalMessages", normalMessages);
        return "admin";
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") User user, Model model) {

        if (!userService.verifyIfActionCanBeAppliedToUser(user)) {
            model.addAttribute("user", userService.getCurrentUser());
            return "accessDenied";
        }

        if (userService.getUserBySsoId(user.getSsoId()) != null) {
            model.addAttribute("goBack", "admin");
            return "userAlreadyExists";
        }
        userService.saveUserAtOwnerTeam(user, userService.getCurrentUser());

        return "redirect:/admin";

    }

    @RequestMapping(value = "remove/{id}")
    public String removeUser(@PathVariable("id") Integer id, Model model) {
        User user = userService.getUserByID(id);
        List<UserDayRelation> userDayRelations;
        if (!userService.verifyIfActionCanBeAppliedToUser(userService.getUserByID(id))) {
            model.addAttribute("user", userService.getCurrentUser());
            return "accessDenied";
        }
        userDayRelations = new ArrayList<>(user.getUserDayRelations());
        userDayRelationService.deleteUserDayRelationsFromUser(userDayRelations);
        userService.deleteUserByID(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/workDayDetail/{id}")
    public String workDayDetail(@PathVariable("id") Integer id, Model model) {

        User currentUser = userService.getCurrentUser();
        List<String> shiftOptions = new ArrayList<>();

        shiftOptions.add("Day");
        shiftOptions.add("Late");

        if (userService.isTeamOwner(currentUser)) {
            model.addAttribute("user", currentUser);
            return "accessDenied";
        }

        UserDayRelation workDay = userDayRelationService.getWorkDayById(id);

        model.addAttribute("workDay", workDay);
        model.addAttribute("shiftOptions", shiftOptions);

        return "workDayDetail";
    }

    @RequestMapping(value = "/edit/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model) {

        if (!userService.verifyIfActionCanBeAppliedToUser(userService.getUserByID(id))) {
            model.addAttribute("user", userService.getCurrentUser());
            return "accessDenied";
        }

        model.addAttribute("user", userService.getUserByID(id));
        model.addAttribute("users", userService.getTeamMates(userService.getCurrentUser()));

        return "admin";
    }

    @RequestMapping(value = "/newNormalMessage")
    public String createNewMessageForTheTeam(HttpServletRequest request, Model model) {
        TeamMessage teamMessage = new TeamMessage();
        User currentUser = userService.getCurrentUser();
        String message = request.getParameter("message");

        teamMessage.setMessage(message);
        teamMessage.setTeam(currentUser.getTeam());

        teamMessageService.save(teamMessage);

        return "redirect:/admin";

    }

    @RequestMapping(value = "/newShiftMessage")
    public String createShiftMessage(HttpServletRequest request) {
        LocalDate date = LocalDate.parse(request.getParameter("day-needed"));
        User currentUser = userService.getCurrentUser();
        List<TeamCalendar> teamCalendars = new ArrayList<>(currentUser.getTeam().getTeamCalendars());
        Day day = teamCalendarService.getDayByDate(date, teamCalendars);
        String shift = request.getParameter("shift");

        if (!teamCalendarService.verifyIfDateMakesPartOfCalendars(date, teamCalendars))
            return "redirect:/calendars";

        if (day.getNecessityMessages() != null && !day.getNecessityMessages().isEmpty()) {

            List<UserOnShiftNotification> necessityMessages = new ArrayList<>(day.getNecessityMessages());

            for (UserOnShiftNotification necessityMessage :
                    necessityMessages) {
                if (necessityMessage.getShift().equals(shift)) {
                    updateExistentNecessityMessage(necessityMessage);
                    return "redirect:/calendars";
                }
            }
        }
        if (teamCalendarService.verifyIfDateMakesPartOfCalendars(date, teamCalendars)) {
            UserOnShiftNotification shiftMessage = new UserOnShiftNotification();
            shiftMessage.setTeam(currentUser.getTeam());
            shiftMessage.setShift(shift);
            shiftMessage.setDate(date);
            shiftMessage.setDay(day);
            shiftMessage.setUsersNeedToReachUsersDesired(1);
            if (request.getParameter("shift").equals(Shift.DAY.getShift())) {
                shiftMessage.setUsersOnShiftAtDate(day.getUsersOnDay());
            }
            else {
                shiftMessage.setUsersOnShiftAtDate(day.getUsersOnLate());
            }
            shiftMessage.setUsersDesiredOnShift(shiftMessage.getUsersOnShiftAtDate()+1);
            shiftMessage.createMessageOfNecessity();
            userOnShiftNotificationService.save(shiftMessage);
        }
        return "redirect:/calendars";
    }

    private void updateExistentNecessityMessage (UserOnShiftNotification necessityMessage){
        necessityMessage.setUsersDesiredOnShift(necessityMessage.getUsersDesiredOnShift()+1);
        necessityMessage.setUsersNeedToReachUsersDesired(necessityMessage.getUsersNeedToReachUsersDesired()+1);
        userOnShiftNotificationService.save(necessityMessage);
    }

    @RequestMapping(value = "/newCalendar")
    public String saveCalendar(HttpServletRequest request, Model model) {

        User currentUser = userService.getCurrentUser();
        TeamCalendar teamCalendar = new TeamCalendar();
        teamCalendar = setDatesToCalendar(teamCalendar, request);
        teamCalendar = setUsersNeededPerShiftAtCalendar(teamCalendar, request);
        teamCalendar.setTeam(currentUser.getTeam());
        teamCalendar.setNumberOfUsersOnTeamAtCreationOfTheCalendar(currentUser.getTeam().getUsers().size() - 1);

        if (!userService.isTeamOwner(currentUser)) {
            model.addAttribute("user", currentUser);
            return "accessDenied";
        }
        if (teamCalendarService.verifyIfShiftsArePossible(teamCalendar)) {
            if (teamCalendarService.verifyIfDateIsPossible(teamCalendar, currentUser.getTeam())) {
                teamCalendarService.createCalendar(teamCalendar);
            }
        }
        return "redirect:/calendars";
    }

    private TeamCalendar setDatesToCalendar(TeamCalendar teamCalendar, HttpServletRequest request) {
        LocalDate startDate = LocalDate.parse(request.getParameter("start-date"));
        LocalDate endDate = LocalDate.parse(request.getParameter("end-date"));
        teamCalendar.setStartDate(startDate);
        teamCalendar.setEndDate(endDate);
        return teamCalendar;
    }

    private TeamCalendar setUsersNeededPerShiftAtCalendar(TeamCalendar teamCalendar, HttpServletRequest request) {
        String usersNeededOnDay = request.getParameter("usersDay");
        String usersNeededOnLate = request.getParameter("usersLate");

        teamCalendar.setInitialUsersNeededOnDay(convertStringToValidInteger(usersNeededOnDay));
        teamCalendar.setInitialUsersNeededOnLate(convertStringToValidInteger(usersNeededOnLate));

        return teamCalendar;
    }

    @RequestMapping(value = "/deleteNormalMessage/{id}")
    public String deleteNormalMessage(@PathVariable("id") Integer id) {
        teamMessageService.deleteMessageById(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/deleteShiftMessage/{id}")
    public String deleteShiftMessage(@PathVariable("id") Integer id) {
        userOnShiftNotificationService.deleteNecessityMessageById(id);
        return "redirect:/calendars";
    }

    @RequestMapping(value = "/showDayDetails/{id}")
    public String editCalendar(@PathVariable("id") Integer id, Model model) {

        Day day = dayService.getDayByID(id);
        User owner = userService.getCurrentUser();

        if (!userService.isTeamOwner(owner)) {
            model.addAttribute("user", owner);
            return "accessDenied";
        }

        model.addAttribute("owner", owner);
        model.addAttribute("day", day);
        model.addAttribute("workDays", day.getUserDayRelations());

        return "showDayDetails";

    }

    @RequestMapping(value = "/editDayShift")
    public String editDayShift(Day day, HttpServletRequest request) {

        Day dayToBeEdited = dayService.getDayByID(day.getId());
        Integer usersOnDay = convertStringToValidInteger(request.getParameter("usersDay"));
        Integer usersOnLate = convertStringToValidInteger(request.getParameter("usersLate"));
        Integer usersOnTeam = dayToBeEdited.getUserDayRelations().size() - 1;

        if (verifyIfSumOfShiftsAreValid(dayToBeEdited, usersOnDay + usersOnLate, usersOnTeam)) {
            dayToBeEdited.setUsersNeededOnDay(usersOnDay);
            dayToBeEdited.setUsersNeededOnLate(usersOnLate);
            dayService.save(dayToBeEdited);
            removeShiftsOfOverlyShiftedDays(new ArrayList<>(dayToBeEdited.getUserDayRelations()));
        }
        return "redirect:/calendars";
    }

    private boolean verifyIfSumOfShiftsAreValid(Day day, Integer sumOfShifts, Integer totalNumberOfUsersOnTeam) {
        int result = (totalNumberOfUsersOnTeam.compareTo(sumOfShifts));

        if (result < 0)
            return false;
        if (!isNormalDay(day))
            return true;
        else if (result == 0)
            return true;
        return false;
    }

    private Integer convertStringToValidInteger(String string) {
        if (string.equals(""))
            return 0;
        return Integer.parseInt(string);
    }

    @RequestMapping(value = "/editDay")
    public String editDay(Day day, Model model) {

        User currentUser = userService.getCurrentUser();
        Day dayFromDatabaseWhichWillBeEdited = dayService.getDayByID(day.getId());

        if (!userService.isTeamOwner(currentUser)) {
            model.addAttribute("user", currentUser);
            return "accessDenied";
        }

        if (isDayBeingSetFromHolidayOrWeekendToNormalDay(dayFromDatabaseWhichWillBeEdited, day)) {
            dayFromDatabaseWhichWillBeEdited = setShiftsForDayWhichWasChangedFromHolidayToNormalDay(dayFromDatabaseWhichWillBeEdited);
        }

        dayFromDatabaseWhichWillBeEdited.setHoliday(day.isHoliday());
        dayFromDatabaseWhichWillBeEdited.setWeekend(day.isWeekend());

        if (!isNormalDay(day)) {
            userDayRelationService.removeShiftsOfHolidayOrWeekend(new ArrayList<>(dayFromDatabaseWhichWillBeEdited.getUserDayRelations()));
        }

        dayService.save(dayFromDatabaseWhichWillBeEdited);

        return "redirect:/calendars";

    }

    private void removeShiftsOfOverlyShiftedDays(List<UserDayRelation> userDayRelations) {
        Day day = userDayRelations.get(0).getDay();
        int i = 0;
        int size = userDayRelations.size();

        while (i < size && day.getUsersOnDay().compareTo(day.getUsersNeededOnDay()) > 0) {
            UserDayRelation userDayRelation = userDayRelations.get(i);
            if (userDayRelation.getShift().equals(Shift.DAY.getShift()) && (userDayRelation.getDesiredOriginalShift().equals(Shift.ANY.getShift()))) {
                userDayRelationService.changeShift(userDayRelation, Shift.LATE.getShift());
            }
            i++;
        }

        i = 0;

        while (i < size && day.getUsersOnDay().compareTo(day.getUsersNeededOnDay()) > 0) {
            UserDayRelation userDayRelation = userDayRelations.get(i);
            if (userDayRelation.getShift().equals(Shift.DAY.getShift()))
                userDayRelationService.removeShift(userDayRelation);
            i++;
        }

        i = 0;

        while (i < size && day.getUsersOnLate().compareTo(day.getUsersNeededOnLate()) > 0) {
            UserDayRelation userDayRelation = userDayRelations.get(i);
            if (userDayRelation.getShift().equals(Shift.LATE.getShift()) && (userDayRelation.getDesiredOriginalShift().equals(Shift.ANY.getShift()))) {
                userDayRelationService.changeShift(userDayRelation, Shift.DAY.getShift());
            }
            i++;
        }

        i = 0;

        while (i < size && day.getUsersOnLate().compareTo(day.getUsersNeededOnLate()) > 0) {
            UserDayRelation userDayRelation = userDayRelations.get(i);
            if (userDayRelation.getShift().equals(Shift.LATE.getShift()))
                userDayRelationService.removeShift(userDayRelation);
            i++;
        }
    }

    private boolean isDayBeingSetFromHolidayOrWeekendToNormalDay(Day dayWithPreviousConfigurations, Day dayWithAfterConfigurations) {
        if (!isNormalDay(dayWithPreviousConfigurations) && isNormalDay(dayWithAfterConfigurations))
            return true;
        return false;
    }

    private boolean isNormalDay(Day day) {
        return (!day.isWeekend() && !day.isHoliday());
    }

    private Day setShiftsForDayWhichWasChangedFromHolidayToNormalDay(Day day) {
        Integer numberOfUsersOnTeam = getNumberOfUsersOnTeam(day.getTeamCalendar().getTeam());
        if (day.getUsersNeededOnDay() > 0) {
            day.setUsersNeededOnLate(numberOfUsersOnTeam - day.getUsersNeededOnDay());
        } else if (day.getUsersNeededOnLate() > 0) {
            day.setUsersNeededOnDay(numberOfUsersOnTeam - day.getUsersNeededOnLate());
        } else {
            day.setUsersNeededOnDay(numberOfUsersOnTeam / 2);
            day.setUsersNeededOnLate(numberOfUsersOnTeam - day.getUsersNeededOnDay());
        }
        return day;
    }

    private Integer getNumberOfUsersOnTeam(Team team) {
        return team.getUsers().size() - 1;
    }

    @RequestMapping(value = "/setShiftFromHomePage/{id}")
    public String setShiftFromHomePage (@PathVariable("id") Integer id, Model model, HttpServletRequest request){

        User currentUser = userService.getCurrentUser();
        List<UserDayRelation> userDayRelations = new ArrayList<>();
        String shiftBeforeModifications;
        String desiredShift = request.getParameter("shift");
        String canWorkString = request.getParameter("canWork");
        boolean canWork = true;

        if (canWorkString == null)
            canWork = false;

        if (userService.isTeamOwner(currentUser)) {
            model.addAttribute("user", currentUser);
            return "accessDenied";
        }
        UserDayRelation userDayRelationFromDatabase = userDayRelationService.getWorkDayById(id);
        shiftBeforeModifications = userDayRelationFromDatabase.getShift();

        userDayRelationFromDatabase.setDesiredOriginalShift(desiredShift);
        userDayRelationFromDatabase.setCanWorkAtHolidayOrWeekend(canWork);
        userDayRelations.add(userDayRelationFromDatabase);

        if (desiredShift.equals(shiftBeforeModifications)){
            userDayRelationService.save(userDayRelationFromDatabase);
            return "redirect:/userPage";
        }

        if (canUserWorkAtDay(userDayRelationFromDatabase, userDayRelationFromDatabase.getDay())) {
            userDayRelationService.changeShift(userDayRelationFromDatabase, userDayRelationFromDatabase.getDesiredOriginalShift());
            if (!shiftBeforeModifications.equals(userDayRelationFromDatabase.getShift()))
                userOnShiftNotificationService.deleteMessagesWhichWereAttended(new ArrayList<>(userDayRelationFromDatabase.getDay().getNecessityMessages()));
        }

        userDayRelationService.removeShiftsOfHolidayOrWeekend(userDayRelations);

        return "redirect:/userPage";
    }

    @RequestMapping(value = "/editWorkDay")
    public String editWorkDay(Model model, UserDayRelation userDayRelationOnlyWithShiftAndId) {

        User currentUser = userService.getCurrentUser();
        List<UserDayRelation> userDayRelations = new ArrayList<>();
        String shiftBeforeModifications;

        if (userService.isTeamOwner(currentUser)) {
            model.addAttribute("user", currentUser);
            return "accessDenied";
        }
        UserDayRelation userDayRelationFromDatabase = userDayRelationService.getWorkDayById(userDayRelationOnlyWithShiftAndId.getId());
        shiftBeforeModifications = userDayRelationFromDatabase.getShift();

        userDayRelationFromDatabase = setUserDayRelationShiftAndAvailability(userDayRelationOnlyWithShiftAndId);
        userDayRelations.add(userDayRelationFromDatabase);

        if (userDayRelationOnlyWithShiftAndId.getShift().equals(shiftBeforeModifications)){
            userDayRelationService.save(userDayRelationFromDatabase);
            return "redirect:/userPage";
        }

        if (canUserWorkAtDay(userDayRelationFromDatabase, userDayRelationFromDatabase.getDay())) {
            userDayRelationService.changeShift(userDayRelationFromDatabase, userDayRelationFromDatabase.getDesiredOriginalShift());
            if (!shiftBeforeModifications.equals(userDayRelationFromDatabase.getShift()))
                userOnShiftNotificationService.deleteMessagesWhichWereAttended(new ArrayList<>(userDayRelationFromDatabase.getDay().getNecessityMessages()));
        }

        userDayRelationService.removeShiftsOfHolidayOrWeekend(userDayRelations);
        return "redirect:/userPage";
    }

    private UserDayRelation setUserDayRelationShiftAndAvailability(UserDayRelation userDayRelationOnlyWithShiftAndId) {
        UserDayRelation userDayRelationFromDatabase = userDayRelationService.getWorkDayById(userDayRelationOnlyWithShiftAndId.getId());
        userDayRelationFromDatabase.setDesiredOriginalShift(userDayRelationOnlyWithShiftAndId.getShift());
        userDayRelationFromDatabase.setCanWorkAtHolidayOrWeekend(userDayRelationOnlyWithShiftAndId.isCanWorkAtHolidayOrWeekend());
        return userDayRelationFromDatabase;
    }

    private boolean canUserWorkAtDay(UserDayRelation userDayRelation, Day day) {
        if (!day.isHoliday() && !day.isWeekend())
            return true;
        else if (userDayRelation.isCanWorkAtHolidayOrWeekend())
            return true;
        return false;
    }

    @RequestMapping(value = "/calendars")
    public String calendars(Model model) {

        User currentUser = userService.getCurrentUser();

        List<TeamCalendar> teamCalendars = new ArrayList<>(userService.getCurrentUser().getTeam().getTeamCalendars());
        List<UserOnShiftNotification> shiftMessages = teamService.getNecessityMessages(currentUser.getTeam());
        Collections.sort(teamCalendars, new TeamCalendarComparator());

        model.addAttribute("calendars", teamCalendars);
        model.addAttribute("shiftMessages", shiftMessages);

        return "calendars";
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public String saveRegistration(@Valid User user, BindingResult result, ModelMap model) {

        User currentUser = userService.getCurrentUser();

        if (user.getId() != null) {

            if (!userService.verifyIfActionCanBeAppliedToUser(currentUser)) {
                model.addAttribute("user", currentUser);
                return "accessDenied";
            }

            User registeredUser = userService.getUserByID(user.getId());

            if (userService.getUserBySsoId(user.getSsoId()) != null) {
                Integer userId = userService.getUserBySsoId(user.getSsoId()).getId();
                Integer registeredUserId = registeredUser.getId();
                if (!registeredUserId.equals(userId)) {
                    model.addAttribute("goBack", "admin");
                    return "userAlreadyExists";

                }
            }

            registeredUser.setPassword(user.getPassword());
            registeredUser.setSsoId(user.getSsoId());
            userService.saveUserAtOwnerTeam(registeredUser, currentUser);
            return "redirect:/admin";

        }

        if (!userService.isTeamOwner(currentUser)) {
            model.addAttribute("user", userService.getCurrentUser());
            return "accessDenied";
        }

        if (userService.getUserBySsoId(user.getSsoId()) != null) {
            model.addAttribute("goBack", "admin");
            return "userAlreadyExists";
        }

        userService.saveUserAtOwnerTeam(user, currentUser);

        List<TeamCalendar> teamCalendars;
        teamCalendars = new ArrayList<>(currentUser.getTeam().getTeamCalendars());

        if (!teamCalendars.isEmpty()) {
            userDayRelationService.generateWorkDaysForNewUser(user, teamCalendars);
        }

        return "redirect:/admin";
    }

}