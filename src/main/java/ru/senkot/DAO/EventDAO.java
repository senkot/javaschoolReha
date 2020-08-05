package ru.senkot.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.senkot.entities.Event;
import ru.senkot.entities.Prescription;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EventDAO {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void insertEvent(Event event) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(event);
    }

    public void updateEvent(Event event) {
        Session session = sessionFactory.getCurrentSession();
        session.update(event);
    }

    public Event selectEvent(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Event.class, id);
    }

    public List<Event> selectAllEvents() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("From Event").list();
    }

    public List<Event> selectAllEventsByPatientId(int id) {
        List<Event> eventList = selectAllEvents();

        return eventList.stream().
                filter(event -> event.getPatientId() == id).
                collect(Collectors.toList());
    }

    public void deleteEvent(Event event) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(event);
    }

}
