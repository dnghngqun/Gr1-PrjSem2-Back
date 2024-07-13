package com.t2307m.group1.prjsem2backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name="Section")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    private String goalTitle;
    private String contentGoal;
    private String introduce;
    private String contentIntroduce;
    private String details;
    private String contentDetails;
    private String countLessons;
    private String durationLesson;
    private String supportTime;
    private String classSize;
    private String contentClassSize;

    public Section() {
    }

    public Section(Course course, String goalTitle, String contentGoal, String introduce, String contentIntroduce, String details, String contentDetails, String countLessons, String durationLesson, String supportTime, String classSize, String contentClassSize) {
        this.course = course;
        this.goalTitle = goalTitle;
        this.contentGoal = contentGoal;
        this.introduce = introduce;
        this.contentIntroduce = contentIntroduce;
        this.details = details;
        this.contentDetails = contentDetails;
        this.countLessons = countLessons;
        this.durationLesson = durationLesson;
        this.supportTime = supportTime;
        this.classSize = classSize;
        this.contentClassSize = contentClassSize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getGoalTitle() {
        return goalTitle;
    }

    public void setGoalTitle(String goalTitle) {
        this.goalTitle = goalTitle;
    }

    public String getContentGoal() {
        return contentGoal;
    }

    public void setContentGoal(String contentGoal) {
        this.contentGoal = contentGoal;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getContentIntroduce() {
        return contentIntroduce;
    }

    public void setContentIntroduce(String contentIntroduce) {
        this.contentIntroduce = contentIntroduce;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getContentDetails() {
        return contentDetails;
    }

    public void setContentDetails(String contentDetails) {
        this.contentDetails = contentDetails;
    }

    public String getCountLessons() {
        return countLessons;
    }

    public void setCountLessons(String countLessons) {
        this.countLessons = countLessons;
    }

    public String getDurationLesson() {
        return durationLesson;
    }

    public void setDurationLesson(String durationLesson) {
        this.durationLesson = durationLesson;
    }

    public String getSupportTime() {
        return supportTime;
    }

    public void setSupportTime(String supportTime) {
        this.supportTime = supportTime;
    }

    public String getClassSize() {
        return classSize;
    }

    public void setClassSize(String classSize) {
        this.classSize = classSize;
    }

    public String getContentClassSize() {
        return contentClassSize;
    }

    public void setContentClassSize(String contentClassSize) {
        this.contentClassSize = contentClassSize;
    }
}
