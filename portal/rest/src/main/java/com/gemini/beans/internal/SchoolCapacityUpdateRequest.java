package com.gemini.beans.internal;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/12/18
 * Time: 1:26 PM
 */
public class SchoolCapacityUpdateRequest {

    private Long schoolId;
    private String gradeLevel;
    private Integer value; //if it is possible add, else subtract

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}