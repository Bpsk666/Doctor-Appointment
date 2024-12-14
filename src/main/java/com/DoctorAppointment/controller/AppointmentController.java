package com.DoctorAppointment.controller;

import com.DoctorAppointment.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentService aptSer;
}
