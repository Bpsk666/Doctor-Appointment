package com.DoctorAppointment;

import com.DoctorAppointment.model.Patient;
import com.DoctorAppointment.repository.PatientRepository;
import com.DoctorAppointment.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void savePatient_shouldSavePatient() {
        Patient patient = new Patient();
        patient.setPatientFName("John");
        patient.setPatientLName("Doe");

        patientService.savePatient(patient);

        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    void findPatientById_shouldReturnPatientIfFound() {
        Patient patient = new Patient();
        patient.setPatientId(1L);
        patient.setPatientFName("Jane");
        patient.setPatientLName("Doe");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        Patient result = patientService.findPatientById(1L);

        assertNotNull(result);
        assertEquals("Jane", result.getPatientFName());
        assertEquals("Doe", result.getPatientLName());
    }

    @Test
    void findPatientById_shouldThrowExceptionIfNotFound() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> patientService.findPatientById(1L));
    }

    @Test
    void checkLogin_shouldRedirectToPatientHomePageIfCredentialsAreInvalid() {

        when(patientRepository.findByPatientEmail("jane.doe@example.com")).thenReturn(null);


        String result = patientService.checkLogin("jane.doe@example.com", "password");

        assertEquals("redirect:/patientSys/patientHomePage", result);
    }


    @Test
    void checkLogin_shouldRedirectToPatientHomePageIfCredentialsAreValid() {
        Patient patient = new Patient();
        patient.setPatientEmail("jane.doe@example.com");
        patient.setPatientPassword("encryptedPassword");

        when(patientRepository.findByPatientEmail("jane.doe@example.com")).thenReturn(patient);
        when(passwordEncoder.matches("password", "encryptedPassword")).thenReturn(true);
        when(patientRepository.findByPatientEmailAndPatientPassword("jane.doe@example.com", "encryptedPassword"))
                .thenReturn(patient);

        String result = patientService.checkLogin("jane.doe@example.com", "password");

        assertEquals("redirect:/patientSys/patientHomePage", result);
    }

    @Test
    void checkLogin_shouldReturnHomePageIfPasswordDoesNotMatch() {
        Patient patient = new Patient();
        patient.setPatientEmail("jane.doe@example.com");
        patient.setPatientPassword("encryptedPassword");

        when(patientRepository.findByPatientEmail("jane.doe@example.com")).thenReturn(patient);
        when(passwordEncoder.matches("wrongPassword", "encryptedPassword")).thenReturn(false);

        String result = patientService.checkLogin("jane.doe@example.com", "wrongPassword");

        assertEquals("homePage", result);
    }

    @Test
    void getPatient_shouldReturnPatientIfCredentialsMatch() {
        Patient patient = new Patient();
        patient.setPatientEmail("jane.doe@example.com");
        patient.setPatientPassword("encryptedPassword");

        when(patientRepository.findByPatientEmailAndPatientPassword("jane.doe@example.com", "encryptedPassword"))
                .thenReturn(patient);

        Patient result = patientService.getPatient("jane.doe@example.com", "encryptedPassword");

        assertNotNull(result);
        assertEquals("jane.doe@example.com", result.getPatientEmail());
    }
}
