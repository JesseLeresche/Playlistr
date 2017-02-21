package za.co.jesseleresche.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * This is the controller for the home page mappings
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("")
    public String home() {
        return "home";
    }
}
