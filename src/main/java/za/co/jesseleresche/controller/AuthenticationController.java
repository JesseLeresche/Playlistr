package za.co.jesseleresche.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import za.co.jesseleresche.model.DeezerAuthentication;
import za.co.jesseleresche.service.AuthenticationService;

import javax.servlet.http.HttpSession;

/**
 * This controller handles the authentication for Deezer, such as
 * generating user tokens and setting them for the session
 */
@Controller
@RequestMapping("/authenticate")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @Value("${deezer.auth.url}")
    private String authUrl;

    @GetMapping("")
    public String generateCode(Model model) {
        return "redirect:" + authUrl;
    }

    @GetMapping("/receiveCode")
    public String authenticate(@RequestParam(value = "error_reason", required = false) String errorReason, @RequestParam(value = "code", required = false) String code, Model model, HttpSession httpSession) {
        String response;
        if (code != null) {
            DeezerAuthentication deezerAuthentication = authenticationService.createDeezerAuthentication(code);
            if (deezerAuthentication != null) {
                response = populateAuthenticationSuccess(model, deezerAuthentication, httpSession);
            } else {
                response = populateAuthenticationError("Could not generate Token", model);
            }
        } else {
            response = populateAuthenticationError(errorReason, model);
        }
        return response;
    }

    private String populateAuthenticationSuccess(Model model, DeezerAuthentication deezerAuthentication, HttpSession httpSession) {
        httpSession.setAttribute("deezerAuthentication", deezerAuthentication);
        return "redirect:/playlists";
    }

    private String populateAuthenticationError(String errorReason, Model model) {
        model.addAttribute("success", false);
        model.addAttribute("errorReason", errorReason);
        return "authenticate";
    }

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
}
