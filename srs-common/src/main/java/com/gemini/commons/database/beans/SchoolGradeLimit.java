package com.gemini.commons.database.beans;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/28/18
 * Time: 7:54 PM
 */
public class SchoolGradeLimit {

/*
    REGION                 VARCHAR2(20)
    CITY                   VARCHAR2(20)
    SCHOOL_NAME            VARCHAR2(100)
    GRADE_LEVEL            VARCHAR2(10)
    MAX_CAPACITY           NUMBER(10)
    EXT_SCHOOL_NUMBER      NUMBER(10)
    SCHOOL_ID              NUMBER
*/

    private Long schoolGradeLimitId;
    private Long schoolId;
    private Long extSchoolNumber;
    private String schoolName;
    private String gradeLevel;
    private Integer planificacionMaxCapacity;
    private Integer maxCapacity;
    private Integer confirmedMaxCapacity;
    private Integer remainCap;
    private Integer enrollmentTotal;


    public Long getSchoolGradeLimitId() {
        return schoolGradeLimitId;
    }

    public void setSchoolGradeLimitId(Long schoolGradeLimitId) {
        this.schoolGradeLimitId = schoolGradeLimitId;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public Long getExtSchoolNumber() {
        return extSchoolNumber;
    }

    public void setExtSchoolNumber(Long extSchoolNumber) {
        this.extSchoolNumber = extSchoolNumber;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public Integer getPlanificacionMaxCapacity() {
        return planificacionMaxCapacity;
    }

    public void setPlanificacionMaxCapacity(Integer planificacionMaxCapacity) {
        this.planificacionMaxCapacity = planificacionMaxCapacity;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Integer getConfirmedMaxCapacity() {
        return confirmedMaxCapacity;
    }

    public void setConfirmedMaxCapacity(Integer confirmedMaxCapacity) {
        this.confirmedMaxCapacity = confirmedMaxCapacity;
    }

    public Integer getRemainCap() {
        return remainCap;
    }

    public void setRemainCap(Integer remainCap) {
        this.remainCap = remainCap;
    }

    public Integer getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public void setEnrollmentTotal(Integer enrollmentTotal) {
        this.enrollmentTotal = enrollmentTotal;
    }
}