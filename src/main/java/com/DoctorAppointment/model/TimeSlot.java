package com.DoctorAppointment.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long slotId;

    private String startTime;
    private String endTime;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name="docId", nullable = false)
    private Doctor doctor;

    private boolean isBooked = false;

    public TimeSlot() {
        super();
    }

    public TimeSlot(String startTime, String endTime, LocalDate date, Doctor doctor, boolean isBooked) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.doctor = doctor;
        this.isBooked = isBooked;
    }

    public long getSlotId() {
        return slotId;
    }

    public void setSlotId(long slotId) {
        this.slotId = slotId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }
}
