package com.DoctorAppointment.service;

import com.DoctorAppointment.enums.AppointmentStatus;
import com.DoctorAppointment.model.Appointment;
import com.DoctorAppointment.model.Doctor;
import com.DoctorAppointment.model.Patient;
import com.DoctorAppointment.model.TimeSlot;
import com.DoctorAppointment.repository.AppointmentRepository;
import com.DoctorAppointment.repository.DoctorRepository;
import com.DoctorAppointment.repository.PatientRepository;
import com.DoctorAppointment.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private PatientRepository patientRep;
    @Autowired
    private DoctorRepository docRep;
    @Autowired
    private TimeSlotRepository tsRep;
    @Autowired
    private AppointmentRepository apptRepo;

    public List<Appointment> findAllApts(){
        return apptRepo.findAll();
    }
    public Appointment findAptById(long aptId){
        return apptRepo.findById(aptId).get();
    }
    public List<Appointment> findAptByPatient(Patient patient){
        return apptRepo.findByPatient(patient);
    }
    public List<Appointment> findAptByDoctor(Doctor doctor){
        return apptRepo.findByDoctor(doctor);
    }
    public void cancelApt(long aptId){
        apptRepo.deleteById(aptId);
    }
    public void bookAppointment(long patientId, long doctorId, long timeSlotId) throws Exception {
        Patient patient = patientRep.findById(patientId).get();
        Doctor doctor = docRep.findById(doctorId).get();
        TimeSlot timeSlot = tsRep.findById(timeSlotId).get();
        if(timeSlot.isBooked()){
            throw new Exception("This time slot is already booked.");
        }
        Appointment appointment = new Appointment(patient,doctor,timeSlot,AppointmentStatus.PENDING);
        apptRepo.save(appointment);
        timeSlot.setBooked(true);
        tsRep.save(timeSlot);
    }

    public List<Appointment> getAptByDocAndStatus(long doctorId, AppointmentStatus status){
        return apptRepo.findByDoctorAndStatus(doctorId,status);
    }

    public List<Appointment> pendingAppointments(long doctorId){
        return getAptByDocAndStatus(doctorId, AppointmentStatus.PENDING);
    }

    public List<Appointment> completedAppointments(long doctorId){
        return getAptByDocAndStatus(doctorId, AppointmentStatus.COMPLETED);
    }

    public List<Appointment> confirmedAppointments(long doctorId){
        return getAptByDocAndStatus(doctorId, AppointmentStatus.CONFIRMED);
    }

    public List<Appointment> cancelledAppointments(long doctorId){
        return getAptByDocAndStatus(doctorId, AppointmentStatus.CANCELLED);
    }

    public List<Appointment> findAllConfirmApts(AppointmentStatus confirmed){
        return apptRepo.findByStatus(AppointmentStatus.CONFIRMED);
    }
}
