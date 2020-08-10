package ru.senkot.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private static final Logger logger = Logger.getLogger(LoginController.class);

    @GetMapping("/login-page")
    public String showMyLoginPage() {
        logger.debug("showMyLoginPage on mapping /login-page is executed");
        return "login";
    }

    @GetMapping("/access-denied")
    public String showAccessDeniedPage() {
        logger.debug("showAccessDeniedPage on mapping /access-denied is executed");
        return "access-denied";
    }

}
