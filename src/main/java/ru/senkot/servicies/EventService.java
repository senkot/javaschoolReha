package ru.senkot.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import ru.senkot.DAO.EventDAO;
import ru.senkot.DTO.EventDTO;
import ru.senkot.DTO.EventStringDTO;
import ru.senkot.DTO.FilterEventsDTO;
import ru.senkot.DTO.PrescriptionDTO;
import ru.senkot.entities.Event;
import ru.senkot.exception.IdNotFoundException;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService {

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private PatientService patientService;

    /**
     * This method saves new Event in DB.
     *
     * @param event new instance Event class for saving
     */
    @Transactional
    public void saveEvent(Event event) {
        eventDAO.safeEvent(event);
    }

    /**
     * This method updates Event in DB.
     *
     * @param event existing instance of Event class
     */
    @Transactional
    public void updateEvent(Event event) {
        eventDAO.updateEvent(event);
    }

    /**
     * This method returns Event found by it's id from database.
     *
     * @param id id of Event
     * @return instance of Event class found in database by id
     */
    @Transactional
    public Event findEventById(int id) {
        return eventDAO.findEventById(id);
    }

    /**
     * This method returns the list of all existing events from DB.
     *
     * @return the list of all existing events found in DB
     */
    @Transactional
    public List<Event> findAllEvents() {
        return eventDAO.findAllEvents();
    }

    /**
     * This method returns the list of events found by date from DB.
     *
     * @param date date for find events with the same date
     * @return list of events found by date from DB
     */
    @Transactional
    public List<Event> findAllEventsByDate(Date date) {
        return eventDAO.findAllEventsByDate(date);
    }

    /**
     * This method finds the event by id, puts event object in model if it exist,
     * or throw IdNotFoundException.
     *
     * @param id searched event id
     * @return ModelAndView instance with the name of view page and instance of event (or without it)
     * @throws IdNotFoundException if can't find event by id in DB
     */
    public ModelAndView getMavForEvent(int id) throws IdNotFoundException {
        ModelAndView mav = new ModelAndView("event");
        Event event = findEventById(id);
        if (event != null) {
            mav.addObject("event", event);
        } else {
            throw new IdNotFoundException(id);
        }
        return mav;
    }

    /**
     * This method converts list of EventStringDTO objects from list of Event objects.
     *
     * @param events list of Event objects
     * @return list of EventStringDTO objects
     */
    public List<EventStringDTO> convertEventsToDTO(List<Event> events) {
        List<EventStringDTO> eventStringDTOList = new ArrayList<>();
        for (Event event : events) {
            eventStringDTOList.add(new EventStringDTO(event.getDate().toString(),
                    event.getTime(),
                    patientService.findPatientById(event.getPatientId()).getLastName() + " "
                            + patientService.findPatientById(event.getPatientId()).getFirstName(),
                    event.getRemedyName(),
                    event.getRemedyType(),
                    event.getQuantity(),
                    event.getStatus())
            );
        }
        return eventStringDTOList;
    }

    /**
     * This method returns all events found by prescription id.
     *
     * @param id prescription id for search connected events
     * @return list of events
     */
    @Transactional
    public List<Event> findAllEventsByPrescriptionId(int id) {
        return eventDAO.findAllEventsByPrescriptionId(id);
    }

    /**
     * This method returns planed events found by prescription id.
     *
     * @param id prescription id for search connected events
     * @return list of events
     */
    @Transactional
    public List<Event> findAllPlanedEventsByPrescriptionId(int id) {
        return eventDAO.findAllPlanedEventsByPrescriptionId(id);
    }

    /**
     * This method returns list of events filtered by data from FilterEventsDTO object.
     *
     * @param filterEventsDTO data-transfer object contains fields for filter the return list.
     *                        Contains date, patient id, time of the day and status.
     * @return list of all events from DB, filtered by data from FilterEventsDTO object
     */
    @Transactional
    public List<Event> findEventsFromDTO(FilterEventsDTO filterEventsDTO) {
        List<Event> events = findAllEvents();

        if (filterEventsDTO.getPatientId() != 0) {
            events = events.stream()
                    .filter(event -> event.getPatientId() == filterEventsDTO.getPatientId())
                    .collect(Collectors.toList());
        }
        if (filterEventsDTO.getDateToFilter() != null) {
            System.out.println(filterEventsDTO.getDateToFilter());
            events = events.stream()
                    .filter(event -> event.getDate().equals(filterEventsDTO.getDateToFilter()))
                    .collect(Collectors.toList());
        }
        if (!filterEventsDTO.getDayTime().equals("all")) {
            events = events.stream()
                    .filter(event -> event.getTime().equals(filterEventsDTO.getDayTime()))
                    .collect(Collectors.toList());
        }
        if (!filterEventsDTO.getStatus().equals("all")) {
            events = events.stream()
                    .filter(event -> event.getStatus().equals(filterEventsDTO.getStatus()))
                    .collect(Collectors.toList());
        }
        return events;
    }

    /**
     * This method checks all events found by id of existing prescription from DB.
     * If all found events have status "done" or "canceled", the method returns true.
     *
     * @param id id of prescription
     * @return true - if all found events have status "done" or "canceled", otherwise - false
     */
    @Transactional
    public boolean checkPlanedEventsForPrescription(int id) {
        List<Event> events = findAllEventsByPrescriptionId(id);
        int numberOfEvents = events.size();
        int counter = 0;
        for (Event event : events) {
            if (!event.getStatus().equals("planed")) counter++;
        }

        return numberOfEvents == counter;
    }

    /**
     * This method changes statuses on "canceled" in all planed events found by prescription id.
     *
     * @param id prescription id for find all connected events from DB
     */
    @Transactional
    public void changeEventStatusForPrescriptionIdByPrescriptionId(int id) {
        List<Event> planedEvents = findAllPlanedEventsByPrescriptionId(id);
        for (Event event : planedEvents) {
            event.setStatus("canceled");
            event.setCause("Prescription Edit");
        }
    }

    /**
     * Method for update Event status based on EventDTO status.
     *
     * @param eventDTO Object points the new status for Event
     */
    @Transactional
    public void updateEventStatusFromDTO(EventDTO eventDTO) {
        Event event = findEventById(eventDTO.getEventId());
        event.setStatus(eventDTO.getStatus());
        if (!eventDTO.getCause().isEmpty() && eventDTO.getCause() != null) {
            event.setCause(eventDTO.getCause());
        }
        updateEvent(event);
    }

    /**
     * This method generates new events based on data from PrescriptionDTO,
     * then insert this events into database.
     *
     * @param prescriptionDTO gives data for generating new events.
     */
    @Transactional
    public void generateAndInsertEvents(PrescriptionDTO prescriptionDTO) {
        Map<Date, List<String>> dateTimeMap = dateTimeMap(prescriptionDTO);

        for (Map.Entry<Date, List<String>> entry : dateTimeMap.entrySet()) {
            for (String time : entry.getValue()) {
                Event event = new Event(entry.getKey(),
                        time, "planed", prescriptionDTO.getRemedyName(),
                        prescriptionDTO.getRemedyType(), prescriptionDTO.getQuantity(),
                        prescriptionService.findPrescriptionById(prescriptionDTO.getPrescriptionId()),
                        prescriptionDTO.getPatientId());
                saveEvent(event);
            }
        }
    }

    /**
     * This method checks list of events found by patient id from DB.
     * If there is any coincidence in dates and times for event type "procedure",
     * method returns set of dates and times as strings (element has structure - "date : daytime"),
     * else returns null (if no coincidence).
     *
     * @param prescriptionDTO contains data to check the coincidence in DB
     * @return set of dates and times of coincidence as string or null
     */
    @Transactional
    public Set<String> overlapEventsFromPrescriptionMap(PrescriptionDTO prescriptionDTO) {
        Map<Date, List<String>> dateTimeMap = dateTimeMap(prescriptionDTO);
        List<Event> events = eventDAO.findAllEventsByPatientId(prescriptionDTO.getPatientId());
        return getStrings(prescriptionDTO, dateTimeMap, events, events);
    }

    /**
     * This method returns list of dates with each date
     * from period specified if PrescriptionDTO object.
     *
     * @param prescriptionDTO contains dates of start and end of the period
     * @return list of dates
     */
    private List<Date> dates(PrescriptionDTO prescriptionDTO) {
        List<Date> datesInRange = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(prescriptionDTO.getDateOfStart());

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(prescriptionDTO.getDateOfEnd());
        endCalendar.add(Calendar.DATE, 1);

        while (calendar.before(endCalendar)) {
            Date result = new Date(calendar.getTime().getTime());
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return datesInRange;
    }

    /**
     * This method returns Map with date as a key and list of times of day as a value.
     * Dates and times gets from PrescriptionDTO object, then this method verifies
     * dates by day of the week pointed in PrescriptionDTO object also.
     *
     * @param prescriptionDTO contains dates of start and end of the prescription,
     *                        times of he day and days of the week for repeat the prescription
     * @return Map with date as a key and list of times of day as a value
     */
    public Map<Date, List<String>> dateTimeMap(PrescriptionDTO prescriptionDTO) {
        List<Date> dates = dates(prescriptionDTO);
        Map<Date, List<String>> dateTimeMap = new HashMap<>();
        Calendar calendar = new GregorianCalendar();

        if (prescriptionDTO.getMonday() != null && prescriptionDTO.getMonday().equals("on")) {
            for (Date d : dates) {
                List<String> times = new ArrayList<>();
                calendar.setTime(d);
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                    listOfAcceptedTimes(prescriptionDTO, times);
                }

                if (!times.isEmpty()) dateTimeMap.put(d, times);
            }
        }

        if (prescriptionDTO.getTuesday() != null && prescriptionDTO.getTuesday().equals("on")) {
            for (Date d : dates) {
                List<String> times = new ArrayList<>();
                calendar.setTime(d);
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                    listOfAcceptedTimes(prescriptionDTO, times);
                }

                if (!times.isEmpty()) dateTimeMap.put(d, times);
            }
        }

        if (prescriptionDTO.getWednesday() != null && prescriptionDTO.getWednesday().equals("on")) {
            for (Date d : dates) {
                List<String> times = new ArrayList<>();
                calendar.setTime(d);
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                    listOfAcceptedTimes(prescriptionDTO, times);
                }

                if (!times.isEmpty()) dateTimeMap.put(d, times);
            }
        }

        if (prescriptionDTO.getThursday() != null && prescriptionDTO.getThursday().equals("on")) {
            for (Date d : dates) {
                List<String> times = new ArrayList<>();
                calendar.setTime(d);
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                    listOfAcceptedTimes(prescriptionDTO, times);
                }

                if (!times.isEmpty()) dateTimeMap.put(d, times);
            }
        }

        if (prescriptionDTO.getFriday() != null && prescriptionDTO.getFriday().equals("on")) {
            for (Date d : dates) {
                List<String> times = new ArrayList<>();
                calendar.setTime(d);
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                    listOfAcceptedTimes(prescriptionDTO, times);
                }

                if (!times.isEmpty()) dateTimeMap.put(d, times);
            }
        }

        if (prescriptionDTO.getSaturday() != null && prescriptionDTO.getSaturday().equals("on")) {
            for (Date d : dates) {
                List<String> times = new ArrayList<>();
                calendar.setTime(d);
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                    listOfAcceptedTimes(prescriptionDTO, times);
                }

                if (!times.isEmpty()) dateTimeMap.put(d, times);
            }
        }

        if (prescriptionDTO.getSunday() != null && prescriptionDTO.getSunday().equals("on")) {
            for (Date d : dates) {
                List<String> times = new ArrayList<>();
                calendar.setTime(d);
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    listOfAcceptedTimes(prescriptionDTO, times);
                }

                if (!times.isEmpty()) dateTimeMap.put(d, times);
            }
        }

        return dateTimeMap;
    }

    /**
     * This method checks times of day in PrescriptionDTO object
     * and add new item in list (second param) if it's "on".
     *
     * @param prescriptionDTO contains fields of times of days for check
     * @param times list for add times of days if it's "on" in PrescriptionDTO object
     */
    private void listOfAcceptedTimes(PrescriptionDTO prescriptionDTO, List<String> times) {
        if (prescriptionDTO.getMorning() != null && prescriptionDTO.getMorning().equals("on")) {
            times.add("morning");
        }
        if (prescriptionDTO.getAfternoon() != null && prescriptionDTO.getAfternoon().equals("on")) {
            times.add("afternoon");
        }
        if (prescriptionDTO.getEvening() != null && prescriptionDTO.getEvening().equals("on")) {
            times.add("evening");
        }
        if (prescriptionDTO.getNight() != null && prescriptionDTO.getNight().equals("on")) {
            times.add("night");
        }
    }

    /**
     * This method prepares Map of coincidence with date as a key and list of times of day as a value,
     * list of all events found by patient id and list of events for defined patient
     * except events connected the same prescription as point at PrescriptionDTO object.
     * Then this method calls getStrings() method and gives map and 2 lists there for getting
     * set of strings with dates and times of coincidence
     *
     * @param prescriptionDTO contains all data for prepare and create set of coincidences
     * @return set of dates and times of coincidence as string or null
     * (element of set has a structure - "date : daytime")
     */
    @Transactional
    public Set<String> overlapEventsFromPrescriptionMapForEdit(PrescriptionDTO prescriptionDTO) {
        Map<Date, List<String>> dateTimeMap = dateTimeMap(prescriptionDTO);
        List<Event> events = eventDAO.findAllEventsByPatientId(prescriptionDTO.getPatientId());
        List<Event> otherEvents = events.stream()
                .filter(event -> event.getPrescription().getId() != prescriptionDTO.getPrescriptionId())
                .collect(Collectors.toList());

        return getStrings(prescriptionDTO, dateTimeMap, events, otherEvents);
    }

    /**
     * This method checks list of events found for patient.
     * If there is any coincidence in dates and times for event type "procedure",
     * method returns set of dates and times as strings (element has structure - "date : daytime"),
     * else returns null (if no coincidence).
     * @param prescriptionDTO contains type of remedy for check
     * @param dateTimeMap contains Map with date as a key and list of times of day as a value for check
     * @param events contains all events for defined patient
     * @param otherEvents contains all events for defined patient
     *                   except events connected the same prescription as point at PrescriptionDTO object
     * @return set of dates and times of coincidence as string or null
     * (element of set has a structure - "date : daytime")
     */
    private Set<String> getStrings(PrescriptionDTO prescriptionDTO, Map<Date, List<String>> dateTimeMap,
                                   List<Event> events, List<Event> otherEvents) {
        if (!prescriptionDTO.getRemedyType().equals("procedure")) return null;
        if (events.isEmpty()) {
            return null;
        } else {
            List<String> collisionList = new ArrayList<>();
            Set<String> collisionSet = new HashSet<>();

            for (Event event : otherEvents) {
                for (Map.Entry<Date, List<String>> entry : dateTimeMap.entrySet()) {
                    List<String> times = new ArrayList<>();
                    for (String time : entry.getValue()) {
                        if (event.getRemedyType().equals("procedure")
                                && event.getStatus().equals("planed")
                                && event.getDate().equals(entry.getKey())
                                && event.getTime().equals(time)) times.add(time);
                    }
                    if (!times.isEmpty()) {
                        for (String time : times) {
                            collisionList.add(entry.getKey() + " : " + time);
                            collisionSet.add(entry.getKey() + " : " + time);
                        }
                    }
                }
            }
            if (!collisionList.isEmpty()) return collisionSet;
            else return null;
        }
    }

}
