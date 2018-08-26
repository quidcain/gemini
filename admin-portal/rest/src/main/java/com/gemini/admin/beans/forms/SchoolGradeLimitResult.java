package com.gemini.admin.beans.forms;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/28/18
 * Time: 8:10 PM
 */
public class SchoolGradeLimitResult {
    private Long schoolGradeLimitId;
    private Long schoolId;
    private String gradeLevel;
    private String gradeLevelDescription;
    private Integer confirmedMaxCapacity;
    private Integer maxCapacity;

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

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getGradeLevelDescription() {
        return gradeLevelDescription;
    }

    public void setGradeLevelDescription(String gradeLevelDescription) {
        this.gradeLevelDescription = gradeLevelDescription;
    }

    public Integer getConfirmedMaxCapacity() {
        return confirmedMaxCapacity;
    }

    public void setConfirmedMaxCapacity(Integer confirmedMaxCapacity) {
        this.confirmedMaxCapacity = confirmedMaxCapacity;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}