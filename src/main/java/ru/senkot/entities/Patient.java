package ru.senkot.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "insurance")
    private String insurance;              // We have 16 digits in Russian Insurance

    @Column(name = "additional_insurance")
    private String additionalInsurance;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "secondname")
    private String secondName;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prescription> prescriptions;

    public Patient() {
    }

    public boolean haveAdditionalInsurance() {
        return additionalInsurance != null && !additionalInsurance.equals("");
    }

    public Patient(String insurance, String additionalInsurance, String firstName, String lastName,
                   String secondName, Date dateOfBirth) {
        this.insurance = insurance;
        this.additionalInsurance = additionalInsurance;
        this.firstName = firstName;
        this.lastName = lastName;
        this.secondName = secondName;
        this.dateOfBirth = dateOfBirth;
    }



    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", insurance=" + insurance +
                ", additionalInsurance" + additionalInsurance +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdditionalInsurance() {
        return additionalInsurance;
    }

    public void setAdditionalInsurance(String additionalInsurance) {
        this.additionalInsurance = additionalInsurance;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;

        Patient anotherPatient = (Patient) obj;
        return id == anotherPatient.getId() && (insurance != null && insurance.equals(anotherPatient.insurance));
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (secondName != null ? secondName.hashCode() : 0);
        return result;
    }

}
