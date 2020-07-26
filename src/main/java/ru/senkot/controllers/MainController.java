package ru.senkot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @GetMapping(value = "/about")
    public ModelAndView getAboutPage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("about");
        return mav;
    }
}
