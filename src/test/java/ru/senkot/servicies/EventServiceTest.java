package ru.senkot.servicies;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.senkot.DAO.EventDAO;
import ru.senkot.DTO.EventDTO;
import ru.senkot.DTO.FilterEventsDTO;
import ru.senkot.DTO.PrescriptionDTO;
import ru.senkot.entities.Event;
import ru.senkot.entities.Patient;
import ru.senkot.entities.Prescription;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventDAO eventDAO;

    @Mock
    private PatientService patientService;

    @Test
    public void selectEvent() {
        int id = 1;
        String name = "Aspirin";
        Event event = new Event();
        event.setId(id);
        event.setRemedyName(name);
        when(eventDAO.findEventById(id)).thenReturn(event);
        String remedyName = eventService.findEventById(id).getRemedyName();

        assertEquals(name, remedyName);
    }

    @Test
    public void selectAllEvents() {
        List<Event> events = new ArrayList<>();
        events.add(new Event());
        events.add(new Event());
        events.add(new Event());
        when(eventDAO.findAllEvents()).thenReturn(events);
        String className = eventService.findAllEvents().get(0).getClass().toString();

        assertEquals(eventDAO.findAllEvents().get(0).getClass().toString(), className);
    }

    @Test
    public void selectAllEventsByDate() {
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.set(2020, Calendar.SEPTEMBER, 6);
        Date date = new Date(calendarDate.getTime().getTime());
        Event event = new Event();
        event.setDate(date);
        List<Event> events = new ArrayList<>();
        events.add(event);
        events.add(event);
        events.add(event);
        when(eventDAO.findAllEventsByDate(date)).thenReturn(events);
        Date testDate = eventService.findAllEventsByDate(date).get(0).getDate();

        assertEquals(testDate, date);
    }

    @Test
    public void convertEventsToDTO() {
        Event event = new Event();
        Patient patient = new Patient();
        patient.setSecondName("Solncev");
        patient.setFirstName("Ivan");
        patient.setId(1);

        Calendar calendarDate = Calendar.getInstance();
        calendarDate.set(2020, Calendar.SEPTEMBER, 6);
        Date date = new Date(calendarDate.getTime().getTime());

        event.setDate(date);
        event.setTime("morning");
        event.setStatus("planed");
        event.setRemedyName("Aspirin");
        event.setRemedyType("pill");
        event.setQuantity(1);
        event.setPatientId(1);

        List<Event> eventList = new ArrayList<>();
        eventList.add(event);

        when(patientService.findPatientById(patient.getId())).thenReturn(patient);

        assertEquals(eventService.convertEventsToDTO(eventList).get(0).getClass().toString(),
                "class ru.senkot.DTO.EventStringDTO");
    }

    @Test
    public void selectAllEventsByPrescriptionId() {
        int prescriptionId = 1;
        Prescription prescription = new Prescription();
        prescription.setId(prescriptionId);

        Event event = new Event();
        event.setPrescription(prescription);

        List<Event> eventList = new ArrayList<>();
        eventList.add(event);

        when(eventDAO.findAllEventsByPrescriptionId(prescriptionId)).thenReturn(eventList);

        assertEquals(prescriptionId,
                eventService.findAllEventsByPrescriptionId(prescriptionId).get(0).getPrescription().getId());
    }

    @Test
    public void selectAllPlanedEventsByPrescriptionId() {
        int prescriptionId = 1;
        Prescription prescription = new Prescription();
        prescription.setId(prescriptionId);

        Event event = new Event();
        event.setPrescription(prescription);
        event.setStatus("planed");

        List<Event> eventList = new ArrayList<>();
        eventList.add(event);
        eventList.add(event);
        eventList.add(event);

        when(eventDAO.findAllEventsByPrescriptionId(prescriptionId)).thenReturn(eventList);

        assertEquals("planed",
                eventService.findAllEventsByPrescriptionId(prescriptionId).get(0).getStatus());
    }

    @Test
    public void selectEventsByDTO() {
        Patient patient = new Patient();
        patient.setId(1);

        Calendar calendarDate = Calendar.getInstance();
        calendarDate.set(2020, Calendar.SEPTEMBER, 6);
        Date date1 = new Date(calendarDate.getTime().getTime());

        Calendar calendarDate2 = Calendar.getInstance();
        calendarDate.set(2020, Calendar.SEPTEMBER, 5);
        Date date2 = new Date(calendarDate2.getTime().getTime());

        FilterEventsDTO filterEvent1 = new FilterEventsDTO(1, date1, "morning", "planed");
        FilterEventsDTO filterEvent2 = new FilterEventsDTO(1, date2, "evening", "done");
        Event event1 = new Event();
        event1.setDate(date1);
        event1.setTime("morning");
        event1.setStatus("planed");
        event1.setRemedyName("Aspirin");
        event1.setPatientId(1);

        Event event2 = new Event();
        event2.setDate(date2);
        event2.setTime("evening");
        event2.setStatus("done");
        event2.setRemedyName("Analgin");
        event2.setPatientId(1);

        List<Event> eventList = new ArrayList<>();
        eventList.add(event1);
        eventList.add(event2);

        when(eventDAO.findAllEvents()).thenReturn(eventList);

        assertEquals(event1.getRemedyName(),
                eventService.findEventsFromDTO(filterEvent1).get(0).getRemedyName());

        assertEquals(event2.getRemedyName(),
                eventService.findEventsFromDTO(filterEvent2).get(0).getRemedyName());
    }

    @Test
    public void checkPlanedEventsForPrescription() {
        int prescriptionId = 1;
        List<Event> eventList = new ArrayList<>();
        Event event = new Event();
        event.setStatus("planed");
        eventList.add(event);
        eventList.add(event);

        Event event1 = new Event();
        event1.setStatus("done");
        eventList.add(event1);

        when(eventDAO.findAllEventsByPrescriptionId(prescriptionId)).thenReturn(eventList);

        assertFalse(eventService.checkPlanedEventsForPrescription(prescriptionId));
    }

    @Test
    public void changeEventStatusForPrescriptionIdByPrescriptionId() {
        int prescriptionId = 1;
        List<Event> eventList = new ArrayList<>();
        Event event = new Event();
        event.setStatus("planed");
        eventList.add(event);
        eventList.add(event);
        eventList.add(event);

        when(eventDAO.findAllPlanedEventsByPrescriptionId(prescriptionId)).thenReturn(eventList);
        eventService.changeEventStatusForPrescriptionIdByPrescriptionId(prescriptionId);

        assertEquals("canceled", eventList.get(0).getStatus());
    }

    @Test
    public void updateEventStatusFromDTO() {
        Event event = new Event();
        event.setId(1);
        event.setStatus("planed");

        EventDTO eventDTO = new EventDTO(1, "done", "test");

        when(eventDAO.findEventById(1)).thenReturn(event);
        eventService.updateEventStatusFromDTO(eventDTO);

        assertEquals("done", event.getStatus());
    }

    @Test
    public void generateAndInsertEvents() {
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.set(2020, Calendar.SEPTEMBER, 1);
        Date dateStart = new Date(calendarDate.getTime().getTime());
        calendarDate.set(2020, Calendar.SEPTEMBER, 6);
        Date dateEnd = new Date(calendarDate.getTime().getTime());

        PrescriptionDTO prescriptionDTO = new PrescriptionDTO(1, "Aspirin", "pill",
                dateStart, dateEnd, null, "on", null, "on", null,
                null, null,
                1, "on", null, null, null);

        Map<Date, List<String>> testMap = eventService.dateTimeMap(prescriptionDTO);

        Calendar calendarDateTest1 = Calendar.getInstance();
        calendarDateTest1.set(2020, Calendar.SEPTEMBER, 1);
        Date dateTest1 = new Date(calendarDateTest1.getTime().getTime());
        System.out.println(dateTest1);

        Calendar calendarDateTest2 = Calendar.getInstance();
        calendarDateTest2.set(2020, Calendar.SEPTEMBER, 3);
        Date dateTest2 = new Date(calendarDateTest2.getTime().getTime());

        String testTime = null;

        for(Map.Entry<Date, List<String>> entry : testMap.entrySet()) {
            testTime = entry.getValue().get(0);
        }

        assertEquals("morning", testTime);
    }
}