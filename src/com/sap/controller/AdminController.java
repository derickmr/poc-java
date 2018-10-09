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

    @RequestMapping(value = "/admin")
    public String listUsers (Model model){

        User admin = userService.getCurrentUser();

        model.addAttribute("user", new User());
        model.addAttribute("users", userService.getTeamMates(admin));
        model.addAttribute("admin", admin);
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
    public String saveCalendar (HttpServletRequest request, Model model){

        User currentUser = userService.getCurrentUser();
        LocalDate startDate = LocalDate.parse(request.getParameter("start-date"));
        LocalDate endDate = LocalDate.parse(request.getParameter("end-date"));
        TeamCalendar teamCalendar = new TeamCalendar();
        teamCalendar.setStartDate(startDate);
        teamCalendar.setEndDate(endDate);

        if (!userService.isTeamOwner(currentUser)){
            model.addAttribute("user", currentUser);
            return "accessDenied";
        }

        if (teamCalendarService.verifyIfDateIsPossible (teamCalendar, currentUser.getTeam())) {
            teamCalendar.setTeam(currentUser.getTeam());
            teamCalendarService.createCalendar(teamCalendar);
        }

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

}
