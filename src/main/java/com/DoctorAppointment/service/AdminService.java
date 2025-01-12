package com.DoctorAppointment.service;

import com.DoctorAppointment.model.Admin;
import com.DoctorAppointment.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRep;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String checkLogin(String adminEmail, String adminPassword){
        Admin admin = adminRep.findByAdminEmail(adminEmail);
        if(admin!=null){
            if(passwordEncoder.matches(adminPassword, admin.getAdminPassword()))
            {
                adminPassword = admin.getAdminPassword();
            }
            admin = adminRep.findByAdminEmailAndAdminPassword(adminEmail, adminPassword);
            if(admin==null)
            {
                return "Login";
            }
        }
        return "redirect:/adminsys/adminHomePage";
    }
    public Admin getAdmin(String adminEmail, String adminPassword)
    {
        return adminRep.findByAdminEmailAndAdminPassword(adminEmail,adminPassword);
    }
}
