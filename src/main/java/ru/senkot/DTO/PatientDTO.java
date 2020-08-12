package ru.senkot.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;

public class PatientDTO {

    private int patientId;
    private String doctorName;
    private String state;
    private String diagnosis;

    @NotBlank(message = "insurance blank")
    @Size(min = 7, max = 30, message = "insurance size")
    private String insurance;

    private String additionalInsurance;

    @NotBlank(message = "firstName blank")
    @Size(min = 2, max = 30, message = "firstName size")
    private String firstName;

    @NotBlank(message = "lastName blank")
    @Size(min = 2, max = 30, message = "lastName size")
    private String lastName;


    private String secondName;

    private Date dateOfBirth;

    public PatientDTO(String doctorName, String diagnosis,
                      String insurance, String additionalInsurance,
                      String firstName, String lastName, String secondName, Date dateOfBirth) {
        this.doctorName = doctorName;
        this.diagnosis = diagnosis;
        this.insurance = insurance;
        this.additionalInsurance = additionalInsurance;
        this.firstName = firstName;
        this.lastName = lastName;
        this.secondName = secondName;
        this.dateOfBirth = dateOfBirth;
    }

    public PatientDTO() {
    }

    public PatientDTO(int patientId, String state) {
        this.patientId = patientId;
        this.state = state;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getAdditionalInsurance() {
        return additionalInsurance;
    }

    public void setAdditionalInsurance(String additionalInsurance) {
        this.additionalInsurance = additionalInsurance;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
