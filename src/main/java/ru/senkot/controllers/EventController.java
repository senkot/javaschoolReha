package ru.senkot.controllers;

import org.apache.log4j.Logger;
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

    private static final Logger logger = Logger.getLogger(EventController.class);

    @Autowired
    private EventService eventService;

    @GetMapping(value = "/event-list")
    public ModelAndView getEvents() {
        logger.debug("getEvents on mapping /event-list is executed");

        ModelAndView mav = new ModelAndView();
        mav.setViewName("event-list");
        mav.addObject("events", eventService.selectAllEvents());
        return mav;
    }

    @GetMapping(value = "/event")
    public ModelAndView getEventById(@ModelAttribute("id") int id) {
        logger.debug("getEventById on mapping /event is executed");

        ModelAndView mav = new ModelAndView();
        mav.setViewName("event");
        mav.addObject("event", eventService.selectEvent(id));
        return mav;
    }

    @PostMapping(value = "/event")
    public ModelAndView changeStatus(@ModelAttribute("eventDTO")EventDTO eventDTO){
        logger.debug("changeStatus on mapping /event is executed");

        ModelAndView mav = new ModelAndView();
        mav.setViewName("event");
        eventService.updateEventStatusFromDTO(eventDTO);
        mav.addObject("event", eventService.selectEvent(eventDTO.getEventId()));
        return mav;
    }
}
