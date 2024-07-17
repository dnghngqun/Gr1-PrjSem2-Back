package com.t2307m.group1.prjsem2backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;


import java.time.LocalDate;

@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "enrollmentId")
    private Enrollment enrollment;

    @Column(name = "lessonNumber")
    private int lessonNumber;

    @Column(name = "attendanceStatus")
    private String attendanceStatus;

    @Column(name = "attendanceDate")
    private LocalDate attendanceDate;

    @PrePersist
    protected void onCreate() {
        this.attendanceDate = LocalDate.now();
    }
    public Attendance(Enrollment enrollment, int lessonNumber, String attendanceStatus) {
        this.enrollment = enrollment;
        this.lessonNumber = lessonNumber;
        this.attendanceStatus = attendanceStatus;
        this.attendanceDate = LocalDate.now();
    }

    public Attendance() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public int getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(int lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }
}
