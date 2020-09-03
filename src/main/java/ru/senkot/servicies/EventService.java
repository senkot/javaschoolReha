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

    @Transactional
    public void insertEvent(Event event) {
        eventDAO.insertEvent(event);
    }

    @Transactional
    public void updateEvent(Event event) {
        eventDAO.updateEvent(event);
    }

    @Transactional
    public Event selectEvent(int id) {
        return eventDAO.selectEvent(id);
    }

    @Transactional
    public List<Event> selectAllEvents() {
        return eventDAO.selectAllEvents();
    }

    @Transactional
    public List<Event> selectAllEventsByDate(Date date) {
        return eventDAO.selectAllEventsByDate(date);
    }

    public ModelAndView getMavForEvent(int id) throws IdNotFoundException {
        ModelAndView mav = new ModelAndView("event");
        Event event = selectEvent(id);
        if (event != null) {
            mav.addObject("event", event);
        } else {
            throw new IdNotFoundException(id);
        }
        return mav;
    }

    public List<EventStringDTO> convertEventsToDTO(List<Event> events) {
        List<EventStringDTO> eventStringDTOList = new ArrayList<>();
        for (Event event : events) {
            eventStringDTOList.add(new EventStringDTO(event.getDate().toString(),
                    event.getTime(),
                    patientService.selectPatient(event.getPatientId()).getLastName() + " "
                            + patientService.selectPatient(event.getPatientId()).getFirstName(),
                    event.getRemedyName(),
                    event.getRemedyType(),
                    event.getQuantity(),
                    event.getStatus())
            );
        }
        return eventStringDTOList;
    }

    @Transactional
    public List<Event> selectAllEventsByPrescriptionId(int id) {
        return eventDAO.selectAllEventsByPrescriptionId(id);
    }

    @Transactional
    public List<Event> selectAllPlanedEventsByPrescriptionId(int id) {
        return eventDAO.selectAllPlanedEventsByPrescriptionId(id);
    }

    @Transactional
    public void deleteEvent(Event event) {
        eventDAO.deleteEvent(event);
    }

    @Transactional
    public List<Event> selectEventsByDTO(FilterEventsDTO filterEventsDTO) {
        List<Event> events = selectAllEvents();

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

    @Transactional
    public boolean checkPlanedEventsForPrescription(int id) {
        List<Event> events = selectAllEventsByPrescriptionId(id);
        int numberOfEvents = events.size();
        int counter = 0;
        for (Event event : events) {
            if (!event.getStatus().equals("planed")) counter++;
        }

        return numberOfEvents == counter;
    }

    @Transactional
    public void changeEventStatusForPrescriptionIdByPrescriptionId(int id) {
        List<Event> planedEvents = selectAllPlanedEventsByPrescriptionId(id);
        for (Event event : planedEvents) {
            event.setStatus("canceled");
            event.setCause("Prescription Edit");
        }
    }

    @Transactional
    public void updateEventStatusFromDTO(EventDTO eventDTO) {
        Event event = selectEvent(eventDTO.getEventId());
        event.setStatus(eventDTO.getStatus());
        if (!eventDTO.getCause().isEmpty() && eventDTO.getCause() != null) {
            event.setCause(eventDTO.getCause());
        }
        updateEvent(event);
    }

    @Transactional
    public void generateAndInsertEvents(PrescriptionDTO prescriptionDTO) {
        Map<Date, List<String>> dateTimeMap = dateTimeMap(prescriptionDTO);

        for (Map.Entry<Date, List<String>> entry : dateTimeMap.entrySet()) {
            for (String time : entry.getValue()) {
                Event event = new Event(entry.getKey(),
                        time, "planed", prescriptionDTO.getRemedyName(),
                        prescriptionDTO.getRemedyType(), prescriptionDTO.getQuantity(),
                        prescriptionService.selectPrescription(prescriptionDTO.getPrescriptionId()),
                        prescriptionDTO.getPatientId());
                insertEvent(event);
            }
        }
    }

    // проверка списка Events пациента из PrescriptionDTO на возможность добавления событий
    // проверка на отсутствие совпадений дат и времени дня, если это процедура
    // возвращает null, если нет коллизий
    // возвращает List с совпадениями в виде: Дата : Время-дня

    @Transactional
    public Set<String> overlapEventsFromPrescriptionMap(PrescriptionDTO prescriptionDTO) {
        Map<Date, List<String>> dateTimeMap = dateTimeMap(prescriptionDTO);
        List<Event> events = eventDAO.selectAllEventsByPatientId(prescriptionDTO.getPatientId());
        return getStrings(prescriptionDTO, dateTimeMap, events, events);
    }

    // получение списка дат с каждой датой из периода PrescriptionDTO

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

    // Получение Map с датой и списком времени дня для применения назначения
    // и генерации событий по дате и времени

    private Map<Date, List<String>> dateTimeMap(PrescriptionDTO prescriptionDTO) {
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

    // вспомогательный метод для проверки актуальности времени дня

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

    @Transactional
    public Set<String> overlapEventsFromPrescriptionMapForEdit(PrescriptionDTO prescriptionDTO) {
        Map<Date, List<String>> dateTimeMap = dateTimeMap(prescriptionDTO);
        List<Event> events = eventDAO.selectAllEventsByPatientId(prescriptionDTO.getPatientId());
        List<Event> otherEvents = events.stream()
                .filter(event -> event.getPrescription().getId() != prescriptionDTO.getPrescriptionId())
                .collect(Collectors.toList());

        return getStrings(prescriptionDTO, dateTimeMap, events, otherEvents);
    }

    private Set<String> getStrings(PrescriptionDTO prescriptionDTO, Map<Date, List<String>> dateTimeMap, List<Event> events, List<Event> otherEvents) {
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
