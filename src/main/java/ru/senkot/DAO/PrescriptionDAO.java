package ru.senkot.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.senkot.entities.Prescription;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PrescriptionDAO {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void insertPrescription(Prescription prescription) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(prescription);
    }

    public void updatePrescription(Prescription prescription) {
        Session session = sessionFactory.getCurrentSession();
        session.update(prescription);
    }

    public Prescription selectPrescription(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Prescription.class, id);
    }

    public List<Prescription> selectAllPrescriptions() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("From Prescription").list();
    }

    public List<Prescription> selectAllPrescriptionsById(int id) {
        Session session = sessionFactory.getCurrentSession();
        List<Prescription> allPrescriptions = session.createQuery("select p FROM Prescription as p").list();
        return allPrescriptions.stream().
                filter(prescription -> prescription.getPatient().getId() == id).
                collect(Collectors.toList());
    }

    public void deletePrescription(Prescription prescription) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(prescription);
    }
}
