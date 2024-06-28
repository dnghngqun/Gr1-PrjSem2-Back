package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.Class;
import com.t2307m.group1.prjsem2backend.model.Course;
import com.t2307m.group1.prjsem2backend.model.Instructor;
import com.t2307m.group1.prjsem2backend.repositories.ClassRepository;
import com.t2307m.group1.prjsem2backend.repositories.CourseRepository;
import com.t2307m.group1.prjsem2backend.repositories.InstructorRepository;
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
    public Class addClass(Class aClass){
        return classRepository.save(aClass);
    }

    @Transactional
    public Class updateClass(int id, Class classUpdate){
        Optional<Class> classOpt = classRepository.findById(id);
        if (classOpt.isPresent()){
            Class aClass = classOpt.get();
            aClass.setCourse(classUpdate.getCourse());
            aClass.setInstructor(classUpdate.getInstructor());
            aClass.setLocation(classUpdate.getLocation());
            aClass.setStartDate(classUpdate.getStartDate());
            aClass.setEndDate(classUpdate.getEndDate());
            return classRepository.save(aClass);
        }else {
            throw new RuntimeException("Class not found");
        }
    }

    @Transactional
    public void deleteClass(int id){
        if (!classRepository.existsById(id)) throw new RuntimeException("Class not found");
        classRepository.deleteById(id);
    }

    public List<Class> getAllClass(){
        return classRepository.findAll();
    }

    public Optional<Class> findClassById(int id){
        return classRepository.findById(id);
    }

}
