package ru.senkot.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.senkot.DTO.EventDTO;
import ru.senkot.DTO.FilterEventsDTO;
import ru.senkot.exception.IdNotFoundException;
import ru.senkot.messaging.MessageSender;
import ru.senkot.servicies.EventService;
import ru.senkot.servicies.PatientService;

@Controller
public class EventController {

    private static final Logger logger = Logger.getLogger(EventController.class);

    @Autowired
    private EventService eventService;

    @Autowired
    private PatientService patientService;

    @Autowired
    MessageSender messageSender;

    @GetMapping(value = "/event-list")
    public ModelAndView getEvents() {
        logger.debug("getEvents on mapping /event-list is executed");

        ModelAndView mav = new ModelAndView();
        mav.setViewName("event-list");
        mav.addObject("events", eventService.findAllEvents());
        mav.addObject("patients", patientService.findAllPatients());
        return mav;
    }

    @PostMapping(value = "/event-list")
    public ModelAndView filterEvents(@ModelAttribute("filterEventsDTO") FilterEventsDTO filterEventsDTO) {
        logger.debug("filterEvents on mapping /event-list is executed");

        ModelAndView mav = new ModelAndView();
        mav.setViewName("event-list");
        mav.addObject("filter", filterEventsDTO);
        mav.addObject("events", eventService.findEventsFromDTO(filterEventsDTO));
        mav.addObject("patients", patientService.findAllPatients());
        return mav;
    }

    @GetMapping(value = "/event")
    public ModelAndView getEventById(@ModelAttribute("id") int id) throws IdNotFoundException {
        logger.debug("getEventById on mapping /event is executed");

        return eventService.getMavForEvent(id);
    }

    @PostMapping(value = "/event")
    public ModelAndView changeStatus(@ModelAttribute("eventDTO") EventDTO eventDTO) {
        logger.debug("changeStatus on mapping /event is executed");

        ModelAndView mav = new ModelAndView();
        mav.setViewName("event");
        eventService.updateEventStatusFromDTO(eventDTO);
        mav.addObject("event", eventService.findEventById(eventDTO.getEventId()));
        messageSender.sendMessage("DB updated. Event changed status");
        return mav;
    }

}
