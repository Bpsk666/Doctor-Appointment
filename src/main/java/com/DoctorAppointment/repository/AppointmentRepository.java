package com.DoctorAppointment.repository;

import com.DoctorAppointment.enums.AppointmentStatus;
import com.DoctorAppointment.model.Appointment;
import com.DoctorAppointment.model.Doctor;
import com.DoctorAppointment.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    List<Appointment> findByPatient(@Param("patientId")Patient patient);

    List<Appointment> findByDoctor(@Param("docId")Doctor doctor);

    List<Appointment> findByStatus(AppointmentStatus status);
    @Query("SELECT a from  Appointment a where a.status= :status AND a.doctor.docId = :docId")
    List<Appointment> findByDoctorAndStatus(@Param("docId")Long docId, @Param("status")AppointmentStatus status);
}
