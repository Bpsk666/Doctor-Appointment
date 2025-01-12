package com.DoctorAppointment;

import com.DoctorAppointment.enums.AppointmentStatus;
import com.DoctorAppointment.model.Appointment;
import com.DoctorAppointment.model.Doctor;
import com.DoctorAppointment.model.Patient;
import com.DoctorAppointment.model.TimeSlot;
import com.DoctorAppointment.repository.AppointmentRepository;
import com.DoctorAppointment.repository.DoctorRepository;
import com.DoctorAppointment.repository.PatientRepository;
import com.DoctorAppointment.repository.TimeSlotRepository;
import com.DoctorAppointment.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private PatientRepository patientRep;

    @Mock
    private DoctorRepository docRep;

    @Mock
    private TimeSlotRepository tsRep;

    @Mock
    private AppointmentRepository apptRepo;

    @Test
    void findAllApts_shouldReturnListOfAppointments() {
        // Arrange
        List<Appointment> mockAppointments = new ArrayList<>();
        mockAppointments.add(new Appointment());
        mockAppointments.add(new Appointment());
        when(apptRepo.findAll()).thenReturn(mockAppointments);

        // Act
        List<Appointment> result = appointmentService.findAllApts();

        // Assert
        assertEquals(2, result.size());
        verify(apptRepo, times(1)).findAll();
    }

    @Test
    void findAptById_shouldReturnAppointment() {
        // Arrange
        Appointment mockAppointment = new Appointment();
        when(apptRepo.findById(1L)).thenReturn(Optional.of(mockAppointment));

        // Act
        Appointment result = appointmentService.findAptById(1L);

        // Assert
        assertNotNull(result);
        verify(apptRepo, times(1)).findById(1L);
    }

    @Test
    void findAptByPatient_shouldReturnListOfAppointments() {
        // Arrange
        Patient mockPatient = new Patient();
        List<Appointment> mockAppointments = new ArrayList<>();
        mockAppointments.add(new Appointment());
        when(apptRepo.findByPatient(mockPatient)).thenReturn(mockAppointments);

        // Act
        List<Appointment> result = appointmentService.findAptByPatient(mockPatient);

        // Assert
        assertEquals(1, result.size());
        verify(apptRepo, times(1)).findByPatient(mockPatient);
    }

    @Test
    void cancelApt_shouldDeleteAppointment() {
        // Act
        appointmentService.cancelApt(1L);

        // Assert
        verify(apptRepo, times(1)).deleteById(1L);
    }

    @Test
    void bookAppointment_shouldBookAppointment() throws Exception {
        // Arrange
        Patient mockPatient = new Patient();
        Doctor mockDoctor = new Doctor();
        TimeSlot mockTimeSlot = new TimeSlot();
        mockTimeSlot.setBooked(false);

        when(patientRep.findById(1L)).thenReturn(Optional.of(mockPatient));
        when(docRep.findById(2L)).thenReturn(Optional.of(mockDoctor));
        when(tsRep.findById(3L)).thenReturn(Optional.of(mockTimeSlot));

        // Act
        appointmentService.bookAppointment(1L, 2L, 3L);

        // Assert
        verify(apptRepo, times(1)).save(any(Appointment.class));
        verify(tsRep, times(1)).save(mockTimeSlot);
        assertTrue(mockTimeSlot.isBooked());
    }

    @Test
    void bookAppointment_shouldThrowExceptionIfTimeSlotBooked() {
        // Arrange
        Patient mockPatient = new Patient();
        Doctor mockDoctor = new Doctor();
        TimeSlot mockTimeSlot = new TimeSlot();
        mockTimeSlot.setBooked(true);

        when(patientRep.findById(1L)).thenReturn(Optional.of(mockPatient));
        when(docRep.findById(2L)).thenReturn(Optional.of(mockDoctor));
        when(tsRep.findById(3L)).thenReturn(Optional.of(mockTimeSlot));

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () ->
                appointmentService.bookAppointment(1L, 2L, 3L));
        assertEquals("This time slot is already booked.", exception.getMessage());
    }

    @Test
    void pendingAppointments_shouldReturnPendingAppointments() {
        // Arrange
        List<Appointment> mockAppointments = new ArrayList<>();
        mockAppointments.add(new Appointment());
        when(apptRepo.findByDoctorAndStatus(1L, AppointmentStatus.PENDING))
                .thenReturn(mockAppointments);

        // Act
        List<Appointment> result = appointmentService.pendingAppointments(1L);

        // Assert
        assertEquals(1, result.size());
        verify(apptRepo, times(1))
                .findByDoctorAndStatus(1L, AppointmentStatus.PENDING);
    }

    @Test
    void findAllConfirmApts_shouldReturnConfirmedAppointments() {
        // Arrange
        List<Appointment> mockAppointments = new ArrayList<>();
        mockAppointments.add(new Appointment());
        when(apptRepo.findByStatus(AppointmentStatus.CONFIRMED))
                .thenReturn(mockAppointments);

        // Act
        List<Appointment> result = appointmentService.findAllConfirmApts(AppointmentStatus.CONFIRMED);

        // Assert
        assertEquals(1, result.size());
        verify(apptRepo, times(1))
                .findByStatus(AppointmentStatus.CONFIRMED);
    }
}
