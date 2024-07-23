package com.t2307m.group1.prjsem2backend.model;

import jakarta.persistence.*;



@Entity
@Table(name = "Attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "enrollmentId")
    private Enrollment enrollment;


    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "scheduleId")
    private Schedule schedule;


    @Column(name = "attendanceStatus")
    private String attendanceStatus;

    public Attendance() {
    }

    public Attendance(Enrollment enrollment, Schedule schedule, String attendanceStatus) {
        this.enrollment = enrollment;
        this.schedule = schedule;
        this.attendanceStatus = attendanceStatus;
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

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }
}
