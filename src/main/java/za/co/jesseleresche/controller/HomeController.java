package za.co.jesseleresche.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import za.co.jesseleresche.model.DeezerAuthentication;

import javax.servlet.http.HttpSession;

/**
 * This is the controller for the home page mappings
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("")
    public String home(HttpSession httpSession, Model model) {
        DeezerAuthentication deezerAuthentication = (DeezerAuthentication) httpSession.getAttribute("deezerAuthentication");
        Boolean isUserLoggedIn = false;
        if (deezerAuthentication != null && deezerAuthentication.isValidToken()){
            isUserLoggedIn = true;
        }
        model.addAttribute("isUserLoggedIn", isUserLoggedIn);
        return "home";
    }
}
