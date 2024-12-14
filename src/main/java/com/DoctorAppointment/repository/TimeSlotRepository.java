package com.DoctorAppointment.repository;

import com.DoctorAppointment.model.Doctor;
import com.DoctorAppointment.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    List<TimeSlot> findByDoctor(@Param("docId")Doctor doctor);
}
