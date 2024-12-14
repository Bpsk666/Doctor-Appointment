package com.DoctorAppointment.service;

import com.DoctorAppointment.model.Specialty;
import com.DoctorAppointment.repository.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialtyService {

    @Autowired
    private SpecialtyRepository specialRep;

    public void saveSpecial(Specialty s){
        specialRep.save(s);
    }
    public Specialty findSepcialtyById(long specialtyId){
        return specialRep.findById(specialtyId).get();
    }
    public List<Specialty> findAllSpecialty(){
        return specialRep.findAll();
    }

    public void updateSpecialty(long specialId, Specialty specialty){
        specialty.setSpecialId(specialId);
        specialRep.save(specialty);
    }
}
