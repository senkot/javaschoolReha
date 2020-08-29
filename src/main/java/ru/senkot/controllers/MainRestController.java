package ru.senkot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.senkot.DTO.EventStringDTO;
import ru.senkot.entities.Event;
import ru.senkot.servicies.EventService;

import java.sql.Date;
import java.util.List;

@RestController
public class MainRestController {

    @Autowired
    EventService eventService;

    @RequestMapping(value = "/test/{date}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<EventStringDTO> getEventStrings(@PathVariable Date date) {
        List<Event> events = eventService.selectAllEventsByDate(date);
        List<EventStringDTO> eventStrings = eventService.convertEventsToDTO(events);
        return eventStrings;
    }

}