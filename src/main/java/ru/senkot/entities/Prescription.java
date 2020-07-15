package ru.senkot.entities;


/*
The Prescription, connected to certain Patent;
Has dates of start and finish;
Can be canceled;
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Prescription {

    @Id
    @Column(name = "prescription_id")
    private int id;

    @Column(name = "patient_id")
    private int patient_id;

}
