package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.Account;
import com.t2307m.group1.prjsem2backend.model.Instructor;
import com.t2307m.group1.prjsem2backend.repositories.AccountRepository;
import com.t2307m.group1.prjsem2backend.repositories.InstructorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final AccountRepository accountRepository;
    @Autowired
    public InstructorService(InstructorRepository instructorRepository, AccountRepository accountRepository) {
        this.instructorRepository = instructorRepository;
        this.accountRepository = accountRepository;
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

        if (instructor.getName() != null) newInstructor.setName(instructor.getName());
        if (instructor.getBio() != null) newInstructor.setBio(instructor.getBio());
        if(instructor.getImageLink() != null) newInstructor.setImageLink(instructor.getImageLink());
        if (instructor.getGender() != null) newInstructor.setGender(instructor.getGender());
        if (instructor.getPhoneNumber() != null) newInstructor.setPhoneNumber(instructor.getPhoneNumber());
        if(instructor.getClassify() != null) newInstructor.setClassify(instructor.getClassify());

        instructorRepository.save(newInstructor);
        return true;
    }


    public List<Instructor> getAllInstructor(){
        return instructorRepository.findAll();
    }

    @Transactional
    public void deleteInstructor(String email) {
        Optional<Account> account = accountRepository.findByEmail(email);
        if (account.isPresent()) {
            accountRepository.delete(account.get());
            instructorRepository.deleteByEmail(email);
        }
        else {
            instructorRepository.deleteByEmail(email);
        }
    }
}
