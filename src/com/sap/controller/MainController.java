package com.sap.controller;

import com.sap.model.Team;
import com.sap.model.User;
import com.sap.model.UserType;
import com.sap.service.TeamService;
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
import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
public class MainController {

    @Resource
    UserService userService;

    @Resource
    TeamService teamService;

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
    public String saveRegistration (@Valid User user, ModelMap model){

        String ssoId = getPrincipal();



        System.out.println("User id: " + user.getId());
        System.out.println("User password: " + user.getPassword());
        System.out.println("User type: " + user.getUserType());

        model.addAttribute("success", "User has been registered successfully");
        return "registrationSuccess";
    }


    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public String deleteUser (ModelMap model){

        User user = new User();
        model.addAttribute("user", user);

        //code below will be at service layer
        String teamOwnerSsoId = getPrincipal();
        User userAdmin = userService.getUserBySsoId(teamOwnerSsoId);
        List<User> users = userAdmin.getTeam().getUsers();
        users.remove(userAdmin);

        model.addAttribute("users", users);

        return "deleteDropdown";

    }

    @RequestMapping(value = "/deleteUserBySsoId", method = RequestMethod.POST)
    public String applyDeleteUser (User user, ModelMap model){

       userService.deleteUserBySsoId(user.getSsoId());

        return "admin";
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.GET)
    public String updateUser (ModelMap model){

        User userToBeModified = new User();
        User userWithChanges = new User();

        String ssoIdAdmin = getPrincipal();
        User userAdmin = userService.getUserBySsoId(ssoIdAdmin);
        List<User> users = userAdmin.getTeam().getUsers();

        users.remove(userAdmin);

        model.addAttribute("users", users);
        model.addAttribute("userToBeModified", userToBeModified);
        model.addAttribute("userWithChanges", userWithChanges);


        return "updateuser";
    }

    @RequestMapping(value = "/updateSelectedUser", method = RequestMethod.POST)
    public String updateUser (User user, ModelMap model){




        return "updateuser";
    }


    @RequestMapping(value = "/userPage")
    public String userPage (ModelMap model){

        User user = new User ();

        return "userPage";

    }


    @RequestMapping(value = "/newAdmin", method = RequestMethod.GET)
    public String newAdminRegistration (ModelMap model){
        User user = new User ();
        model.addAttribute("user", user);
        return "newadmin";
    }

    @RequestMapping(value = "/newAdmin", method = RequestMethod.POST)
    public String saveAdminRegistration (@Valid User user, ModelMap model){


        userService.saveAdmin(user);

        model.addAttribute("success", "Admin has been registered successfully");
        return "registrationSuccess";
    }

    @RequestMapping(value = "/mock")
    public void mockUsers (){

        User user = new User();

        Team team = new Team();

        team.setName("teste");

        teamService.save(team);

        System.out.println("Team id: " + team.getId());

        for (int i = 0; i<3; i++){

            String n = "";

            n += (int) (Math.random() * 10) + 1;

            user.setSsoId(n);
            user.setPassword("12345");
            user.setUserType(UserType.USER.getUserType());
            user.setTeam(teamService.getTeamByID(1));

            System.out.println("User: " + user);

            userService.save(user);

        }


    }

    @RequestMapping(value = "/showAll")
    public String showAll (ModelMap model){

        //code below will be at service layer

        String ssoId = getPrincipal();
        User userAdmin = userService.getUserBySsoId(ssoId);

        List<User> users = userAdmin.getTeam().getUsers();

        users.remove(userAdmin);

        model.addAttribute("users", users);

        return "showAll";
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
