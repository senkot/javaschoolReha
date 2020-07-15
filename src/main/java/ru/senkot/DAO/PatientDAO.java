package ru.senkot.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.senkot.entities.Patient;
import ru.senkot.entities.User;

import java.util.List;

@Repository
public class PatientDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void insertPatient(Patient patient) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(patient);
    }

    public void updatePatient(Patient patient) {
        Session session = sessionFactory.getCurrentSession();
        session.update(patient);
    }

    public Patient selectPatient(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Patient.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Patient> selectAllPatients() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select p from Patient as p").list();
    }

    public void deletePatient(Patient patient) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(patient);
    }

}
