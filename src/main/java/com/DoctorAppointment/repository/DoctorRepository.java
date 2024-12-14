package com.DoctorAppointment.repository;

import com.DoctorAppointment.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findBySpecialty(String specialty);

    Doctor findByDocEmail(String docEmail);
    Doctor findByDocEmailAndDocPassword(String docEmail, String docPassword);
}
