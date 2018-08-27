package com.gemini.commons.database.beans;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 8/9/18
 * Time: 6:13 PM
 */
public class StudentScheduleView {

    /*
        STUDENT_ID       NUMBER(16)
        PERIOD           NUMBER(2)
        PERIODO          VARCHAR2(15)
        MONDAY           VARCHAR2(36)
        TUESDAY          VARCHAR2(36)
        WEDNESDAY        VARCHAR2(36)
        THURSDAY         VARCHAR2(36)
        FRIDAY           VARCHAR2(36)
    */

    private Long studentId;
    private Long period;
    private String periodText;
    private String teacher;
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getPeriod() {
        return period;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }

    public String getPeriodText() {
        return periodText;
    }

    public void setPeriodText(String periodText) {
        this.periodText = periodText;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }
}