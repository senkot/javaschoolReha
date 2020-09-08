package ru.senkot.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.senkot.entities.Event;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EventDAO {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void safeEvent(Event event) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(event);
    }

    public void updateEvent(Event event) {
        Session session = sessionFactory.getCurrentSession();
        session.update(event);
    }

    public Event findEventById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Event.class, id);
    }

    public List<Event> findAllEvents() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("From Event order by date").list();
    }

    public List<Event> findAllEventsByPatientId(int id) {
        List<Event> eventList = findAllEvents();

        return eventList.stream().
                filter(event -> event.getPatientId() == id).
                collect(Collectors.toList());
    }

    public List<Event> findAllEventsByDate(Date date) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select e from Event as e where e.date = :date");
        query.setParameter("date", date);
        return query.list();
    }

    public List<Event> findAllEventsByPrescriptionId(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select e from Event as e where e.prescription.id = :id");
        query.setParameter("id", id);
        return query.list();
    }

    public List<Event> findAllPlanedEventsByPrescriptionId(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select e from Event as e where e.prescription.id = :id and e.status = :status");
        query.setParameter("id", id);
        query.setParameter("status", "planed");
        return query.list();
    }

    public void deleteEvent(Event event) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(event);
    }

}
