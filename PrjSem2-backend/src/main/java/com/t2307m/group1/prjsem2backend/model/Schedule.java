package com.t2307m.group1.prjsem2backend.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "Schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "classId", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private AClass aClass;

    private Integer lessonNumber;
    private Date classDate;

    public Schedule() {
    }

    public Schedule(AClass aClass, Integer lessonNumber, Date classDate) {
        this.aClass = aClass;
        this.lessonNumber = lessonNumber;
        this.classDate = classDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AClass getaClass() {
        return aClass;
    }

    public void setaClass(AClass aClass) {
        this.aClass = aClass;
    }

    public Integer getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(Integer lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    public Date getClassDate() {
        return classDate;
    }

    public void setClassDate(Date classDate) {
        this.classDate = classDate;
    }
}
