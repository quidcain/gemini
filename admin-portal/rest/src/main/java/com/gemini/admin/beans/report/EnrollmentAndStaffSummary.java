package com.gemini.admin.beans.report;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 8/3/18
 * Time: 4:35 PM
 */
public class EnrollmentAndStaffSummary {

    private String schoolName;
    private String gradeLevel;
    private String category;
    private Long enrollmentTotal;
    private Long needs;
    private Long assigns;
    private Long assignTransit;
    private Long vacants;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public void setEnrollmentTotal(Long enrollmentTotal) {
        this.enrollmentTotal = enrollmentTotal;
    }

    public Long getNeeds() {
        return needs;
    }

    public void setNeeds(Long needs) {
        this.needs = needs;
    }

    public Long getAssigns() {
        return assigns;
    }

    public void setAssigns(Long assigns) {
        this.assigns = assigns;
    }

    public Long getAssignTransit() {
        return assignTransit;
    }

    public void setAssignTransit(Long assignTransit) {
        this.assignTransit = assignTransit;
    }

    public Long getVacants() {
        return vacants;
    }

    public void setVacants(Long vacants) {
        this.vacants = vacants;
    }
}