
package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.Course;
import com.t2307m.group1.prjsem2backend.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
//    yêu cầu spring tiêm 1 instance cụ thể của CourseRepository vào CourseService
    private CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
        // method trả về ds cách dt của course ,trong return findAll() lấy tất cả các khóa học từ csdl
    }

    public Optional<Course> getCourseById(int id) {
        return courseRepository.findById(id);
        // Optional xử lý gt 1 các an toàn ( nó có thể có hoặc là ko)
    }

    public Course addCourse(Course course) {
        return courseRepository.save(course);
        // nhận 1 obj course và luu vào csdl
    }

    public Course updateCourse(int id, Course courseDetails) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        // tìm course theo id nếu ko tìm dc sẽ ném ra ngoại lệ
        course.setName(courseDetails.getName());
        course.setPrice(courseDetails.getPrice());
        course.setStatus(courseDetails.getStatus());
        //cập nhật
        return courseRepository.save(course);
    }// trả về và lưu

    public void deleteCourse(int id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        courseRepository.delete(course);
        // tìm id ko tìm thì ném ngoaị lệ
        // xóa course trg csdl
    }

    // Các phương thức sử dụng Optional
    public Optional<Course> getCourseByName(String name) {
        return courseRepository.findByName(name);
        // trả về tên course
    }

    public Optional<List<Course>> getCoursesByPrice(double price) {
        return courseRepository.findByPrice(price);
        //giá course
    }

    public Optional<List<Course>> getCoursesByStatus(int status) {
        return courseRepository.findByStatus(status);
        //trạng thái
    }

    public Optional<List<Course>> getCoursesByNameAndStatus(String name, int status) {
        return courseRepository.findByNameAndStatus(name, status);
        //trạng thái và tên
    }
}
