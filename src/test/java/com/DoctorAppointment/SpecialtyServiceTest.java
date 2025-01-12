package com.DoctorAppointment;

import com.DoctorAppointment.model.Specialty;
import com.DoctorAppointment.repository.SpecialtyRepository;
import com.DoctorAppointment.service.SpecialtyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpecialtyServiceTest {

    @Mock
    private SpecialtyRepository specialtyRepository;

    @InjectMocks
    private SpecialtyService specialtyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveSpecial_shouldSaveSpecialty() {
        Specialty specialty = new Specialty();
        specialty.setSpecialtyField("Cardiology");

        specialtyService.saveSpecial(specialty);

        verify(specialtyRepository, times(1)).save(specialty);
    }

    @Test
    void findSpecialtyById_shouldReturnSpecialtyWhenExists() {
        Specialty specialty = new Specialty();
        specialty.setSpecialId(1L);
        specialty.setSpecialtyField("Neurology");

        when(specialtyRepository.findById(1L)).thenReturn(Optional.of(specialty));

        Specialty result = specialtyService.findSepcialtyById(1L);

        assertNotNull(result);
        assertEquals("Neurology", result.getSpecialtyField());
        verify(specialtyRepository, times(1)).findById(1L);
    }

    @Test
    void findSpecialtyById_shouldThrowExceptionWhenNotExists() {
        when(specialtyRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> specialtyService.findSepcialtyById(1L));
        verify(specialtyRepository, times(1)).findById(1L);
    }

    @Test
    void findAllSpecialty_shouldReturnListOfSpecialties() {
        Specialty specialty1 = new Specialty();
        specialty1.setSpecialtyField("Orthopedics");
        Specialty specialty2 = new Specialty();
        specialty2.setSpecialtyField("Dermatology");

        when(specialtyRepository.findAll()).thenReturn(Arrays.asList(specialty1, specialty2));

        List<Specialty> result = specialtyService.findAllSpecialty();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Orthopedics", result.get(0).getSpecialtyField());
        assertEquals("Dermatology", result.get(1).getSpecialtyField());
        verify(specialtyRepository, times(1)).findAll();
    }

    @Test
    void updateSpecialty_shouldUpdateSpecialty() {
        Specialty existingSpecialty = new Specialty();
        existingSpecialty.setSpecialId(1L);
        existingSpecialty.setSpecialtyField("Pediatrics");

        Specialty updatedSpecialty = new Specialty();
        updatedSpecialty.setSpecialtyField("Psychiatry");

        when(specialtyRepository.save(any(Specialty.class))).thenReturn(updatedSpecialty);

        specialtyService.updateSpecialty(1L, updatedSpecialty);

        assertEquals(1L, updatedSpecialty.getSpecialId());
        assertEquals("Psychiatry", updatedSpecialty.getSpecialtyField());
        verify(specialtyRepository, times(1)).save(updatedSpecialty);
    }
}
