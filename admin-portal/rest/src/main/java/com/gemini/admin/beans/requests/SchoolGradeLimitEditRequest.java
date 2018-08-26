package com.gemini.admin.beans.requests;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/28/18
 * Time: 8:17 PM
 */
public class SchoolGradeLimitEditRequest {
    private Long schoolId;
    private String gradeLevel;
    private Integer newMaxCapacity;
    private String loggedAuthority;

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

    public Integer getNewMaxCapacity() {
        return newMaxCapacity;
    }

    public void setNewMaxCapacity(Integer newMaxCapacity) {
        this.newMaxCapacity = newMaxCapacity;
    }

    public String getLoggedAuthority() {
        return loggedAuthority;
    }

    public void setLoggedAuthority(String loggedAuthority) {
        this.loggedAuthority = loggedAuthority;
    }
}