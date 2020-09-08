package ru.senkot.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.senkot.entities.Patient;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PatientDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void savePatient(Patient patient) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(patient);
    }

    public void updatePatient(Patient patient) {
        Session session = sessionFactory.getCurrentSession();
        session.update(patient);
    }

    public Patient findPatientById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Patient.class, id);
    }

    public Patient findPatientByInsurance(String number) {
        List<Patient> patients = findAllPatients().stream()
                .filter(patient -> patient.getInsurance().equals(number))
                .collect(Collectors.toList());
        return patients.size() > 0 ? patients.get(0) : null;
    }

    @SuppressWarnings("unchecked")
    public List<Patient> findAllPatients() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select p from Patient as p order by p.lastName").list();
    }

    public void deletePatient(Patient patient) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(patient);
    }

}
