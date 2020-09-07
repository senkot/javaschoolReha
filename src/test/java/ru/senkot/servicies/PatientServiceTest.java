package ru.senkot.servicies;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.senkot.DAO.PatientDAO;
import ru.senkot.DTO.PatientDTO;
import ru.senkot.entities.Event;
import ru.senkot.entities.Patient;
import ru.senkot.entities.Prescription;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientDAO patientDAO;

    @Mock
    private PrescriptionService prescriptionService;

    @Mock
    private EventService eventService;

    @Test
    public void selectAllPatients() {
    }

    @Test
    public void selectPatient() {
    }

    @Test
    public void selectPatientByInsurance() {
        int id = 1;
        String name = "Ivan";
        Patient patient = new Patient();
        patient.setId(id);
        patient.setFirstName(name);

        when(patientDAO.selectPatient(id)).thenReturn(patient);

        assertEquals(name, patientService.selectPatient(id).getFirstName());
    }

    @Test
    public void patientFromPatientDTOForUpdate() {
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.set(1980, Calendar.SEPTEMBER, 1);
        Date birthDay = new Date(calendarDate.getTime().getTime());

        PatientDTO patientDTO  = new PatientDTO();
        patientDTO.setPatientId(1);
        patientDTO.setInsurance("insh");
        patientDTO.setAdditionalInsurance("Add insh");
        patientDTO.setFirstName("Ivan");
        patientDTO.setLastName("Ivanov");
        patientDTO.setSecondName("Ivanovich");
        patientDTO.setDateOfBirth(birthDay);
        patientDTO.setDiagnosis("Sadness");

        Patient patient = new Patient();
        patient.setId(1);

        when(patientDAO.selectPatient(patientDTO.getPatientId())).thenReturn(patient);

        assertEquals("Ivanovich", patientService.patientFromPatientDTOForUpdate(patientDTO).getSecondName());
        assertEquals("class ru.senkot.entities.Patient",
                patientService.patientFromPatientDTOForUpdate(patientDTO).getClass().toString());
    }

    @Test
    public void patientFromPatientDTOForInsert() {
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.set(1980, Calendar.SEPTEMBER, 1);
        Date birthDay = new Date(calendarDate.getTime().getTime());

        PatientDTO patientDTO  = new PatientDTO();
        patientDTO.setPatientId(1);
        patientDTO.setInsurance("insh123213dsf");
        patientDTO.setAdditionalInsurance("Add insh");
        patientDTO.setFirstName("Ivan");
        patientDTO.setLastName("Ivanov");
        patientDTO.setSecondName("Ivanovich");
        patientDTO.setDateOfBirth(birthDay);
        patientDTO.setDiagnosis("Sadness");

        assertEquals("insh123213dsf", patientService.patientFromPatientDTOForInsert(patientDTO).getInsurance());
        assertEquals("class ru.senkot.entities.Patient",
                patientService.patientFromPatientDTOForInsert(patientDTO).getClass().toString());
    }

    @Test
    public void setPatientStateFromDTO() {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setPatientId(1);
        patientDTO.setState("discharged");

        Patient patient = new Patient();
        patient.setState("in");

        when(patientDAO.selectPatient(patientDTO.getPatientId())).thenReturn(patient);
        patientService.setPatientStateFromDTO(patientDTO);

        assertEquals("discharged", patient.getState());
    }

    @Test
    public void changeStatusesFromPatientDischarge() {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setPatientId(1);
        patientDTO.setState("discharged");

        Prescription prescription = new Prescription();
        prescription.setId(1);
        prescription.setStatus("planed");

        Event event = new Event();
        event.setStatus("planed");

        List<Prescription> prescriptions = new ArrayList<>();
        prescriptions.add(prescription);
        prescriptions.add(prescription);
        prescriptions.add(prescription);

        List<Event> events = new ArrayList<>();
        events.add(event);
        events.add(event);
        events.add(event);

        when(prescriptionService.selectAllPrescriptionsById(patientDTO.getPatientId())).thenReturn(prescriptions);
        when(eventService.selectAllPlanedEventsByPrescriptionId(prescription.getId())).thenReturn(events);

        patientService.changeStatusesFromPatientDischarge(patientDTO);

        for (Prescription p : prescriptions) {
            assertEquals("canceled", p.getStatus());
            assertEquals("Patient discharged", p.getCause());
        }

        for (Event e : events) {
            assertEquals("canceled", e.getStatus());
            assertEquals("Patient discharged", e.getCause());
        }
    }
}