package ru.senkot.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.senkot.DAO.EventDAO;
import ru.senkot.entities.Event;
import ru.senkot.entities.Prescription;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventDAO eventDAO;

    @Transactional
    public void insertEvent(Event event) {
        eventDAO.insertEvent(event);
    }

    @Transactional
    public void updateEvent(Event event) {
        eventDAO.updateEvent(event);
    }

    @Transactional
    public Event selectEvent(int id) {
        return eventDAO.selectEvent(id);
    }

    @Transactional
    public List<Event> selectAllEvents() {
        return eventDAO.selectAllEvents();
    }

    @Transactional
    public List<Prescription> selectAllEventsByPatientId(int id) {
        return eventDAO.selectAllEventsByPatientId(id);
    }

    @Transactional
    public void deleteEvent(Event event) {
        eventDAO.deleteEvent(event);
    }



}
