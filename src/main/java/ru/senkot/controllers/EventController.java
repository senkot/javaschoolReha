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
import ru.senkot.entities.Event;
import ru.senkot.exception.IdNotFoundException;
import ru.senkot.messaging.MessageSender;
import ru.senkot.servicies.EventService;
import ru.senkot.servicies.PatientService;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

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
        mav.addObject("events", eventService.selectAllEvents());
        mav.addObject("patients", patientService.selectAllPatients());
        return mav;
    }

    @PostMapping(value = "/event-list")
    public ModelAndView filterEvents(@ModelAttribute("filterEventsDTO") FilterEventsDTO filterEventsDTO) {
        logger.debug("filterEvents on mapping /event-list is executed");

        ModelAndView mav = new ModelAndView();
        mav.setViewName("event-list");
        mav.addObject("events", eventService.selectEventsByDTO(filterEventsDTO));
        mav.addObject("patients", patientService.selectAllPatients());
        return mav;
    }

    @GetMapping(value = "/event")
    public ModelAndView getEventById(@ModelAttribute("id") int id) throws IdNotFoundException {
        logger.debug("getEventById on mapping /event is executed");

        ModelAndView mav = new ModelAndView();
        mav.setViewName("event");
        Event event = eventService.selectEvent(id);
        if (event != null) {
            mav.addObject("event", eventService.selectEvent(id));
        } else {
            throw new IdNotFoundException(id);
        }
        return mav;
    }

    @PostMapping(value = "/event")
    public ModelAndView changeStatus(@ModelAttribute("eventDTO") EventDTO eventDTO) {
        logger.debug("changeStatus on mapping /event is executed");

        ModelAndView mav = new ModelAndView();
        mav.setViewName("event");
        eventService.updateEventStatusFromDTO(eventDTO);
        mav.addObject("event", eventService.selectEvent(eventDTO.getEventId()));
        messageSender.sendMessage("DB updated. Event changed status");
        return mav;
    }

    @GetMapping(value = "/test")
    public ModelAndView testMethod() {
        ModelAndView mav = new ModelAndView("test");
        List<Event> events = eventService.selectAllEventsByDate(new Date(Calendar.getInstance().getTime().getTime()));
        mav.addObject("events", eventService.convertEventsToDTO(events));
        messageSender.sendMessage("DB not updated. Message from test method");
        return mav;
    }
}
