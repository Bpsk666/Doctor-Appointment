package com.DoctorAppointment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long patientId;

    private String patientFName;
    private String patientLName;
    private String patientAge;
    private String patientPhone;
    private String patientAddress;
    private String patientEmail;
    private String patientPassword;

    public Patient() {
        super();
    }

    public Patient(String patientFName, String patientLName, String patientAge, String patientPhone, String patientAddress, String patientEmail, String patientPassword) {
        this.patientFName = patientFName;
        this.patientLName = patientLName;
        this.patientAge = patientAge;
        this.patientPhone = patientPhone;
        this.patientAddress = patientAddress;
        this.patientEmail = patientEmail;
        this.patientPassword = patientPassword;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getPatientFName() {
        return patientFName;
    }

    public void setPatientFName(String patientFName) {
        this.patientFName = patientFName;
    }

    public String getPatientLName() {
        return patientLName;
    }

    public void setPatientLName(String patientLName) {
        this.patientLName = patientLName;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getPatientPassword() {
        return patientPassword;
    }

    public void setPatientPassword(String patientPassword) {
        this.patientPassword = patientPassword;
    }
}
