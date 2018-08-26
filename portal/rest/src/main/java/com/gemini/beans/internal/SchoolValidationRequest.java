package com.gemini.beans.internal;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/30/18
 * Time: 9:49 AM
 */
public class SchoolValidationRequest {

    private Long schoolId;
    private String gradeLevel;

    public SchoolValidationRequest() {
    }

    public SchoolValidationRequest(Long schoolId, String gradeLevel) {
        this.schoolId = schoolId;
        this.gradeLevel = gradeLevel;
    }

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
}