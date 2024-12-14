package com.DoctorAppointment.controller;

import com.DoctorAppointment.model.Doctor;
import com.DoctorAppointment.model.TimeSlot;
import com.DoctorAppointment.repository.TimeSlotRepository;
import com.DoctorAppointment.service.DoctorService;
import com.DoctorAppointment.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/timeSlots")
public class TimeSlotController {
    @Autowired
    private TimeSlotService tsSer;

    @Autowired
    private DoctorService docSer;

    @GetMapping("/tsByDoc/{docId}")
    public ResponseEntity<?> getTimeSlotsByDoctor(@PathVariable("docId") long docId) {
        try {
            Doctor doc = docSer.getDocById(docId);
            if (doc == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found for ID: " + docId);
            }
            List<TimeSlot> timeSlots = tsSer.getTimeSlotByDoctor(doc);
            return ResponseEntity.ok(timeSlots);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
}
