package ru.senkot.entities;

/*
The event, bases on Prescription of certain Patient;
Generated automatically;
Can have status: plan, done, cancel;
If canceled: need to point the reason (bad effect, no technical ability...)
 */

import java.sql.Date;

public class Event {

    private int id;
    private Date date;
    private String time;
    private String status;
    private String remedyName;
    private String remedyType;
    private int quantity;

    private Patient patient;
    private Prescription prescription;

}
