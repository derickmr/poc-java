package com.sap.controller;

import com.sap.model.*;
import com.sap.service.TeamService;
import com.sap.service.UserOnShiftNotificationService;
import com.sap.service.UserService;
import com.sap.service.impl.UserDayRelationComparator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class MainController {

    @Resource
    UserService userService;

    @Resource
    TeamService teamService;

    @Resource
    UserOnShiftNotificationService userOnShiftNotificationService;

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String homePage(ModelMap model) {

        model.addAttribute("greeting", "Hi, welcome to my site");

        return "welcome";

    }

    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", userService.getCurrentUser());
        return "accessDenied";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @RequestMapping(value = "/userPage")
    public String userPage(ModelMap model) {

        User currentUser = userService.getCurrentUser();
        List<UserDayRelation> userDayRelations = new ArrayList<>(currentUser.getUserDayRelations());
        List<TeamMessage> normalMessages = teamService.getNormalMessages(currentUser.getTeam());
        List<UserOnShiftNotification> shiftMessages = teamService.getNecessityMessages(currentUser.getTeam());
        List<UserDayRelation> userDayRelationsWithMessage = new ArrayList<>();
        Collections.sort(userDayRelations, new UserDayRelationComparator());

        for (UserDayRelation userDayRelation :
                userDayRelations) {
            for (UserOnShiftNotification necessityMessage :
                    shiftMessages) {
                if (userDayRelation.getDay().getDate().isEqual(necessityMessage.getDate()))
                    userDayRelationsWithMessage.add(userDayRelation);
            }
        }

        Set<UserDayRelation> userDayRelationSet = new HashSet<>();
        userDayRelationSet.addAll(userDayRelationsWithMessage);
        userDayRelationsWithMessage.clear();
        userDayRelationsWithMessage.addAll(userDayRelationSet);

        model.addAttribute("user", currentUser);
        model.addAttribute("workDays", userDayRelations);
        model.addAttribute("normalMessages", normalMessages);
        model.addAttribute("shiftMessages", shiftMessages);
        model.addAttribute("userDayRelationsWithMessage", userDayRelationsWithMessage);

        return "userPage";

    }

    @RequestMapping(value = "/newAdmin", method = RequestMethod.GET)
    public String newAdminRegistration(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        return "newadmin";
    }

    @RequestMapping(value = "/newAdmin", method = RequestMethod.POST)
    public String saveAdminRegistration(User user, ModelMap model) {

        if (userService.getUserBySsoId(user.getSsoId()) != null) {
            model.addAttribute("goBack", "/newAdmin");
            return "userAlreadyExists";
        }
        userService.saveAdmin(user);


        model.addAttribute("success", "Admin has been registered successfully");
        return "registrationSuccess";
    }

    @RequestMapping(value = "/showAll")
    public String showAll(ModelMap model) {

        User userAdmin = userService.getCurrentUser();

        List<User> users = userService.getTeamMates(userAdmin);

        model.addAttribute("users", users);

        return "showAll";
    }

}