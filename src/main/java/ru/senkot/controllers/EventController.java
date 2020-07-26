package ru.senkot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.senkot.DAO.EventDAO;
import ru.senkot.servicies.EventService;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping(value = "/event-list")
    public ModelAndView getEvents() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("event-list");
        mav.addObject("events", eventService.selectAllEvents());
        return mav;
    }

}
