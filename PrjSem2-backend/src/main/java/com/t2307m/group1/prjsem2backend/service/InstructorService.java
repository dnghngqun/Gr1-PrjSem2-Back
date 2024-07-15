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

    public boolean updateInstructor(String email, Instructor instructor) {
        Optional<Instructor> instructorOpt = findIntructorByEmail(email);
        if (instructorOpt.isEmpty()) {
            return false;
        }

        Instructor newInstructor = instructorOpt.get();

        if (instructor.getName() != null) {
            newInstructor.setName(instructor.getName());
        }
        if (instructor.getBio() != null) {
            newInstructor.setBio(instructor.getBio());
        }
        if (instructor.getGender() != null) {
            newInstructor.setGender(instructor.getGender());
        }
        if (instructor.getPhoneNumber() != null) {
            newInstructor.setPhoneNumber(instructor.getPhoneNumber());
        }

        instructorRepository.save(newInstructor);
        return true;
    }


    public List<Instructor> getAllInstructor(){
        return instructorRepository.findAll();
    }
}
