package com.sap.controller;

import com.sap.model.User;
import com.sap.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class MainController {

    @Resource
    UserService userService;

    @RequestMapping(value = {"/", "/home" }, method = RequestMethod.GET)
    public String homePage(ModelMap model) {

        model.addAttribute("greeting", "Hi, welcome to my site");

        return "welcome";

    }

    @RequestMapping(value="/admin", method = RequestMethod.GET)
    public String adminPage(ModelMap model) {

        model.addAttribute("user", getPrincipal());

        return "admin";
    }

    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage (ModelMap model){
        model.addAttribute("user", getPrincipal());
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

    @RequestMapping(value = "/newUser", method = RequestMethod.GET)
    public String newRegistration (ModelMap model){
        User user = new User ();
        model.addAttribute("user", user);
        return "newuser";
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public String saveRegistration (@Valid User user, BindingResult result, ModelMap model){

        if (result.hasErrors()){
            System.out.println("There are errors");
            return "newuser";
        }

        userService.save(user);

        System.out.println("User id: " + user.getId());
        System.out.println("User password: " + user.getPassword());
        System.out.println("User type: " + user.getType());

        model.addAttribute("success", "User " + user.getId() + " has been registered successfully");
        return "registrationSuccess";
    }

    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

}
