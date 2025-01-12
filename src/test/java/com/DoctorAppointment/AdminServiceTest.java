package com.DoctorAppointment;

import com.DoctorAppointment.model.Admin;
import com.DoctorAppointment.repository.AdminRepository;
import com.DoctorAppointment.service.AdminService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AdminServiceTest {
    @Mock
    private AdminRepository adminRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void checkLogin_validCredentials_shouldRedirectToHomePage() {
        String adminEmail = "admin@example.com";
        String adminPassword = "password123";
        String encodedPassword = "$2a$10$abcdefghijklmnopqrstuvwx";

        Admin mockAdmin = new Admin();
        mockAdmin.setAdminEmail(adminEmail);
        mockAdmin.setAdminPassword(encodedPassword);

        Mockito.when(adminRepository.findByAdminEmail(adminEmail)).thenReturn(mockAdmin);
        Mockito.when(passwordEncoder.matches(adminPassword, encodedPassword)).thenReturn(true);
        Mockito.when(adminRepository.findByAdminEmailAndAdminPassword(adminEmail, encodedPassword))
                .thenReturn(mockAdmin);

        // Act
        String result = adminService.checkLogin(adminEmail, adminPassword);

        // Assert
        Assertions.assertEquals("redirect:/adminsys/adminHomePage", result);
    }

    @Test
    void checkLogin_invalidPassword_shouldReturnLoginPage() {
        String adminEmail = "admin@example.com";
        String rawPassword = "wrongPassword";
        String encodedPassword = "$2a$10$D9Hq9L0p.P2jWz8HWF0qYuLJdZLEckGnZ5";

        Admin mockAdmin = new Admin();
        mockAdmin.setAdminEmail(adminEmail);
        mockAdmin.setAdminPassword(encodedPassword);

        when(adminRepository.findByAdminEmail(adminEmail)).thenReturn(mockAdmin);
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        String result = adminService.checkLogin(adminEmail, rawPassword);

        assertEquals("Login", result);
        verify(adminRepository, times(1)).findByAdminEmail(adminEmail);
        verify(passwordEncoder, times(1)).matches(rawPassword, encodedPassword);
    }

    @Test
    void getAdmin_validCredentials_shouldReturnAdmin() {
        String adminEmail = "admin@example.com";
        String adminPassword = "password123";

        Admin mockAdmin = new Admin();
        mockAdmin.setAdminEmail(adminEmail);
        mockAdmin.setAdminPassword(adminPassword);

        when(adminRepository.findByAdminEmailAndAdminPassword(adminEmail, adminPassword)).thenReturn(mockAdmin);

        Admin result = adminService.getAdmin(adminEmail, adminPassword);

        assertEquals(mockAdmin, result);
        verify(adminRepository, times(1)).findByAdminEmailAndAdminPassword(adminEmail, adminPassword);
    }

    @Test
    void getAdmin_invalidCredentials_shouldReturnNull() {
        String adminEmail = "admin@example.com";
        String adminPassword = "wrongPassword";

        when(adminRepository.findByAdminEmailAndAdminPassword(adminEmail, adminPassword)).thenReturn(null);

        Admin result = adminService.getAdmin(adminEmail, adminPassword);

        assertEquals(null, result);
        verify(adminRepository, times(1)).findByAdminEmailAndAdminPassword(adminEmail, adminPassword);
    }
}
