package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.AClass;
import com.t2307m.group1.prjsem2backend.model.Instructor;
import com.t2307m.group1.prjsem2backend.repositories.ClassRepository;
import com.t2307m.group1.prjsem2backend.repositories.InstructorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClassService {
    private final ClassRepository classRepository;
    private final InstructorRepository instructorRepository;
    @Autowired
    public ClassService(ClassRepository classRepository, InstructorRepository instructorRepository) {
        this.classRepository = classRepository;
        this.instructorRepository = instructorRepository;
    }

    @Transactional
    public AClass addClass(AClass aClass){
        return classRepository.save(aClass);
    }

    @Transactional
    public AClass updateClass(int id, AClass aClassUpdate) {
        Optional<AClass> classOpt = classRepository.findById(id);
        if (classOpt.isPresent()) {
            AClass aClass = classOpt.get();

            if (aClassUpdate.getCourse() != null) {
                aClass.setCourse(aClassUpdate.getCourse());
            }
            if (aClassUpdate.getInstructor() != null) {
                aClass.setInstructor(aClassUpdate.getInstructor());
            }
            if (aClassUpdate.getLocation() != null) {
                aClass.setLocation(aClassUpdate.getLocation());
            }
            if (aClassUpdate.getStartDate() != null) {
                aClass.setStartDate(aClassUpdate.getStartDate());
            }
            if (aClassUpdate.getEndDate() != null) {
                aClass.setEndDate(aClassUpdate.getEndDate());
            }
            if(aClassUpdate.getStatus() != null){
                aClass.setStatus(aClassUpdate.getStatus());
            }

            return classRepository.save(aClass);
        } else {
            throw new RuntimeException("Class not found");
        }
    }


    @Transactional
    public void deleteClass(int id){
        if (!classRepository.existsById(id)) throw new RuntimeException("Class not found");
        classRepository.deleteById(id);
    }

    public List<AClass> getAllClass(){
        return classRepository.findAll();
    }

    public Optional<AClass> findClassById(int id){
        return classRepository.findById(id);
    }

    public List<AClass> getClassByStatusIsStarted(){
        return classRepository.getAClassByStatusIsStarted();
    }

    public List<AClass> getClassByInstructorId(String email){
        Optional<Instructor> instructor = instructorRepository.findByEmail(email);

        if (instructor.isPresent()) {
            return classRepository.getAClassByInstructorId(instructor.get().getId());
        }
        return null;
    }

    public List<AClass> getClassesForToday() {
        // Lấy ngày hiện tại
        Date today = Date.valueOf(LocalDate.now());
        return classRepository.findClassesByDateAndStatus(today);
    }
}
