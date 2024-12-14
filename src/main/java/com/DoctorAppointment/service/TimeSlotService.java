package com.DoctorAppointment.service;

import com.DoctorAppointment.model.Doctor;
import com.DoctorAppointment.model.TimeSlot;
import com.DoctorAppointment.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeSlotService {
    @Autowired
    private TimeSlotRepository tsRep;

    public void saveTimeSlot(TimeSlot timeSlot){
        tsRep.save(timeSlot);
    }
    public List<TimeSlot> getAllTimeSlots(){
        return tsRep.findAll();
    }

    public List<TimeSlot> getTimeSlotByDoctor(Doctor doc){
        if(doc==null){
            throw new IllegalArgumentException("Doctor cannot be null");
        }
        return tsRep.findByDoctor(doc);
    }
}
