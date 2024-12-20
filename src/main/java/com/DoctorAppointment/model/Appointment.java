package com.DoctorAppointment.model;

import com.DoctorAppointment.enums.AppointmentStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long apptId;

    @ManyToOne
    @JoinColumn(name = "patientId", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "docId", nullable = false)
    private Doctor doctor;

    @OneToOne
    private TimeSlot timeSlot;


    @Enumerated(EnumType.STRING)
    private AppointmentStatus status = AppointmentStatus.PENDING;

    public Appointment() {
        super();
    }

    public Appointment(Patient patient, Doctor doctor, TimeSlot timeSlot, AppointmentStatus status) {
        this.patient = patient;
        this.doctor = doctor;
        this.timeSlot = timeSlot;
        this.status = status;
    }

    public long getApptId() {
        return apptId;
    }

    public void setApptId(long apptId) {
        this.apptId = apptId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
}
