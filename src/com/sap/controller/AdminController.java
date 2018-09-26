package com.sap.controller;


import com.sap.model.User;
import com.sap.service.UserService;
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
        model.addAttribute("user", new User());
        model.addAttribute("users", userService.getAllUsers());
        return "adminPageTest";
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String addUser (@ModelAttribute("user") User user){

        userService.saveUser(user);

        return "redirect:/adminPageTest";

    }

    @RequestMapping(value = "remove/{id}")
    public String removeUser (@PathVariable("id") Integer id){

        userService.deleteUserByID(id);

        return "redirect:/adminPageTest";
    }

    @RequestMapping(value = "/edit/{id}")
    public String editUser (@PathVariable("id") Integer id, Model model){
        model.addAttribute("user", userService.getUserByID(id));
        model.addAttribute("users", userService.getAllUsers());

        return "adminPageTest";
    }
}
