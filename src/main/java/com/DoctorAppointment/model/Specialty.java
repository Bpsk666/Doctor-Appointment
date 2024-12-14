package com.DoctorAppointment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long specialId;

    private String specialtyField;

    public Specialty() {
        super();
    }

    public Specialty(String specialtyField) {
        this.specialtyField = specialtyField;
    }

    public long getSpecialId() {
        return specialId;
    }

    public void setSpecialId(long specialId) {
        this.specialId = specialId;
    }

    public String getSpecialtyField() {
        return specialtyField;
    }

    public void setSpecialtyField(String specialtyField) {
        this.specialtyField = specialtyField;
    }
}
