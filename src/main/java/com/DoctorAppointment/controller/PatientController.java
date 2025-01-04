package com.DoctorAppointment.controller;

import com.DoctorAppointment.model.Appointment;
import com.DoctorAppointment.model.Doctor;
import com.DoctorAppointment.model.Patient;
import com.DoctorAppointment.service.*;
import jakarta.servlet.http.HttpSession;
import org.hibernate.annotations.NaturalId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patientSys")
public class PatientController {

    @Autowired
    private PatientService patientSer;
    @Autowired
    private DoctorService docSer;
    @Autowired
    private SpecialtyService specialSer;
    @Autowired
    private TimeSlotService tsSer;
    @Autowired
    private AppointmentService aptSer;
    @Autowired
    private HttpSession session;

    @GetMapping("/homePage")
    public String homePage(Model model){
        session.removeAttribute("showError");
        model.addAttribute("showError",false);
        model.addAttribute("patient",new Patient());
        return "homePage";
    }
    @PostMapping("/newPatient")
    public String newPatient(@ModelAttribute("patient")Patient patient){
        patientSer.savePatient(patient);
        session.setAttribute("patient",patient);
        return "patientHomePage";
    }
    @GetMapping("/patientHomePage")
    private String patientHomePage(Model model){
        Patient patient = (Patient) session.getAttribute("patient");
        if(patient==null){
            return "homePage";
        }
        model.addAttribute("patient",patient);
        return "patientHomePage";
    }
    @PostMapping("/patientLogin")
    public String patientLogin(@RequestParam("patientEmail") String patientEmail,
                               @RequestParam("patientPassword") String patientPassword,
                               Model model) {
        String res = patientSer.checkLogin(patientEmail, patientPassword);
        if (res.equals("redirect:/patientSys/patientHomePage")) {
            Patient patient = patientSer.getPatient(patientEmail, patientPassword);
            session.setAttribute("patient", patient);
            session.setAttribute("patientId", patient.getPatientId());
            return "redirect:/patientSys/patientHomePage";
        } else {
            model.addAttribute("patient",new Patient());
            model.addAttribute("showError", true);
            model.addAttribute("errorMessage", "Invalid email or password. Please try again.");
            return "homePage";
        }
    }
    @GetMapping("/viewDoctors")
    public String viewDocs(Model model){
        model.addAttribute("patient",session.getAttribute("patient"));
        model.addAttribute("doctors",docSer.getAllDoc());
        model.addAttribute("specialty",specialSer.findAllSpecialty());
        return "patientViewDoctors";
    }

    @GetMapping("/viewDoctorsBySpecialty/{specialty}")
    public String viewDocsBySpecialty(@PathVariable("specialty")String specialty,Model model){
        model.addAttribute("patient",session.getAttribute("patient"));
        model.addAttribute("docSpecialty",docSer.getDocBySpecialty(specialty));
        model.addAttribute("specialty",specialSer.findAllSpecialty());
        return "patientViewDocSpecialty";
    }

    @GetMapping("/viewDoctorDetail/{docId}")
    public String viewDocDetail(@PathVariable("docId")long docId, Model model){
        model.addAttribute("patient",session.getAttribute("patient"));
        Doctor doc = docSer.getDocById(docId);
        model.addAttribute("doc",doc);
        model.addAttribute("timeSlots",tsSer.getTimeSlotByDoctor(doc));
        return "patientViewDocDetail";
    }

    @GetMapping("/viewApt/{patientId}")
    public String viewAppointments(@PathVariable("patientId")long patientId, Model model){
        model.addAttribute("patient",session.getAttribute("patient"));
        Patient patient = patientSer.findPatientById(patientId);
        model.addAttribute("allApt",aptSer.findAptByPatient(patient));
        return "patientViewAppointments";
    }
    @PostMapping("/cancelApt/{aptId}")
    public String cancelAppointments(@PathVariable("aptId")long aptId, Model model){
        model.addAttribute("patient",session.getAttribute("patient"));
        aptSer.cancelApt(aptId);
        return "patientHomePage";
    }
}
