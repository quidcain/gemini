package com.gemini.admin.database.dao.beans;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 8/3/18
 * Time: 4:38 PM
 */
public class EnrollmentStaffingRow {

    private Long regionId;
    private String regionName;

    private Long cityCode;
    private String city;

    private Long schoolId;
    private String schoolName;
    private String gradeLevel;
    private String category;
    private Long enrollmentTotal;
    private Long needs;
    private Long assigns;
    private Long assignTransit;
    private Long vacants;

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Long getCityCode() {
        return cityCode;
    }

    public void setCityCode(Long cityCode) {
        this.cityCode = cityCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
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