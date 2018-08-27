package com.gemini.admin.beans.report;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 8/3/18
 * Time: 3:46 PM
 */
public class StaffSummary {
    private String regionName;
    private String city;
    private String schoolName;
    private Long needs;
    private Long assigns;
    private Long vacants;
    private Long assignTransit;
    private Long enrollmentTotal;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
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

    public Long getVacants() {
        return vacants;
    }

    public void setVacants(Long vacants) {
        this.vacants = vacants;
    }

    public Long getAssignTransit() {
        return assignTransit;
    }

    public void setAssignTransit(Long assignTransit) {
        this.assignTransit = assignTransit;
    }

    public Long getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public void setEnrollmentTotal(Long enrollmentTotal) {
        this.enrollmentTotal = enrollmentTotal;
    }
}