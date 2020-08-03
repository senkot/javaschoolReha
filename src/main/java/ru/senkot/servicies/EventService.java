package ru.senkot.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.senkot.DAO.EventDAO;
import ru.senkot.DTO.PrescriptionDTO;
import ru.senkot.entities.Event;
import ru.senkot.entities.Prescription;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventDAO eventDAO;

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
    public List<Prescription> selectAllEventsByPatientId(int id) {
        return eventDAO.selectAllEventsByPatientId(id);
    }

    @Transactional
    public void deleteEvent(Event event) {
        eventDAO.deleteEvent(event);
    }

    public List<Date> dates(PrescriptionDTO prescriptionDTO) {
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

}
