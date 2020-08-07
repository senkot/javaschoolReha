package ru.senkot.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.senkot.DAO.PrescriptionDAO;
import ru.senkot.DTO.PrescriptionDTO;
import ru.senkot.entities.Event;
import ru.senkot.entities.Prescription;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionDAO prescriptionDAO;

    @Autowired
    private PatientService patientService;

    @Autowired
    private EventService eventService;

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

    @Transactional
    public int getLastInsertedPrescriptionIdForPatient(int id) {
        List<Prescription> prescriptions = prescriptionDAO.selectAllPrescriptionsById(id);
        int lastPrescriptionId = 0;
        for (Prescription p : prescriptions) {
            if (p.getId() > lastPrescriptionId) lastPrescriptionId = p.getId();
        }
        return lastPrescriptionId;
    }

    @Transactional
    public Prescription getPrescriptionFromDTOForUpdate(PrescriptionDTO prescriptionDTO) {
        Prescription prescription = selectPrescription(prescriptionDTO.getPrescriptionId());
        prescription.setRemedyName(prescriptionDTO.getRemedyName());
        prescription.setRemedyType(prescriptionDTO.getRemedyType());
        prescription.setDateStart(prescriptionDTO.getDateOfStart());
        prescription.setDateEnd(prescriptionDTO.getDateOfEnd());
        prescription.setQuantity(prescription.getQuantity());
        return prescription;
    }

    @Transactional
    public Prescription getPrescriptionForInsert (PrescriptionDTO prescriptionDTO) {
        return new Prescription(
                patientService.selectPatient(prescriptionDTO.getPatientId()),
                prescriptionDTO.getRemedyName(),
                prescriptionDTO.getRemedyType(),
                prescriptionDTO.getDateOfStart(),
                prescriptionDTO.getDateOfEnd(),
                prescriptionDTO.getQuantity(),
                "planed"
        );
    }

    @Transactional
    public void updatePrescriptionStatus (PrescriptionDTO prescriptionDTO) {
        Prescription prescription = selectPrescription(prescriptionDTO.getPrescriptionId());
        prescription.setStatus(prescriptionDTO.getStatus());
        if (!prescriptionDTO.getCause().isEmpty() && prescriptionDTO.getCause() != null) {
            prescription.setCause(prescriptionDTO.getCause());
        }
        updatePrescription(prescription);
    }

    // изменение статусов во всех событиях наначения с указанием причины

    @Transactional
    public void setStatusPrescriptionToEvent(PrescriptionDTO prescriptionDTO) {
        if (prescriptionDTO.getStatus().equals("canceled")) {
            List<Event> events = eventService.selectAllPlanedEventsByPrescriptionId(prescriptionDTO.getPrescriptionId());
            for (Event event : events) {
                event.setStatus("canceled");
                event.setCause("Prescription canceled");
                eventService.updateEvent(event);
            }
        }
        if (prescriptionDTO.getStatus().equals("done")) {
            List<Event> events = eventService.selectAllPlanedEventsByPrescriptionId(prescriptionDTO.getPrescriptionId());
            for (Event event : events) {
                event.setStatus("done");
                event.setCause("Prescription done");
                eventService.updateEvent(event);
            }
        }
    }
}
