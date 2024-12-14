package com.DoctorAppointment.repository;

import com.DoctorAppointment.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    Patient findByPatientEmail(String patientEmail);
    Patient findByPatientEmailAndPatientPassword(String patientEmail, String patientPassword);
}
