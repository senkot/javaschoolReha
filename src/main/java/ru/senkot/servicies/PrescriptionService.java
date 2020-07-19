package ru.senkot.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.senkot.DAO.PrescriptionDAO;
import ru.senkot.entities.Prescription;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionDAO prescriptionDAO;

    @Transactional
    public List<Prescription> selectAllPrescriptionsById(int id) {
        return prescriptionDAO.selectAllPrescriptionsById(id);
    }

    @Transactional
    public void deletePrescription(Prescription prescription) {
        prescriptionDAO.deletePrescription(prescription);
    }

    @Transactional
    public Prescription selectPrescription(int id) {
        return prescriptionDAO.selectPrescription(id);
    }

    @Transactional
    public void updatePrescription(Prescription prescription) {
        prescriptionDAO.updatePrescription(prescription);
    }

    @Transactional
    public void insertPrescription(Prescription prescription) {
        prescriptionDAO.insertPrescription(prescription);
    }
}
