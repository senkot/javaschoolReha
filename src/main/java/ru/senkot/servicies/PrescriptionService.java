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

    /**
     * This method returns list of Prescriptions found by ID of the patient in database.
     *
     * @param id - the patient's id
     * @return list of Prescriptions
     */
    @Transactional
    public List<Prescription> findAllPrescriptionsByPatientId(int id) {
        return prescriptionDAO.findAllPrescriptionsByPatientId(id);
    }

    /**
     * This method returns Prescription found by it's id from database.
     *
     * @param id id of prescription
     * @return instance of Prescription class found in database by id
     */
    @Transactional
    public Prescription findPrescriptionById(int id) {
        return prescriptionDAO.findPrescriptionById(id);
    }

    /**
     * This method updates Prescription in DB
     *
     * @param prescription existing instance of Prescription class
     */
    @Transactional
    public void updatePrescription(Prescription prescription) {
        prescriptionDAO.updatePrescription(prescription);
    }

    /**
     * This method saves new Prescription in DB
     *
     * @param prescription new instance of Prescription class for saving
     */
    @Transactional
    public void savePrescription(Prescription prescription) {
        prescriptionDAO.savePrescription(prescription);
    }

    /**
     * This method checks all prescriptions found by id of existing patient from DB.
     * If prescription hasn't got any planed event, it will change it's status to "done".
     *
     * @param id id of the patient
     */
    @Transactional
    public void checkPrescriptionsByPatientId(int id) {
        List<Prescription> prescriptions = findAllPrescriptionsByPatientId(id);
        for (Prescription prescription : prescriptions) {
            if (prescription.getStatus().equals("planed")
                    && eventService.checkPlanedEventsForPrescription(prescription.getId())) {
                prescription.setStatus("done");
            }
        }
    }

    /**
     * This method returns the id of last saved prescription in DB found by patient's id.
     *
     * @param id id of the patient
     * @return
     */
    @Transactional
    public int getLastInsertedPrescriptionIdForPatient(int id) {
        List<Prescription> prescriptions = prescriptionDAO.findAllPrescriptionsByPatientId(id);
        int lastPrescriptionId = 0;
        for (Prescription p : prescriptions) {
            if (p.getId() > lastPrescriptionId) lastPrescriptionId = p.getId();
        }
        return lastPrescriptionId;
    }

    /**
     * This method returns existing Prescription object with updated data
     * from PrescriptionDTO object for update in DB.
     *
     * @param prescriptionDTO Data Transfer object mapped from post-method on the view-page
     * @return existing Prescription object with updated data from PrescriptionDTO object
     */
    @Transactional
    public Prescription getPrescriptionFromDTOForUpdate(PrescriptionDTO prescriptionDTO) {
        Prescription prescription = findPrescriptionById(prescriptionDTO.getPrescriptionId());
        prescription.setRemedyName(prescriptionDTO.getRemedyName());
        prescription.setRemedyType(prescriptionDTO.getRemedyType());
        prescription.setDateStart(prescriptionDTO.getDateOfStart());
        prescription.setDateEnd(prescriptionDTO.getDateOfEnd());
        prescription.setQuantity(prescription.getQuantity());
        prescription.setStatus("planed");
        return prescription;
    }

    /**
     * This method returns new Prescription object based on data
     * from PrescriptionDTO object for save in DB.
     *
     * @param prescriptionDTO Data Transfer object mapped from post-method on the view-page
     * @return new Prescription object based on data from PrescriptionDTO object
     */
    @Transactional
    public Prescription getPrescriptionForInsert(PrescriptionDTO prescriptionDTO) {
        return new Prescription(
                patientService.findPatientById(prescriptionDTO.getPatientId()),
                prescriptionDTO.getRemedyName(),
                prescriptionDTO.getRemedyType(),
                prescriptionDTO.getDateOfStart(),
                prescriptionDTO.getDateOfEnd(),
                prescriptionDTO.getQuantity(),
                "planed"
        );
    }

    /**
     * This method updates status of existing Prescription in DB
     * with data from PrescriptionDTO object.
     *
     * @param prescriptionDTO Data-transfer object contains data for update existing Prescription in DB in it's id
     */
    @Transactional
    public void updatePrescriptionStatus(PrescriptionDTO prescriptionDTO) {
        Prescription prescription = findPrescriptionById(prescriptionDTO.getPrescriptionId());
        prescription.setStatus(prescriptionDTO.getStatus());
        if (prescriptionDTO.getCause() != null && !prescriptionDTO.getCause().isEmpty()) {
            prescription.setCause(prescriptionDTO.getCause());
        }
        updatePrescription(prescription);
    }

    /**
     * This method changes statuses in all planed events found by id of existing prescription in DB.
     *
     * @param prescriptionDTO object contains new status for planed events
     */
    @Transactional
    public void setStatusPrescriptionToEvent(PrescriptionDTO prescriptionDTO) {
        if (prescriptionDTO.getStatus().equals("canceled")) {
            List<Event> events = eventService.findAllPlanedEventsByPrescriptionId(prescriptionDTO.getPrescriptionId());
            for (Event event : events) {
                event.setStatus("canceled");
                event.setCause("Prescription canceled");
                eventService.updateEvent(event);
            }
        }
        if (prescriptionDTO.getStatus().equals("done")) {
            List<Event> events = eventService.findAllPlanedEventsByPrescriptionId(prescriptionDTO.getPrescriptionId());
            for (Event event : events) {
                event.setStatus("canceled");
                event.setCause("Prescription done");
                eventService.updateEvent(event);
            }
        }
    }
}
