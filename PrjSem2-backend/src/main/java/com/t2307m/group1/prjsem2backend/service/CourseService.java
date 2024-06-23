
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
//     yeu cau spring tiem 1 instance cu the cua CourseRepository vao CourseService
     private CourseRepository courseRepository;
     public List<Course> getAllCourses() {
         return courseRepository.findAll();
         //method tra ve ds khoa hoc,trong return findAll() lay tat ca cac khoa hoc tu csdl
     }
     public Optional<Course> getCourseById(int id) {
         return courseRepository.findById(id);
         //Optional xu ly du lieu 1  cac an toan (no co the co hoac ko)
     }
     public Course addCourse(Course course) {

         return courseRepository.save(course);
         //nhan 1 obj course va luu vao csdl
     }
     public Course updateCourse(int id,Course courseDetails){
       // courseDetails  là một đối tượng của class Course chứa các thông tin mới sẽ được cập nhật vào khóa học hiện có.
         Course course=courseRepository.findById(id).orElseThrow(()->new RuntimeException("Course not found"));
         course.setName(courseDetails.getName());
         course.setPrice(courseDetails.getPrice());
         course.setStatus(courseDetails.getStatus());
         //courseDetails là đối tượng chứa dữ liệu mới được gửi từ client hoặc người dùng để cập nhật.
         // nên dt course sẽ dc update theo cac trg của courseDetails ( nó là dữ liêu phải yc chỉnh sửa từ phía client)
         return courseRepository.save(course);
     }

}