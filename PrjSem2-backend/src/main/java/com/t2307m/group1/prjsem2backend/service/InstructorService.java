package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.Instructor;
import com.t2307m.group1.prjsem2backend.repositories.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {
    private final InstructorRepository instructorRepository;

    @Autowired
    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    public Instructor addInstructor(Instructor instructor){
        return instructorRepository.save(instructor);
    }

    public Optional<Instructor> findIntructorByEmail(String email){
        Optional<Instructor> instructorOptional = instructorRepository.findByEmail(email);
        return instructorOptional;
    }

    public boolean updateInstructor(String email,Instructor instructor){
        if (findIntructorByEmail(email).isEmpty()) return false;
        Instructor newInstructor = findIntructorByEmail(email).get();
        newInstructor.setName(instructor.getName());
        newInstructor.setBio(instructor.getBio());
        newInstructor.setGender(instructor.getGender());
        newInstructor.setPhoneNumber(instructor.getPhoneNumber());
        instructorRepository.save(newInstructor);
        return true;
    }

    public List<Instructor> getAllInstructor(){
        return instructorRepository.findAll();
    }
}
