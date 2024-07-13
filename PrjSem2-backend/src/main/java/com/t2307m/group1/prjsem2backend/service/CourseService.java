
package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.Course;
import com.t2307m.group1.prjsem2backend.model.Lesson;
import com.t2307m.group1.prjsem2backend.model.Section;
import com.t2307m.group1.prjsem2backend.repositories.CourseRepository;
import com.t2307m.group1.prjsem2backend.repositories.LessonRepository;
import com.t2307m.group1.prjsem2backend.repositories.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

 @Service
public class CourseService {
//     yeu cau spring tiem 1 instance cu the cua CourseRepository vao CourseService
     private final CourseRepository courseRepository;
     private final LessonRepository lessonRepository;
     private final SectionRepository sectionRepository;

     @Autowired
     public CourseService(CourseRepository courseRepository, LessonRepository lessonRepository, SectionRepository sectionRepository) {
         this.courseRepository = courseRepository;
         this.lessonRepository = lessonRepository;
         this.sectionRepository = sectionRepository;
     }

     public List<Course> getAllCourses() {
         return courseRepository.findAll();
         //method tra ve ds khoa hoc,trong return findAll() lay tat ca cac khoa hoc tu csdl
     }
     public Optional<Course> getCourseById(int id) {
         return courseRepository.findById(id);
         //Optional xu ly du lieu 1  cac an toan (no co the co hoac ko)
     }
     public Course addCourse(Course course) {
         course.setStatus(0);
         return courseRepository.save(course);
         //nhan 1 obj course va luu vao csdl
     }
     public Course updateCourse(int id,Course courseDetails){
       // courseDetails  là một đối tượng của class Course chứa các thông tin mới sẽ được cập nhật vào khóa học hiện có.
         Course course=courseRepository.findById(id).orElseThrow(()->new RuntimeException("Course not found"));
         course.setName(courseDetails.getName());
         course.setPrice(courseDetails.getPrice());
         course.setStatus(courseDetails.getStatus());
         course.setClassify(courseDetails.getClassify());
         course.setImgLink(course.getImgLink());
         //courseDetails là đối tượng chứa dữ liệu mới được gửi từ client hoặc người dùng để cập nhật.
         // nên dt course sẽ dc update theo cac trg của courseDetails ( nó là dữ liêu phải yc chỉnh sửa từ phía client)
         return courseRepository.save(course);
     }
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
     // Lesson Services
     public Lesson saveLesson(Lesson lesson) {
         return lessonRepository.save(lesson);
     }

     public Optional<Lesson> getLessonById(int id) {
         return lessonRepository.findById(id);
     }

     public List<Lesson> getLessonsByCourseId(int courseId) {
         return lessonRepository.findAll().stream()
                 .filter(lesson -> lesson.getCourse().getId() == courseId)
                 .toList();
     }

     public void deleteLessonById(int id) {
         lessonRepository.deleteById(id);
     }

     // Section Services
     public Section saveSection(Section section) {
         return sectionRepository.save(section);
     }

     public Optional<Section> getSectionById(int id) {
         return sectionRepository.findById(id);
     }

     public List<Section> getSectionsByCourseId(int courseId) {
         return sectionRepository.findAll().stream()
                 .filter(section -> section.getCourse().getId() == courseId)
                 .toList();
     }

     public void deleteSectionById(int id) {
         sectionRepository.deleteById(id);
     }
 }

