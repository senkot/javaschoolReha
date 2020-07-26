package ru.senkot.entities;

/*
The event, bases on Prescription of certain Patient;
Generated automatically;
Can have status: plan, done, cancel;
If canceled: need to point the reason (bad effect, no technical ability...)
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
@Table(name = "event")
public class Event {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date")
    private Date date;

    @Column(name = "time")
    private String time;

    @Column(name = "status")
    private String status;

    @Column(name = "remedy_name")
    private String remedyName;

    @Column(name = "remedy_type")
    private String remedyType;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    public Event(){}

    @Override
    public String toString() {
        return date + " " + time + " " + status + " " + remedyName + " " + remedyType +
                " " + quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }
}
