package com.DoctorAppointment.controller;

import com.DoctorAppointment.service.AppointmentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/aptSys")
public class AppointmentController {

    @Autowired
    private AppointmentService aptSer;

    @Autowired
    private HttpSession session;
    @PostMapping("/bookApt")
    public String bookAppointment(@RequestParam("patientId") long patientId, @RequestParam("doctorId") long doctorId, @RequestParam("timeSlotId") long timeSlotId, Model model){
        Long patient = (Long) session.getAttribute("patientId");
        try{
            aptSer.bookAppointment(patientId,doctorId,timeSlotId);
            return "redirect:/patientSys/patientHomePage";
        }
        catch (Exception e){
            return "error: "+e.getMessage();
        }
    }
}
