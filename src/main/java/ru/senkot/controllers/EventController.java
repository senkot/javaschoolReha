package ru.senkot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.senkot.DTO.EventDTO;
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

    @GetMapping(value = "/event")
    public ModelAndView getEventById(@ModelAttribute("id") int id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("event");
        mav.addObject("event", eventService.selectEvent(id));
        return mav;
    }

    @PostMapping(value = "/event")
    public ModelAndView changeStatus(@ModelAttribute("eventDTO")EventDTO eventDTO){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("event");
        eventService.updateEventStatusFromDTO(eventDTO);
        mav.addObject("event", eventService.selectEvent(eventDTO.getEventId()));
        return mav;
    }
}
