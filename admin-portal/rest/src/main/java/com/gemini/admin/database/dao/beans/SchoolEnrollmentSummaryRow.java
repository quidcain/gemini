package com.gemini.admin.database.dao.beans;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 8/15/18
 * Time: 10:50 AM
 */
public class SchoolEnrollmentSummaryRow {

    private Long regionId;
    private String regionName;

    private Long cityCode;
    private String city;

    private Long schoolId;
    private Long extSchoolNumber;
    private String schoolName;

    private Long endingEnrollment;
    private Long startingEnrollment;

    private Long additions;
    private Long subtractions;
    private Long netChange;

    private Long enrollmentWithoutSession;

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

    public Long getEndingEnrollment() {
        return endingEnrollment;
    }

    public void setEndingEnrollment(Long endingEnrollment) {
        this.endingEnrollment = endingEnrollment;
    }

    public Long getStartingEnrollment() {
        return startingEnrollment;
    }

    public void setStartingEnrollment(Long startingEnrollment) {
        this.startingEnrollment = startingEnrollment;
    }

    public Long getAdditions() {
        return additions;
    }

    public void setAdditions(Long additions) {
        this.additions = additions;
    }

    public Long getSubtractions() {
        return subtractions;
    }

    public void setSubtractions(Long subtractions) {
        this.subtractions = subtractions;
    }

    public Long getNetChange() {
        return netChange;
    }

    public void setNetChange(Long netChange) {
        this.netChange = netChange;
    }

    public Long getEnrollmentWithoutSession() {
        return enrollmentWithoutSession;
    }

    public void setEnrollmentWithoutSession(Long enrollmentWithoutSession) {
        this.enrollmentWithoutSession = enrollmentWithoutSession;
    }
}