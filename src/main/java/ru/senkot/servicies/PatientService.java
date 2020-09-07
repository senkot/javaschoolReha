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

    @Transactional
    public List<Patient> findAllPatients() {
        return patientDAO.selectAllPatients();
    }

    @Transactional
    public Patient findPatientById(int id) {
        return patientDAO.selectPatient(id);
    }

    @Transactional
    public Patient findPatientByInsurance(String number) {
        return patientDAO.selectPatientByInsurance(number);
    }

    @Transactional
    public void savePatient(Patient patient) {
        patientDAO.insertPatient(patient);
    }

    @Transactional
    public void updatePatient(Patient patient) {
        patientDAO.updatePatient(patient);
    }

    @Transactional
    public Patient patientFromPatientDTOForUpdate(PatientDTO patientDTO) {
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

    @Transactional
    public void setPatientStateFromDTO(PatientDTO patientDTO) {
        Patient patient = findPatientById(patientDTO.getPatientId());
        patient.setState(patientDTO.getState());
        updatePatient(patient);
    }

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
