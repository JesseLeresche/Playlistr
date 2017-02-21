package za.co.jesseleresche.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import za.co.jesseleresche.model.User;
import za.co.jesseleresche.service.UserService;

import java.security.Principal;

/**
 * This is the controller for the user page mappings
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @GetMapping("/me")
    public String getUser(Model model) {
        User user = userService.getUser();
        model.addAttribute("user", user);
        return "user";
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
