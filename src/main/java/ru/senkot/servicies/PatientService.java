package ru.senkot.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.senkot.DAO.PatientDAO;
import ru.senkot.entities.Patient;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientDAO patientDAO;

    @Transactional
    public List<Patient> selectAllPatients() {
        return patientDAO.selectAllPatients();
    }

    @Transactional
    public Patient selectPatient(int id) {
        return patientDAO.selectPatient(id);
    }

    @Transactional
    public void insertPatient(Patient patient) {
        patientDAO.insertPatient(patient);
    }

    @Transactional
    public void updatePatient(Patient patient) {
        patientDAO.updatePatient(patient);
    }

    @Transactional
    public void deletePatient(Patient patient) {
        patientDAO.deletePatient(patient);
    }
}
