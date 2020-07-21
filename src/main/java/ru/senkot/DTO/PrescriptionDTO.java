package ru.senkot.DTO;

import java.sql.Date;

public class PrescriptionDTO {

    private int patientId;
    private String remedyName;
    private String remedyType;
    private Date dateOfStart;
    private Date dateOfEnd;
    private String repeat;
    private int quantity;

    public PrescriptionDTO(int patientId, String remedyName, String remedyType, Date dateOfStart, Date dateOfEnd, String repeat, int quantity) {
        this.patientId = patientId;
        this.remedyName = remedyName;
        this.remedyType = remedyType;
        this.dateOfStart = dateOfStart;
        this.dateOfEnd = dateOfEnd;
        this.repeat = repeat;
        this.quantity = quantity;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getRemedyName() {
        return remedyName;
    }

    public void setRemedyName(String remedyName) {
        this.remedyName = remedyName;
    }

    public String getRemedyType() {
        return remedyType;
    }

    public void setRemedyType(String remedyType) {
        this.remedyType = remedyType;
    }

    public Date getDateOfStart() {
        return dateOfStart;
    }

    public void setDateOfStart(Date dateOfStart) {
        this.dateOfStart = dateOfStart;
    }

    public Date getDateOfEnd() {
        return dateOfEnd;
    }

    public void setDateOfEnd(Date dateOfEnd) {
        this.dateOfEnd = dateOfEnd;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
