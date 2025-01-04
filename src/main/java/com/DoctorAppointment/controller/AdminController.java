package com.DoctorAppointment.controller;

import com.DoctorAppointment.DTO.DoctorDTO;
import com.DoctorAppointment.enums.AppointmentStatus;
import com.DoctorAppointment.model.*;
import com.DoctorAppointment.repository.AppointmentRepository;
import com.DoctorAppointment.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/adminsys")
public class AdminController {

    @Autowired
    private AdminService adminSer;
    @Autowired
    private DoctorService docSer;
    @Autowired
    private SpecialtyService specialSer;
    @Autowired
    private TimeSlotService tsSer;
    @Autowired
    private AppointmentService aptSer;
    @Autowired
    private AppointmentRepository aptRep;
    @Autowired
    private HttpSession httpSession;

    @GetMapping("/adminindex")
    public String adminLoginPage() {
        return "Login";
    }
    @GetMapping("/adminHomePage")
    public String adminHomePage(Model model){
        Admin admin = (Admin) httpSession.getAttribute("admin");
        if(admin==null){
            return "Login";
        }
        model.addAttribute("admin",admin);
        return "adminDashboard";
    }
    @PostMapping("/adminLoginSuccess")
    public String adminLoginSuccess(@RequestParam("adminEmail")String adminEmail, @RequestParam("adminPassword")String adminPassword, Model model) {
        String res = adminSer.checkLogin(adminEmail,adminPassword);
        if(res.equals("redirect:/adminsys/adminHomePage")){
            httpSession.setAttribute("admin",adminSer.getAdmin(adminEmail,adminPassword));
            return "redirect:/adminsys/adminHomePage";
        }
        else{
            return "Login";
        }
    }
    @GetMapping("/addDoctor")
    public String addDoctor(Model model) {
        model.addAttribute("admin",httpSession.getAttribute("admin"));
        model.addAttribute("doctorDTO",new DoctorDTO());
        model.addAttribute("specialty",specialSer.findAllSpecialty());
        return "adminAddDoctor";
    }
    @PostMapping("/newDoc")
    public String newDoctor(@ModelAttribute("doctorDTO")DoctorDTO doctorDTO, Model model, @RequestParam("profileImage")MultipartFile file) {
        model.addAttribute("admin",httpSession.getAttribute("admin"));
        try{
            Doctor doctor = new Doctor();
            doctor.setDocFName(doctorDTO.getDocFName());
            doctor.setDocLName(doctorDTO.getDocLName());
            doctor.setDocAge(doctorDTO.getDocAge());
            doctor.setSpecialty(doctorDTO.getSpecialty());
            doctor.setQualification(doctorDTO.getQualification());
            doctor.setExperience(doctorDTO.getExperience());
            doctor.setDocPhone(doctorDTO.getDocPhone());
            doctor.setDocAddress(doctorDTO.getDocAddress());
            doctor.setDocEmail(doctorDTO.getDocEmail());
            doctor.setDocPassword(doctorDTO.getDocPassword());
            doctor.setAptFee(doctorDTO.getAptFee());

            if(doctorDTO.getProfileImage()!=null && !doctorDTO.getProfileImage().isEmpty()){
                doctor.setProfileImage(doctorDTO.getProfileImage().getBytes());
            }
            docSer.newDoc(doctor);
        }catch (IOException e){
            e.printStackTrace();
            model.addAttribute("error","Error uploading image");
            return "adminAddDoctor";
        }
        return "redirect:/adminsys/adminHomePage";
    }
    @GetMapping("/viewDoc")
    public String adminViewDoctor( Model model){
        model.addAttribute("admin",httpSession.getAttribute("admin"));
        model.addAttribute("doctors",docSer.getAllDoc());
        return "adminViewDoctors";
    }
    @GetMapping("/viewDocDetail/{docId}")
    public String viewDocDetail(@PathVariable("docId")long docId, Model model){
        model.addAttribute("admin",httpSession.getAttribute("admin"));
        Doctor doc = docSer.getDocById(docId);
        model.addAttribute("doc",doc);
        model.addAttribute("timeSlots",tsSer.getTimeSlotByDoctor(doc));
        return "adminViewDocDetail";
    }
    @GetMapping("/doctorImage/{docId}")
    @ResponseBody
    public ResponseEntity<byte[]>getDoctorImage(@PathVariable("docId")long docId){
        Doctor doc = docSer.getDocById(docId);
        if(doc==null || doc.getProfileImage()==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(doc.getProfileImage());
    }

    @GetMapping("/addSpecialty")
    public String addSpecialty(Model model) {
        model.addAttribute("admin",httpSession.getAttribute("admin"));
        model.addAttribute("specialty",new Specialty());
        return "adminAddSpecialty";
    }
    @PostMapping("/newSpecialty")
    public String newSpecialty(@ModelAttribute("specialty")Specialty specialty, Model model) {
        model.addAttribute("admin",httpSession.getAttribute("admin"));
        specialSer.saveSpecial(specialty);
        return "adminDashboard";
    }
    @GetMapping("/viewSpecialty")
    public String viewSpecialty(Model model){
        model.addAttribute("admin",httpSession.getAttribute("admin"));
        model.addAttribute("specialty",specialSer.findAllSpecialty());
        return "adminViewSpecialty";
    }
    @GetMapping("/updateSpecialty/{specialId}")
    public String updateSpecialty(@PathVariable("specialId")long specialId,Model model){
        model.addAttribute("admin",httpSession.getAttribute("admin"));
        model.addAttribute("special",specialSer.findSepcialtyById(specialId));
        return "somePage";
    }

    @PostMapping("/deleteDocProcess/{docId}")
    public String deleteProcess(@PathVariable("docId")long docId, Model model){
        model.addAttribute("admin",httpSession.getAttribute("admin"));
        docSer.deleteDoc(docId);
        return "redirect:/adminsys/viewDoc";
    }
    @GetMapping("/viewAllConfirm")
    public String viewAllConfirmApts(Model model){
        model.addAttribute("admin",httpSession.getAttribute("admin"));
        model.addAttribute("confirmApt",aptSer.findAllConfirmApts(AppointmentStatus.CONFIRMED));
        return "adminViewConfirm";
    }

    @PostMapping("/aptComplete/{aptId}")
    public String makeAptComplete(@PathVariable("aptId")long aptId, Model model){
        model.addAttribute("admin",httpSession.getAttribute("admin"));
        Appointment apt = aptSer.findAptById(aptId);
        if(apt==null){
            model.addAttribute("Error","No Appointment Found!");
        }
        apt.setStatus(AppointmentStatus.COMPLETED);
        aptRep.save(apt);
        return "adminDashboard";
    }

    @GetMapping("/viewAllApt")
    public String viewAllAppointments(Model model){
        model.addAttribute("admin",httpSession.getAttribute("admin"));
        model.addAttribute("allApt",aptSer.findAllApts());
        return "adminViewAllApts";
    }
}
