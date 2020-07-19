package ru.senkot.entities;


/*
The Prescription, connected to certain Patent;
Has dates of start and finish;
Can be canceled;
 */

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "remedy_name")
    private String remedy_name;

    @Column(name = "remedy_type")
    private String remedy_type;

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

    @Override
    public String toString() {
        return remedy_name + " " + remedy_type + " " + dateStart + " " + dateEnd + " " + quantity;
    }

    public int getId() {
        return id;
    }

    public String getRemedy_name() {
        return remedy_name;
    }

    public void setRemedy_name(String remedy_name) {
        this.remedy_name = remedy_name;
    }

    public String getRemedy_type() {
        return remedy_type;
    }

    public void setRemedy_type(String remedy_type) {
        this.remedy_type = remedy_type;
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
