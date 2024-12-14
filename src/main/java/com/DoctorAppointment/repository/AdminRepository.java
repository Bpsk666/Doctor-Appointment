package com.DoctorAppointment.repository;

import com.DoctorAppointment.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.*;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByAdminEmail(String adminEmail);
    Admin findByAdminEmailAndAdminPassword(String adminEmail, String adminPassword);
}
