package com.sap.controller;

import com.sap.model.Team;
import com.sap.model.User;
import com.sap.model.UserType;
import com.sap.service.TeamService;
import com.sap.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class MainController {

    @Resource
    UserService userService;

    @Resource
    TeamService teamService;
	
	@Resource
    NecessityMessageService necessityMessageService;

    @RequestMapping(value = {"/", "/home" }, method = RequestMethod.GET)
    public String homePage(ModelMap model) {

        model.addAttribute("greeting", "Hi, welcome to my site");

        return "welcome";

    }

   


    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage (ModelMap model){
        model.addAttribute("user", userService.getCurrentUser());
        return "accessDenied";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(){
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }



    @RequestMapping(value = "/userPage")
    public String userPage (ModelMap model){

        User user = userService.getCurrentUser();
		List<UserDayRelation> userDayRelations = new ArrayList<>(currentUser.getUserDayRelations());
        List<Message> normalMessages = teamService.getNormalMessages(currentUser.getTeam());
        List<NecessityMessage> shiftMessages = teamService.getNecessityMessages(currentUser.getTeam());
        List<NecessityMessage> necessityMessages;
        List<UserDayRelation> userDayRelationsWithMessage = new ArrayList<>();
        
		necessityMessages = necessityMessageService.deleteMessagesWhichWereAttended(shiftMessages);
		
		for (UserDayRelation userDayRelation :
                userDayRelations) {
            for (NecessityMessage necessityMessage :
                    shiftMessages) {
                if (userDayRelation.getDay().getDate().isEqual(necessityMessage.getDate()))
                    userDayRelationsWithMessage.add(userDayRelation);
            }
        }
		
		model.addAttribute("user", currentUser);
        model.addAttribute("workDays", userDayRelations);
        model.addAttribute("normalMessages", normalMessages);
        model.addAttribute("shiftMessages", necessityMessages);
        model.addAttribute("userDayRelationsWithMessage", userDayRelationsWithMessage);

        return "userPage";

    }


    @RequestMapping(value = "/newAdmin", method = RequestMethod.GET)
    public String newAdminRegistration (ModelMap model){
        User user = new User ();
        model.addAttribute("user", user);
        return "newadmin";
    }

    @RequestMapping(value = "/newAdmin", method = RequestMethod.POST)
    public String saveAdminRegistration (User user, ModelMap model){

        if (userService.getUserBySsoId(user.getSsoId())!=null) {
            model.addAttribute("goBack", "/newAdmin");
            return "userAlreadyExists";
        }
        userService.saveAdmin(user);


        model.addAttribute("success", "Admin has been registered successfully");
        return "registrationSuccess";
    }

    @RequestMapping(value = "/showAll")
    public String showAll (ModelMap model){

        //String ssoId = userService.getPrincipal();

        User userAdmin = userService.getCurrentUser();

        List<User> users = userService.getTeamMates(userAdmin);

        model.addAttribute("users", users);

        return "showAll";
    }
}