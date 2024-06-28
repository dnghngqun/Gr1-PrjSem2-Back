package com.t2307m.group1.prjsem2backend.controllers;

import com.t2307m.group1.prjsem2backend.model.Class;
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
    public ResponseEntity<ResponseObject> addClass(@RequestBody Class aClass){
        try {
            Class newClass = classService.addClass(aClass);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Add Class Successfully!", newClass)
            );
        }catch (Exception e){
            System.err.println("Exception: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("Failed", "Add Class Error!", "")
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateClass(@PathVariable int id, @RequestBody Class classUpdate){
        try{
            Class updateClass = classService.updateClass(id, classUpdate);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Update Class Successfully!", updateClass)
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
            List<Class> allClass = classService.getAllClass();
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Get all class successfully!", allClass)
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
        Optional<Class> classOptional = classService.findClassById(id);
        return classOptional.map(value -> ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Find class by id successfully!", value)
        )).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseObject("Failed", "Find class by id error!","")
        ));
    }
}
