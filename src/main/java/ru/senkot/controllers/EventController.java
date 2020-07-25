package ru.senkot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EventController {

    @GetMapping(value = "event-list")
    public ModelAndView getEvents() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("event-list");

        return mav;
    }

}
