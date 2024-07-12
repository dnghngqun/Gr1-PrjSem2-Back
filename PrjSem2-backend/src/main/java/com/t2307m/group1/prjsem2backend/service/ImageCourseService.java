package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.ImageCourse;
import com.t2307m.group1.prjsem2backend.repositories.ImageCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageCourseService {
    private final ImageCourseRepository imageCourseRepository;

    @Autowired
    public ImageCourseService(ImageCourseRepository imageCourseRepository) {
        this.imageCourseRepository = imageCourseRepository;
    }
    public ImageCourse saveImageCourse(ImageCourse imageCourse) {
        return imageCourseRepository.save(imageCourse);
    }

    public Optional<ImageCourse> getImageCourseById(int id) {
        return imageCourseRepository.findById(id);
    }

    public List<ImageCourse> getAllImageCourses() {
        return imageCourseRepository.findAll();
    }

    public ImageCourse updateImageCourse(ImageCourse imageCourse) {
        return imageCourseRepository.save(imageCourse);
    }

    public void deleteImageCourse(int id) {
        imageCourseRepository.deleteById(id);
    }
}
