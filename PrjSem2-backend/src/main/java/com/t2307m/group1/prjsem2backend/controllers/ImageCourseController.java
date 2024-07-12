package com.t2307m.group1.prjsem2backend.controllers;

import com.t2307m.group1.prjsem2backend.model.ImageCourse;
import com.t2307m.group1.prjsem2backend.model.ResponseObject;
import com.t2307m.group1.prjsem2backend.service.ImageCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/imageCourse")
public class ImageCourseController {
    private final ImageCourseService imageCourseService;
    @Autowired
    public ImageCourseController(ImageCourseService imageCourseService) {
        this.imageCourseService = imageCourseService;
    }



    @PostMapping("/")
    public ResponseEntity<ResponseObject> createImageCourse(@RequestBody ImageCourse imageCourse){
        try {
            ImageCourse newImageCourse = imageCourseService.saveImageCourse(imageCourse);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Create ImageCourse successfully!", newImageCourse)
            );
        }catch (Exception e){
            System.err.println("Exception: "+ e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("Failed", "Create ImageCourse error!", "")
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateImageCourse(@PathVariable int id, @RequestBody ImageCourse imageCourseUpdate){
        Optional<ImageCourse> updatedImageCourse = imageCourseService.getImageCourseById(id)
                .map(existingImageCourse -> {
                    existingImageCourse.setPath(imageCourseUpdate.getPath());
                    existingImageCourse.setCourse(imageCourseUpdate.getCourse());
                    return imageCourseService.updateImageCourse(existingImageCourse);
                });

        return updatedImageCourse.map(imageCourse -> ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Update ImageCourse successfully!", imageCourse)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("Failed", "ImageCourse not found!", "")
                ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteImageCourse(@PathVariable int id) {
        try {
            imageCourseService.deleteImageCourse(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete ImageCourse successfully!", "")
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("Failed", "Delete ImageCourse error!", "")
            );
        }
    }

    @GetMapping("/")
    public ResponseEntity<ResponseObject> getAllImageCourses() {
        List<ImageCourse> imageCourses = imageCourseService.getAllImageCourses();
        return !imageCourses.isEmpty() ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query all ImageCourses successfully", imageCourses))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("Failed", "No ImageCourses found", "")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getImageCourseById(@PathVariable int id) {
        Optional<ImageCourse> imageCourse = imageCourseService.getImageCourseById(id);
        return imageCourse.map(value -> ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query ImageCourse by ID successfully", value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("Failed", "ImageCourse not found", "")
                ));
    }
}
