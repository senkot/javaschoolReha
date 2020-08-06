package ru.senkot.DTO;

public class EventDTO {
    private int eventId;
    private String status;
    private String cause;

    public EventDTO(int eventId, String status, String cause) {
        this.eventId = eventId;
        this.status = status;
        this.cause = cause;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
