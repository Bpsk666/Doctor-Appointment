package com.DoctorAppointment.service;

import com.DoctorAppointment.model.Doctor;
import com.DoctorAppointment.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository docRep;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void newDoc(Doctor doc)
    {
        docRep.save(doc);
    }

    public String checkLogin(String docEmail, String docPassword)
    {
        Doctor doc = docRep.findByDocEmail(docEmail);
        if(doc==null)
        {
            return "Login";
        }
        if(passwordEncoder.matches(docPassword, doc.getDocPassword())){
            docPassword = doc.getDocPassword();
        }
        doc = docRep.findByDocEmailAndDocPassword(docEmail,docPassword);
        if(doc==null)
        {
            return "Login";
        }
        return "docDashboard";
    }

    public Doctor getDoctor(String docEmail, String docPassword)
    {
        return docRep.findByDocEmailAndDocPassword(docEmail, docPassword);
    }

    public List<Doctor> getAllDoc()
    {
        return docRep.findAll();
    }

    public Doctor getDocById(long docId){
        return docRep.findById(docId).get();
    }

    public void updateDoctor(long docId, Doctor doc){
        Optional<Doctor> doctor = docRep.findById(docId);
        if(doctor.isPresent()){
            Doctor existingDoc = doctor.get();
            existingDoc.setDocFName(doc.getDocFName());
            existingDoc.setDocLName(doc.getDocLName());
            existingDoc.setDocAge(doc.getDocAge());
            existingDoc.setSpecialty(doc.getSpecialty());
            existingDoc.setQualification(doc.getQualification());
            existingDoc.setExperience(doc.getExperience());
            existingDoc.setDocPhone(doc.getDocPhone());
            existingDoc.setDocEmail(doc.getDocEmail());
            existingDoc.setDocPassword(doc.getDocPassword());
            if(doc.getProfileImage()!=null){
                existingDoc.setProfileImage(doc.getProfileImage());
            }
            docRep.save(existingDoc);
        }
        else{
            throw new RuntimeException("Doctor with ID "+docId+" not found!");
        }
    }
    public List<Doctor> getDocBySpecialty(String specialty){
        return docRep.findBySpecialty(specialty);
    }

    public void deleteDoc(long docId){
        docRep.deleteById(docId);
    }
}
