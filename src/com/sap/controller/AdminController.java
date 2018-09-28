package com.sap.controller;


import com.sap.model.User;
import com.sap.service.UserService;
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

        User admin = userService.getCurrentUser();

        if (user.getId() != null) {
            User search = userService.getUserByID(user.getId());


                search.setPassword(user.getPassword());
                search.setSsoId(user.getSsoId());
                userService.saveUserAtOwnerTeam(search, admin);
                model.addAttribute("success", "User has been registered successfully");
                return "redirect:/admin";

        }

        userService.saveUserAtOwnerTeam(user, admin);

        model.addAttribute("success", "User has been registered successfully");
        return "redirect:/admin";
    }

}
