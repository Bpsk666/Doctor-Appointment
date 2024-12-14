package com.DoctorAppointment.controller;

import com.DoctorAppointment.DTO.DoctorDTO;
import com.DoctorAppointment.model.Doctor;
import com.DoctorAppointment.model.TimeSlot;
import com.DoctorAppointment.service.DoctorService;
import com.DoctorAppointment.service.SpecialtyService;
import com.DoctorAppointment.service.TimeSlotService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/docsys")
public class DoctorController {

    @Autowired
    private DoctorService docSer;
    @Autowired
    private SpecialtyService specialSer;
    @Autowired
    private TimeSlotService tsSer;
    @Autowired
    private HttpSession session;

    @PostMapping("/docLoginSuccess")
    public String docLoginSuccess(@RequestParam("docEmail")String docEmail, @RequestParam("docPassword")String docPassword, Model model)
    {
        model.addAttribute("doc",docSer.getDoctor(docEmail,docPassword));
        session.setAttribute("doc",docSer.getDoctor(docEmail,docPassword));
        return docSer.checkLogin(docEmail,docPassword);
    }

    @GetMapping("/updateDoctor/{docId}")
    public String updateDoctor(@PathVariable("docId")long docId, Model model){
        model.addAttribute("doctor",docSer.getDocById(docId));
        model.addAttribute("specialty",specialSer.findAllSpecialty());
        return "doctorInfoUpdate";
    }
    @PostMapping("/doctorUpdateProcess/{docId}")
    public String doctorUpdateProcess(@PathVariable("docId")long docId, @ModelAttribute("doctorDTO") DoctorDTO doctorDTO, @RequestParam(value = "profileImage", required = false) MultipartFile file, Model model){
        model.addAttribute("doc",session.getAttribute("doc"));
        try{
            Doctor existingDoc = docSer.getDocById(docId);
            if(existingDoc==null){
                model.addAttribute("error","Doctor not found!");
                return "doctorInfoUpdate";
            }
            existingDoc.setDocFName(doctorDTO.getDocFName());
            existingDoc.setDocLName(doctorDTO.getDocLName());
            existingDoc.setDocAge(doctorDTO.getDocAge());
            existingDoc.setSpecialty(doctorDTO.getSpecialty());
            existingDoc.setExperience(doctorDTO.getExperience());
            existingDoc.setQualification(doctorDTO.getQualification());
            existingDoc.setDocAddress(doctorDTO.getDocAddress());
            existingDoc.setDocPhone(doctorDTO.getDocPhone());
            existingDoc.setDocEmail(doctorDTO.getDocEmail());
            existingDoc.setDocPassword(doctorDTO.getDocPassword());
            if(file!=null && !file.isEmpty()){
                existingDoc.setProfileImage(file.getBytes());
            }
            docSer.updateDoctor(docId,existingDoc);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error","Error uploading image");
            return "doctorInfoUpdate";
        }
        return "docDashboard";
    }
    @GetMapping("/addTimeSlot/{docId}")
    public String docAddTimeSlot(@PathVariable("docId")long docId, Model model){
        model.addAttribute("doc",session.getAttribute("doc"));
        model.addAttribute("doc",docSer.getDocById(docId));
        model.addAttribute("timeSlot",new TimeSlot());
        return "docAddTimeSlot";
    }

    @PostMapping("/newTimeSlot/{docId}")
    public String newTimeSlot(@PathVariable("docId")long docId, @ModelAttribute("timeSlot")TimeSlot timeSlot, Model model){
        model.addAttribute("doc",session.getAttribute("doc"));
        Doctor doctor = docSer.getDocById(docId);
        timeSlot.setDoctor(doctor);
        tsSer.saveTimeSlot(timeSlot);
        return "docDashboard";
    }

    @GetMapping("/viewTimeSlots/{docId}")
    public String viewTimeSlots(@PathVariable("docId")long docId, Model model){
        model.addAttribute("doc",session.getAttribute("doc"));
        Doctor doctor = docSer.getDocById(docId);
        model.addAttribute("timeSlots",tsSer.getTimeSlotByDoctor(doctor));
        return "docViewTimeSlots";
    }
}
