package ru.senkot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.senkot.entities.User;
import ru.senkot.servicies.UserService;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/about")
    public ModelAndView getAboutPage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("about");
        return mav;
    }

    @GetMapping(value = "/")
    public ModelAndView getIndexPage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");

        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user != null) {
            mav.addObject("user", user);
        }

        return mav;
    }

}
