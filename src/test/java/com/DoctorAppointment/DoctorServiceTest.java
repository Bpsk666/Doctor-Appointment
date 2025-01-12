package com.DoctorAppointment;

import com.DoctorAppointment.model.Doctor;
import com.DoctorAppointment.repository.DoctorRepository;
import com.DoctorAppointment.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private DoctorService doctorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void newDoc_shouldSaveDoctor() {
        Doctor doctor = new Doctor();
        doctor.setDocFName("John");
        doctor.setDocLName("Doe");
        doctor.setDocEmail("john.doe@example.com");

        doctorService.newDoc(doctor);

        verify(doctorRepository, times(1)).save(doctor);
    }

    @Test
    void checkLogin_shouldReturnDashboardIfCredentialsAreValid() {
        Doctor doctor = new Doctor();
        doctor.setDocEmail("john.doe@example.com");
        doctor.setDocPassword("encryptedPassword");

        when(doctorRepository.findByDocEmail("john.doe@example.com")).thenReturn(doctor);
        when(passwordEncoder.matches("password", "encryptedPassword")).thenReturn(true);
        when(doctorRepository.findByDocEmailAndDocPassword("john.doe@example.com", "encryptedPassword"))
                .thenReturn(doctor);

        String result = doctorService.checkLogin("john.doe@example.com", "password");

        assertEquals("docDashboard", result);
    }

    @Test
    void checkLogin_shouldReturnLoginIfDoctorNotFound() {
        when(doctorRepository.findByDocEmail("john.doe@example.com")).thenReturn(null);

        String result = doctorService.checkLogin("john.doe@example.com", "password");

        assertEquals("Login", result);
    }

    @Test
    void checkLogin_shouldReturnLoginIfPasswordDoesNotMatch() {
        Doctor doctor = new Doctor();
        doctor.setDocEmail("john.doe@example.com");
        doctor.setDocPassword("encryptedPassword");

        when(doctorRepository.findByDocEmail("john.doe@example.com")).thenReturn(doctor);
        when(passwordEncoder.matches("wrongPassword", "encryptedPassword")).thenReturn(false);

        String result = doctorService.checkLogin("john.doe@example.com", "wrongPassword");

        assertEquals("Login", result);
    }

    @Test
    void getDoctor_shouldReturnDoctor() {
        Doctor doctor = new Doctor();
        doctor.setDocEmail("john.doe@example.com");
        doctor.setDocPassword("encryptedPassword");

        when(doctorRepository.findByDocEmailAndDocPassword("john.doe@example.com", "encryptedPassword"))
                .thenReturn(doctor);

        Doctor result = doctorService.getDoctor("john.doe@example.com", "encryptedPassword");

        assertNotNull(result);
        assertEquals("john.doe@example.com", result.getDocEmail());
    }

    @Test
    void getAllDoc_shouldReturnAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor());
        doctors.add(new Doctor());

        when(doctorRepository.findAll()).thenReturn(doctors);

        List<Doctor> result = doctorService.getAllDoc();

        assertEquals(2, result.size());
    }

    @Test
    void getDocById_shouldReturnDoctorIfFound() {
        Doctor doctor = new Doctor();
        doctor.setDocFName("John");

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        Doctor result = doctorService.getDocById(1L);

        assertNotNull(result);
        assertEquals("John", result.getDocFName());
    }

    @Test
    void getDocById_shouldThrowExceptionIfNotFound() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> doctorService.getDocById(1L));
    }

    @Test
    void updateDoctor_shouldUpdateExistingDoctor() {
        Doctor existingDoctor = new Doctor();
        existingDoctor.setDocFName("John");
        existingDoctor.setDocLName("Doe");

        Doctor updatedDoctor = new Doctor();
        updatedDoctor.setDocFName("Jane");
        updatedDoctor.setDocLName("Smith");

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(existingDoctor));

        doctorService.updateDoctor(1L, updatedDoctor);

        verify(doctorRepository, times(1)).save(existingDoctor);
        assertEquals("Jane", existingDoctor.getDocFName());
        assertEquals("Smith", existingDoctor.getDocLName());
    }

    @Test
    void updateDoctor_shouldThrowExceptionIfDoctorNotFound() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        Doctor updatedDoctor = new Doctor();

        assertThrows(RuntimeException.class, () -> doctorService.updateDoctor(1L, updatedDoctor));
    }

    @Test
    void getDocBySpecialty_shouldReturnDoctorsWithSpecialty() {
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor());
        doctors.add(new Doctor());

        when(doctorRepository.findBySpecialty("Cardiology")).thenReturn(doctors);

        List<Doctor> result = doctorService.getDocBySpecialty("Cardiology");

        assertEquals(2, result.size());
    }

    @Test
    void deleteDoc_shouldDeleteDoctor() {
        doctorService.deleteDoc(1L);

        verify(doctorRepository, times(1)).deleteById(1L);
    }
}
