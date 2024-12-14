package com.DoctorAppointment.repository;

import com.DoctorAppointment.model.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialtyRepository  extends JpaRepository<Specialty, Long> {

}
