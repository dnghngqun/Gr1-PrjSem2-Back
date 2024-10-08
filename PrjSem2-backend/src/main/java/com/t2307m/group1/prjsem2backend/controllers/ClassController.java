package com.t2307m.group1.prjsem2backend.controllers;

import com.t2307m.group1.prjsem2backend.model.AClass;
import com.t2307m.group1.prjsem2backend.model.ResponseObject;
import com.t2307m.group1.prjsem2backend.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/class")
public class ClassController {
    private final ClassService classService;

    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @PostMapping("/")
    public ResponseEntity<ResponseObject> addClass(@RequestBody AClass aClass){
        try {
            AClass newAClass = classService.addClass(aClass);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Add Class Successfully!", newAClass)
            );
        }catch (Exception e){
            System.err.println("Exception: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("Failed", "Add Class Error!", "")
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateClass(@PathVariable int id, @RequestBody AClass aClassUpdate){
        try{
            AClass updateAClass = classService.updateClass(id, aClassUpdate);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Update Class Successfully!", updateAClass)
            );
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", e.getMessage(), "")
            );
        }
    }

    @GetMapping("/")
    public ResponseEntity<ResponseObject> getAllClass(){
        try {
            List<AClass> allAClasses = classService.getAllClass();
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Get all class successfully!", allAClasses)
            );
        }catch (Exception e){
            System.err.println("Exception: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("failed", "Get all class error!", "")
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteClass(@PathVariable int id){
        try {
            classService.deleteClass(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete Class Successfully!", "")
            );
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("failed", e.getMessage(), "")
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findClassById(@PathVariable int id){
        Optional<AClass> classOptional = classService.findClassById(id);
        return classOptional.map(value -> ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Find class by id successfully!", value)
        )).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseObject("Failed", "Find class by id error!","")
        ));
    }

    @GetMapping("/status/started")
    public ResponseEntity<ResponseObject> getClassByStatusIsStarted(){
        List<AClass> classes = classService.getClassByStatusIsStarted();
        if(classes.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Class not found!", "")
            );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Get class by status is started successfully!", classes)
        );
    }
    @GetMapping("/instructor/{email}")
    public ResponseEntity<ResponseObject> getClassByInstructor(@PathVariable String email){
        List<AClass> classes = classService.getClassByInstructorId(email);
        if (classes.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Class not found!", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Get class by instructor successfully!", classes)
        );
    }
    @GetMapping("/today")
    public List<AClass> getClassesForToday() {
        return classService.getClassesForToday();
    }
}
