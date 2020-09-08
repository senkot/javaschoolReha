package ru.senkot.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.senkot.DAO.PatientDAO;
import ru.senkot.DTO.PatientDTO;
import ru.senkot.entities.Event;
import ru.senkot.entities.Patient;
import ru.senkot.entities.Prescription;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PatientService {

    @Autowired
    private PatientDAO patientDAO;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private EventService eventService;


    /**
     * This method returns the list of all existing patients from DB.
     *
     * @return the list of all existing patients found in DB
     */
    @Transactional
    public List<Patient> findAllPatients() {
        return patientDAO.findAllPatients();
    }

    /**
     * This method returns Patient found by it's id from database.
     *
     * @param id id of patient
     * @return instance of Patient class found in database by id
     */
    @Transactional
    public Patient findPatientById(int id) {
        return patientDAO.findPatientById(id);
    }

    /**
     * This method returns Patient found by it's number of insurance from database.
     *
     * @param number number of insurance of patient
     * @return instance of Patient class found in database by number of insurance
     */
    @Transactional
    public Patient findPatientByInsurance(String number) {
        return patientDAO.findPatientByInsurance(number);
    }

    /**
     * This method saves new Patient in DB.
     *
     * @param patient new instance Patient class for saving
     */
    @Transactional
    public void savePatient(Patient patient) {
        patientDAO.savePatient(patient);
    }

    /**
     * This method updates Patient in DB
     *
     * @param patient existing instance of Patient class
     */
    @Transactional
    public void updatePatient(Patient patient) {
        patientDAO.updatePatient(patient);
    }

    /**
     * This method returns existing Patient object with updated data
     * from PatientDTO object for update in DB.
     *
     * @param patientDTO Data Transfer object mapped from post-method on the view-page
     * @return existing Patient object with updated data from PatientDTO object
     */
    @Transactional
    public Patient getPatientFromPatientDTOForUpdate(PatientDTO patientDTO) {
        Patient patient = findPatientById(patientDTO.getPatientId());
        patient.setInsurance(patientDTO.getInsurance());
        patient.setAdditionalInsurance(patientDTO.getAdditionalInsurance());
        patient.setFirstName(patientDTO.getFirstName());
        patient.setLastName(patientDTO.getLastName());
        patient.setSecondName(patientDTO.getSecondName());
        patient.setDateOfBirth(patientDTO.getDateOfBirth());
        patient.setDiagnosis(patientDTO.getDiagnosis());
        return patient;
    }

    /**
     * This method returns new Patient object based data
     * from PatientDTO object for save in DB.
     *
     * @param patientDTO Data Transfer object mapped from post-method on the view-page
     * @return new Patient object based on data from PatientDTO object
     */
    @Transactional
    public Patient patientFromPatientDTOForInsert(PatientDTO patientDTO) {
        return new Patient(
                patientDTO.getInsurance(),
                patientDTO.getAdditionalInsurance(),
                patientDTO.getFirstName(),
                patientDTO.getLastName(),
                patientDTO.getSecondName(),
                patientDTO.getDateOfBirth(),
                patientDTO.getDoctorName(),
                patientDTO.getDiagnosis(),
                patientDTO.getState()
        );
    }

    /**
     * This method updates status of existing patient object in DB
     * to new status from DTO.
     *
     * @param patientDTO Data-transfer object with new status for update
     */
    @Transactional
    public void setPatientStateFromDTO(PatientDTO patientDTO) {
        Patient patient = findPatientById(patientDTO.getPatientId());
        patient.setState(patientDTO.getState());
        updatePatient(patient);
    }

    /**
     * This method changes statuses of all planed events and prescriptions of patient
     * found by patient's id because of patient's discharge.
     *
     * @param patientDTO Data-transfer object with new status of Patient for update
     */
    @Transactional
    public void changeStatusesFromPatientDischarge(PatientDTO patientDTO) {
        if (patientDTO.getState().equals("discharged")) {
            List<Prescription> prescriptions = prescriptionService.findAllPrescriptionsByPatientId(patientDTO.getPatientId());
            for (Prescription prescription : prescriptions) {
                List<Event> events = eventService.findAllPlanedEventsByPrescriptionId(prescription.getId());
                for (Event event : events) {
                    event.setStatus("canceled");
                    event.setCause("Patient discharged");
                }
                if (prescription.getStatus().equals("planed")) {
                    prescription.setStatus("canceled");
                    prescription.setCause("Patient discharged");
                }
            }
        }
    }

}
