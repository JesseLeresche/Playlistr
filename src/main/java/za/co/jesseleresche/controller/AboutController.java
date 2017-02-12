package za.co.jesseleresche.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This is the controller for the about page mappings
 */
@Controller
@RequestMapping("/about")
public class AboutController {

    @GetMapping("")
    public String getAbout(){
        return "about";
    }
}
