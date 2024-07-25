package com.t2307m.group1.prjsem2backend.model;

//class DTO dùng để nhận tthông tin từ client
public class AttendanceDTO {
    private Integer enrollmentId;
    private Integer scheduleId;
    private String status;

    public AttendanceDTO() {
    }

    public AttendanceDTO(Integer scheduleId, Integer enrollmentId, String status) {
        this.scheduleId = scheduleId;
        this.enrollmentId = enrollmentId;
        this.status = status;
    }

    public Integer getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(Integer enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
