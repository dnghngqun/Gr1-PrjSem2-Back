package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.AClass;
import com.t2307m.group1.prjsem2backend.repositories.ClassRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassService {
    private final ClassRepository classRepository;

    @Autowired
    public ClassService(ClassRepository classRepository) {
        this.classRepository = classRepository;
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
}
