package com.DoctorAppointment;

import com.DoctorAppointment.model.Doctor;
import com.DoctorAppointment.model.TimeSlot;
import com.DoctorAppointment.repository.TimeSlotRepository;
import com.DoctorAppointment.service.TimeSlotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TimeSlotServiceTest {

    @Mock
    private TimeSlotRepository timeSlotRepository;

    @InjectMocks
    private TimeSlotService timeSlotService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveTimeSlot_shouldSaveTimeSlot() {
        TimeSlot timeSlot = new TimeSlot("10:00 AM", "11:00 AM", LocalDate.now(), new Doctor(), false);

        timeSlotService.saveTimeSlot(timeSlot);

        verify(timeSlotRepository, times(1)).save(timeSlot);
    }

    @Test
    void getAllTimeSlots_shouldReturnListOfTimeSlots() {
        TimeSlot timeSlot1 = new TimeSlot("10:00 AM", "11:00 AM", LocalDate.now(), new Doctor(), false);
        TimeSlot timeSlot2 = new TimeSlot("11:00 AM", "12:00 PM", LocalDate.now(), new Doctor(), false);

        when(timeSlotRepository.findAll()).thenReturn(Arrays.asList(timeSlot1, timeSlot2));

        List<TimeSlot> result = timeSlotService.getAllTimeSlots();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("10:00 AM", result.get(0).getStartTime());
        assertEquals("12:00 PM", result.get(1).getEndTime());
        verify(timeSlotRepository, times(1)).findAll();
    }

    @Test
    void getTimeSlotByDoctor_shouldReturnTimeSlotsForDoctor() {
        Doctor doctor = new Doctor();
        doctor.setDocId(1L);
        doctor.setDocFName("John");

        TimeSlot timeSlot1 = new TimeSlot("10:00 AM", "11:00 AM", LocalDate.now(), doctor, false);
        TimeSlot timeSlot2 = new TimeSlot("11:00 AM", "12:00 PM", LocalDate.now(), doctor, true);

        when(timeSlotRepository.findByDoctor(doctor)).thenReturn(Arrays.asList(timeSlot1, timeSlot2));

        List<TimeSlot> result = timeSlotService.getTimeSlotByDoctor(doctor);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("10:00 AM", result.get(0).getStartTime());
        assertEquals("12:00 PM", result.get(1).getEndTime());
        assertFalse(result.get(0).isBooked());
        assertTrue(result.get(1).isBooked());
        verify(timeSlotRepository, times(1)).findByDoctor(doctor);
    }

    @Test
    void getTimeSlotByDoctor_shouldThrowExceptionIfDoctorIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            timeSlotService.getTimeSlotByDoctor(null);
        });

        assertEquals("Doctor cannot be null", exception.getMessage());
        verify(timeSlotRepository, never()).findByDoctor(any());
    }
}
