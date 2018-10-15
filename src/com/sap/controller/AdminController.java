package com.sap.controller;

import com.sap.model.User;
import com.sap.service.UserService;
import com.sap.service.UserDayRelationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;

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
    private MessageService messageService;

    @Resource
    private TeamService teamService;

    @Resource
    private NecessityMessageService necessityMessageService;

    @RequestMapping(value = "/admin")
    public String listUsers (Model model){

        User admin = userService.getCurrentUser();
        List<Message> normalMessages = teamService.getNormalMessages(admin.getTeam());

        model.addAttribute("user", new User());
        model.addAttribute("users", userService.getTeamMates(admin));
        model.addAttribute("admin", admin);
		model.addAttribute("normalMessages", normalMessages);

        return "admin";
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String addUser (@ModelAttribute("user") User user, Model model){

        if (!userService.verifyIfActionCanBeAppliedToUser(user)) {
            model.addAttribute("user", userService.getCurrentUser());
            return "accessDenied";
        }

        if (userService.getUserBySsoId(user.getSsoId())!=null) {
            model.addAttribute("goBack", "admin");
            return "userAlreadyExists";
        }
        userService.saveUserAtOwnerTeam(user, userService.getCurrentUser());

        return "redirect:/admin";

    }

    @RequestMapping(value = "remove/{id}")
    public String removeUser (@PathVariable("id") Integer id, Model model){

        if (!userService.verifyIfActionCanBeAppliedToUser(userService.getUserByID(id))) {
            model.addAttribute("user", userService.getCurrentUser());
            return "accessDenied";
        }


        userService.deleteUserByID(id);


        return "redirect:/admin";
    }

    @RequestMapping(value = "/edit/{id}")
    public String editUser (@PathVariable("id") Integer id, Model model){

        if (!userService.verifyIfActionCanBeAppliedToUser(userService.getUserByID(id))) {
            model.addAttribute("user", userService.getCurrentUser());
            return "accessDenied";
        }

        model.addAttribute("user", userService.getUserByID(id));
        model.addAttribute("users", userService.getTeamMates(userService.getCurrentUser()));



        return "admin";
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public String saveRegistration (@Valid User user, BindingResult result, ModelMap model){

        User currentUser = userService.getCurrentUser();

        if (user.getId() != null) {

            if (!userService.verifyIfActionCanBeAppliedToUser(currentUser)){
                model.addAttribute("user", currentUser);
                return "accessDenied";
            }

            User registeredUser = userService.getUserByID(user.getId());

            if (userService.getUserBySsoId(user.getSsoId())!=null){
                if (userService.getUserBySsoId(user.getSsoId()).getId() != registeredUser.getId()){
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

        if (userService.getUserBySsoId(user.getSsoId()) != null){
            model.addAttribute("goBack", "admin");
            return "userAlreadyExists";
        }

        userService.saveUserAtOwnerTeam(user, currentUser);
		
		List<TeamCalendar> teamCalendars;

        if ((teamCalendars = new ArrayList<>(currentUser.getTeam().getTeamCalendars())) != null){
            userDayRelationService.generateWorkDaysForNewUser(user, teamCalendars);
        }

        return "redirect:/admin";
    }
	
	@RequestMapping(value = "/workDayDetail/{id}")
    public String workDayDetail (@PathVariable("id") Integer id, Model model){

        User currentUser = userService.getCurrentUser();

        if (userService.isTeamOwner(currentUser)){
            model.addAttribute("user", currentUser);
            return "accessDenied";

        }

        UserDayRelation workDay = workDayService.getWorkDayById(id);

        List<String> shiftOptions = new ArrayList<>();

        shiftOptions.add("Day");
        shiftOptions.add("Late");

        model.addAttribute("workDay", workDay);
        model.addAttribute("shiftOptions", shiftOptions);

        return "workDayDetail";
    }
	
	@RequestMapping(value = "/newCalendar")
    public String saveCalendar(HttpServletRequest request, Model model) {

        User currentUser = userService.getCurrentUser();
        LocalDate startDate = LocalDate.parse(request.getParameter("start-date"));
        LocalDate endDate = LocalDate.parse(request.getParameter("end-date"));
        TeamCalendar teamCalendar = new TeamCalendar();
        teamCalendar.setStartDate(startDate);
        teamCalendar.setEndDate(endDate);
        teamCalendar.setTeam(currentUser.getTeam());
        teamCalendar.setNumberOfUsersOnTeamAtCreationOfTheCalendar(currentUser.getTeam().getUsers().size() - 1);

        String usersNeededOnDay = request.getParameter("usersDay");
        String usersNeededOnLate = request.getParameter("usersLate");

        if (usersNeededOnDay.equals(""))
            teamCalendar.setInitialUsersNeededOnDay(0);
        else
            teamCalendar.setInitialUsersNeededOnDay(Integer.parseInt(usersNeededOnDay));

        if (usersNeededOnLate.equals(""))
            teamCalendar.setInitialUsersNeededOnLate(0);
        else
            teamCalendar.setInitialUsersNeededOnLate(Integer.parseInt(usersNeededOnLate));


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
	
	@RequestMapping(value = "/deleteNormalMessage/{id}")
    public String deleteNormalMessage(@PathVariable("id") Integer id) {
        messageService.deleteMessageById(id);
        return "redirect:/calendars";
    }
	
	@RequestMapping(value = "/deleteShiftMessage/{id}")
    public String deleteShiftMessage(@PathVariable("id") Integer id) {
        necessityMessageService.deleteNecessityMessageById(id);
        return "redirect:/calendars";
    }
	
	@RequestMapping(value = "/showDayDetails/{id}")
    public String editCalendar (@PathVariable("id") Integer id, Model model){

        Day day = dayService.getDayByID(id);

        User owner = userService.getCurrentUser();

        if (!userService.isTeamOwner(owner)){
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

        Integer usersOnDay;
        Integer usersOnLate;
        String usersOnDayString = request.getParameter("usersDay");
        String usersOnLateString = request.getParameter("usersLate");
        Day dayToBeEdited = dayService.getDayByID(day.getId());

        if (usersOnDayString.equals(""))
            usersOnDay = 0;
        else
            usersOnDay = Integer.parseInt(usersOnDayString);

        if (usersOnLateString.equals(""))
            usersOnLate = 0;
        else
            usersOnLate = Integer.parseInt(usersOnLateString);

        if (dayToBeEdited.isHoliday() || dayToBeEdited.isWeekend()){
            int result = (dayToBeEdited.getTeamCalendar().getNumberOfUsersOnTeamAtCreationOfTheCalendar().compareTo(usersOnDay + usersOnLate));
            if (result >= 0){
                dayToBeEdited.setUsersNeededOnDay(usersOnDay);
                dayToBeEdited.setUsersNeededOnLate(usersOnLate);
                dayService.save(dayToBeEdited);
            }

        }
        else {
            if (dayToBeEdited.getTeamCalendar().getNumberOfUsersOnTeamAtCreationOfTheCalendar().equals(usersOnDay + usersOnLate)) {
                dayToBeEdited.setUsersNeededOnDay(usersOnDay);
                dayToBeEdited.setUsersNeededOnLate(usersOnLate);
                dayService.save(dayToBeEdited);
            }
        }
        return "redirect:/calendars";
    }
	
	@RequestMapping(value = "/editDay")
    public String editDay(Day day, Model model) {

        User currentUser = userService.getCurrentUser();

        if (!userService.isTeamOwner(currentUser)) {
            model.addAttribute("user", currentUser);
            return "accessDenied";
        }

        Day dayToBeSet = dayService.getDayByID(day.getId());

        dayToBeSet.setHoliday(day.isHoliday());
        dayToBeSet.setWeekend(day.isWeekend());

        if (day.isWeekend() || day.isHoliday()){
            userDayRelationService.removeShiftsOfHolidayOrWeekend(new ArrayList<>(dayToBeSet.getUserDayRelations()));
        }

        dayService.save(dayToBeSet);

        return "redirect:/calendars";

    }
	
	@RequestMapping(value = "/editDay")
    public String editDay (Day day, Model model){

        User currentUser = userService.getCurrentUser();

        if (!userService.isTeamOwner(currentUser)){
            model.addAttribute("user", currentUser);
            return "accessDenied";
        }

        Day dayToBeSet = dayService.getDayByID(day.getId());

        dayToBeSet.setHoliday(day.isHoliday());

        dayToBeSet.setWeekend(day.isWeekend());

        dayService.save(dayToBeSet);

        return "redirect:/calendars";

    }
	
	 @RequestMapping(value = "/editWorkDay")
    public String editWorkDay (Model model, UserDayRelation userDayRelation){

        User currentUser = userService.getCurrentUser();

        if (userService.isTeamOwner(currentUser)){
            model.addAttribute("user", currentUser);
            return "accessDenied";
        }

        UserDayRelation userDayRelationToBeSet = userDayRelationService.getWorkDayById(userDayRelation.getId());

        userDayRelationToBeSet.setShift(userDayRelation.getShift());

        userDayRelationToBeSet.setCanWorkAtHolidayOrWeekend(userDayRelation.isCanWorkAtHolidayOrWeekend());

        userDayRelationService.save(userDayRelationToBeSet);

        return "redirect:/userPage";
    }
	
	@RequestMapping(value = "/calendars")
    public String calendars (Model model){

        List<TeamCalendar> teamCalendars = new ArrayList<>(userService.getCurrentUser().getTeam().getTeamCalendars());
        model.addAttribute("teamCalendar", new TeamCalendar());


        return "calendars";
    }
	
	@RequestMapping(value = "/newNormalMessage")
    public String createNewMessageForTheTeam(HttpServletRequest request, Model model) {
        Message teamMessage = new Message();
        User currentUser = userService.getCurrentUser();
        String message = request.getParameter("message");

        teamMessage.setMessage(message);
        teamMessage.setTeam(currentUser.getTeam());

        messageService.save(teamMessage);

        return "redirect:/admin";

    }
	
	@RequestMapping(value = "/newShiftMessage")
    public String createShiftMessage(HttpServletRequest request) {
        LocalDate date = LocalDate.parse(request.getParameter("day-needed"));
        User currentUser = userService.getCurrentUser();
        List<TeamCalendar> teamCalendars = new ArrayList<>(currentUser.getTeam().getTeamCalendars());
        Day day = teamCalendarService.getDayByDate(date, teamCalendars);
        if (teamCalendarService.verifyIfDateMakesPartOfCalendars(date, teamCalendars)) {
            NecessityMessage shiftMessage = new NecessityMessage();
            shiftMessage.setTeam(currentUser.getTeam());
            shiftMessage.setShift(request.getParameter("shift"));
            shiftMessage.setDate(date);
            shiftMessage.setDay(day);
            if (request.getParameter("shift").equals(Shift.DAY.getShift()))
                shiftMessage.setUsersOnShiftAtDate(day.getUsersOnDay());
            else
                shiftMessage.setUsersOnShiftAtDate(day.getUsersOnLate());
            shiftMessage.createMessageOfNecessity();
            necessityMessageService.save(shiftMessage);
        }
        return "redirect:/calendars";
    }

}
