package ru.senkot.DTO;

import java.sql.Date;

public class FilterEventsDTO {

    private int patientId;
    private Date dateToFilter;
    private String dayTime;
    private String status;

    public FilterEventsDTO(int patientId, Date dateToFilter, String dayTime, String status) {
        this.patientId = patientId;
        this.dateToFilter = dateToFilter;
        this.dayTime = dayTime;
        this.status = status;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public Date getDateToFilter() {
        return dateToFilter;
    }

    public void setDateToFilter(Date dateToFilter) {
        this.dateToFilter = dateToFilter;
    }

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
