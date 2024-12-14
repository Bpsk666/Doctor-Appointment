package com.DoctorAppointment.service;

import com.DoctorAppointment.model.Patient;
import com.DoctorAppointment.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRep;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void savePatient(Patient patient){
        patientRep.save(patient);
    }
    public String checkLogin(String patientEmail, String patientPassword){
        Patient patient = patientRep.findByPatientEmail(patientEmail);
        if(patient!=null){
            if(passwordEncoder.matches(patientPassword,patient.getPatientPassword())){
                patientPassword=patient.getPatientPassword();
            }
            patient = patientRep.findByPatientEmailAndPatientPassword(patientEmail,patientPassword);
            if(patient==null){
                return "homePage";
            }
        }
        return "redirect:/patientSys/patientHomePage";
    }
    public Patient getPatient(String patientEmail, String patientPassword){
        return patientRep.findByPatientEmailAndPatientPassword(patientEmail,patientPassword);
    }
}
