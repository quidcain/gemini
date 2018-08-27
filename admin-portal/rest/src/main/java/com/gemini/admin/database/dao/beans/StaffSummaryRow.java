package com.gemini.admin.database.dao.beans;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 8/3/18
 * Time: 3:48 PM
 */
public class StaffSummaryRow {
    private Long regionId;
    private String regionName;
    private String cityCd;
    private String city;
    private Long schoolId;
    private String schoolName;
    private Long needs;
    private Long assigns;
    private Long vacants;
    private Long assignTransit;

    private Long enrollmentTotal;

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

    public String getCityCd() {
        return cityCd;
    }

    public void setCityCd(String cityCd) {
        this.cityCd = cityCd;
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

    public Long getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public void setEnrollmentTotal(Long enrollmentTotal) {
        this.enrollmentTotal = enrollmentTotal;
    }

    public Long getAssignTransit() {
        return assignTransit;
    }

    public void setAssignTransit(Long assignTransit) {
        this.assignTransit = assignTransit;
    }
}