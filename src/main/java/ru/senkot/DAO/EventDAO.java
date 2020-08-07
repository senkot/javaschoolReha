package ru.senkot.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.senkot.entities.Event;

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

    public List<Event> selectAllEventsByPrescriptionId(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select e from Event as e where e.prescription.id = :id");
        query.setParameter("id", id);
        return query.list();
    }

    public List<Event> selectAllPlanedEventsByPrescriptionId(int id) {
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
