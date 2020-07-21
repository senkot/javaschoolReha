package ru.senkot.entities;


/*
The Prescription, connected to certain Patent;
Has dates of start and finish;
Can be canceled;
 */

import ru.senkot.servicies.PatientService;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "prescription")
public class Prescription {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "remedy_name")
    private String remedyName;

    @Column(name = "remedy_type")
    private String remedyType;

    @Column(name = "date_start")
    private Date dateStart;

    @Column(name = "date_end")
    private Date dateEnd;

    @Column(name = "repeat")
    private String repeat;

    @Column(name = "quantity")
    private int quantity;

    public Prescription() {

    }

    public Prescription(Patient patient, String remedyName, String remedyType, Date dateStart, Date dateEnd, String repeat, int quantity) {
        this.patient = patient;
        this.remedyName = remedyName;
        this.remedyType = remedyType;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.repeat = repeat;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return remedyName + " " + remedyType + " " + dateStart + " " + dateEnd + " " + quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
