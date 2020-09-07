package ru.senkot.servicies;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.senkot.DAO.PrescriptionDAO;
import ru.senkot.DTO.PrescriptionDTO;
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
public class PrescriptionServiceTest {

    @InjectMocks
    private PrescriptionService prescriptionService;

    @Mock
    private PrescriptionDAO prescriptionDAO;

    @Mock
    private PatientService patientService;

    @Mock
    private EventService eventService;

    @Test
    public void selectAllPrescriptionsById() {
        int id = 1;
        Prescription prescription = new Prescription();
        prescription.setId(id);
        List<Prescription> prescriptions = new ArrayList<>();
        prescriptions.add(prescription);
        prescriptions.add(prescription);
        prescriptions.add(prescription);

        when(prescriptionDAO.selectAllPrescriptionsById(id)).thenReturn(prescriptions);

        assertEquals(id, prescriptionService.selectAllPrescriptionsById(id).get(1).getId());
    }

    @Test
    public void selectPrescription() {
        int id = 1;
        String name = "Aspirin";
        Prescription prescription = new Prescription();
        prescription.setRemedyName(name);
        prescription.setId(id);

        when(prescriptionDAO.selectPrescription(id)).thenReturn(prescription);

        assertEquals(name, prescriptionService.selectPrescription(id).getRemedyName());
    }

    @Test
    public void checkPrescriptionsByPatientId() {
        Prescription prescription = new Prescription();
        prescription.setId(1);
        prescription.setStatus("planed");

        List<Prescription> prescriptions = new ArrayList<>();
        prescriptions.add(prescription);
        prescriptions.add(prescription);
        prescriptions.add(prescription);

        when(eventService.checkPlanedEventsForPrescription(prescription.getId())).thenReturn(true);
        when(prescriptionDAO.selectAllPrescriptionsById(prescription.getId())).thenReturn(prescriptions);

        prescriptionService.checkPrescriptionsByPatientId(prescription.getId());

        for (Prescription p : prescriptions) {
            assertEquals("done", p.getStatus());
        }
    }

    @Test
    public void getLastInsertedPrescriptionIdForPatient() {
        Prescription prescription1 = new Prescription();
        prescription1.setId(1);
        Prescription prescription2 = new Prescription();
        prescription1.setId(2);
        Prescription prescription3 = new Prescription();
        prescription1.setId(3);

        List<Prescription> prescriptions = new ArrayList<>();
        prescriptions.add(prescription1);
        prescriptions.add(prescription2);
        prescriptions.add(prescription3);

        when(prescriptionDAO.selectAllPrescriptionsById(0)).thenReturn(prescriptions);

        assertEquals(3, prescriptionService.getLastInsertedPrescriptionIdForPatient(0));
    }

    @Test
    public void getPrescriptionFromDTOForUpdate() {
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.set(2020, Calendar.SEPTEMBER, 1);
        Date dateStart = new Date(calendarDate.getTime().getTime());
        calendarDate.set(2020, Calendar.SEPTEMBER, 6);
        Date dateEnd = new Date(calendarDate.getTime().getTime());

        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setPrescriptionId(1);
        prescriptionDTO.setRemedyName("Aspirin");
        prescriptionDTO.setRemedyType("pill");
        prescriptionDTO.setDateOfStart(dateStart);
        prescriptionDTO.setDateOfEnd(dateEnd);
        prescriptionDTO.setQuantity(1);

        Prescription prescription = new Prescription();
        prescription.setId(prescriptionDTO.getPrescriptionId());

        when(prescriptionService.selectPrescription(prescriptionDTO.getPrescriptionId()))
                .thenReturn(prescription);

        assertEquals(prescriptionDTO.getRemedyName(),
                prescriptionService.getPrescriptionFromDTOForUpdate(prescriptionDTO).getRemedyName());
        assertEquals("class ru.senkot.entities.Prescription",
                prescriptionService.getPrescriptionFromDTOForUpdate(prescriptionDTO).getClass().toString());
    }

    @Test
    public void getPrescriptionForInsert() {
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.set(2020, Calendar.SEPTEMBER, 1);
        Date dateStart = new Date(calendarDate.getTime().getTime());
        calendarDate.set(2020, Calendar.SEPTEMBER, 6);
        Date dateEnd = new Date(calendarDate.getTime().getTime());

        Patient patient = new Patient();
        patient.setId(1);

        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setPatientId(patient.getId());
        prescriptionDTO.setPrescriptionId(1);
        prescriptionDTO.setRemedyName("Aspirin");
        prescriptionDTO.setRemedyType("pill");
        prescriptionDTO.setDateOfStart(dateStart);
        prescriptionDTO.setDateOfEnd(dateEnd);
        prescriptionDTO.setQuantity(1);

        when(patientService.selectPatient(1)).thenReturn(patient);

        assertEquals(prescriptionDTO.getRemedyName(),
                prescriptionService.getPrescriptionForInsert(prescriptionDTO).getRemedyName());
        assertEquals("class ru.senkot.entities.Prescription",
                prescriptionService.getPrescriptionForInsert(prescriptionDTO).getClass().toString());
    }

    @Test
    public void updatePrescriptionStatus() {
        Prescription prescription = new Prescription();
        prescription.setId(1);
        prescription.setStatus("planed");

        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setPrescriptionId(1);
        prescriptionDTO.setStatus("done");

        when(prescriptionDAO.selectPrescription(prescriptionDTO.getPrescriptionId())).thenReturn(prescription);

        prescriptionService.updatePrescriptionStatus(prescriptionDTO);

        assertEquals("done", prescription.getStatus());
    }

    @Test
    public void setStatusPrescriptionToEvent() {
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setPrescriptionId(1);
        prescriptionDTO.setStatus("done");

        Event event = new Event();
        event.setStatus("planed");

        List<Event> events = new ArrayList<>();
        events.add(event);
        events.add(event);
        events.add(event);

        when(eventService.selectAllPlanedEventsByPrescriptionId(prescriptionDTO.getPrescriptionId()))
                .thenReturn(events);

        prescriptionService.setStatusPrescriptionToEvent(prescriptionDTO);

        for (Event e : events) {
            assertEquals("canceled", e.getStatus());
        }
    }
}