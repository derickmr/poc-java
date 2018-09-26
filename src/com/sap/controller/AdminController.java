package com.sap.controller;


import com.sap.model.User;
import com.sap.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
public class AdminController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/adminPageTest")
    public String listUsers (Model model){

        User admin = userService.getCurrentUser();
        
        model.addAttribute("user", new User());
        model.addAttribute("users", userService.getTeamMates(admin));
        return "adminPageTest";
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String addUser (@ModelAttribute("user") User user){

        userService.saveUserAtOwnerTeam(user, userService.getCurrentUser());

        return "redirect:/adminPageTest";

    }

    @RequestMapping(value = "remove/{id}")
    public String removeUser (@PathVariable("id") Integer id){

        if (userService.verifyIfActionCanBeAppliedToUser(userService.getUserByID(id))) {

            userService.deleteUserByID(id);

        }
        return "redirect:/adminPageTest";
    }

    @RequestMapping(value = "/edit/{id}")
    public String editUser (@PathVariable("id") Integer id, Model model){
        model.addAttribute("user", userService.getUserByID(id));
        model.addAttribute("users", userService.getTeamMates(userService.getCurrentUser()));

        return "adminPageTest";
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
