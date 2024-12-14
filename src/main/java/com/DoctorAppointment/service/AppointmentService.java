package com.DoctorAppointment.service;

import com.DoctorAppointment.enums.AppointmentStatus;
import com.DoctorAppointment.model.Appointment;
import com.DoctorAppointment.model.Doctor;
import com.DoctorAppointment.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository apptRepo;

    public void saveApt(Appointment apt){
        apptRepo.save(apt);
    }

    public List<Appointment> getAptByDocAndStatus(Doctor doctor, AppointmentStatus status){
        return apptRepo.findByDoctorAndStatus(doctor,status);
    }

    public List<Appointment> pendingAppointments(Doctor doctor){
        return getAptByDocAndStatus(doctor, AppointmentStatus.PENDING);
    }

    public List<Appointment> completedAppointments(Doctor doctor){
        return getAptByDocAndStatus(doctor, AppointmentStatus.COMPLETED);
    }

    public List<Appointment> confirmedAppointments(Doctor doctor){
        return getAptByDocAndStatus(doctor, AppointmentStatus.CONFIRMED);
    }

    public List<Appointment> cancelledAppointments(Doctor doctor){
        return getAptByDocAndStatus(doctor, AppointmentStatus.CANCELLED);
    }
}
