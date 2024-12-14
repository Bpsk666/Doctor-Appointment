package com.DoctorAppointment.controller;

import com.DoctorAppointment.model.Doctor;
import com.DoctorAppointment.model.Patient;
import com.DoctorAppointment.service.DoctorService;
import com.DoctorAppointment.service.PatientService;
import com.DoctorAppointment.service.SpecialtyService;
import com.DoctorAppointment.service.TimeSlotService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
    private HttpSession session;

    @GetMapping("/homePage")
    public String homePage(Model model){
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
    public String patientLogin(@RequestParam("patientEmail")String patientEmail, @RequestParam("patientPassword")String patientPassword,Model model){
        String res = patientSer.checkLogin(patientEmail,patientPassword);
        if(res.equals("redirect:/patientSys/patientHomePage")){
            model.addAttribute("patient",patientSer.getPatient(patientEmail,patientPassword));
            Patient patient = patientSer.getPatient(patientEmail,patientPassword);
            session.setAttribute("patient",patient);
            session.setAttribute("patientId",patient.getPatientId());
        }
        else{
            return "homePage";
        }
        return "redirect:/patientSys/patientHomePage";
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
}
