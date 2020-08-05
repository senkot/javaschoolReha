package ru.senkot.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.senkot.DAO.EventDAO;
import ru.senkot.DTO.PrescriptionDTO;
import ru.senkot.entities.Event;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventService {

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private PrescriptionService prescriptionService;

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
    public List<Event> selectAllEventsByPatientId(int id) {
        return eventDAO.selectAllEventsByPatientId(id);
    }

    @Transactional
    public void deleteEvent(Event event) {
        eventDAO.deleteEvent(event);
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
    public List<String> overlapEventsFromPrescriptionMap(PrescriptionDTO prescriptionDTO) {
        Map<Date, List<String>> dateTimeMap = dateTimeMap(prescriptionDTO);
        List<Event> events = eventDAO.selectAllEventsByPatientId(prescriptionDTO.getPatientId());
        if (events.isEmpty()) {
            return null;
        } else {
            List<String> collisionList = new ArrayList<>();

            for (Event event : events) {
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
                        }
                    }
                }
            }

            if (!collisionList.isEmpty()) return collisionList;
            else return null;
        }
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

}
