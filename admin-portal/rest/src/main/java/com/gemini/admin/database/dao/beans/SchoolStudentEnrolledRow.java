package com.gemini.admin.database.dao.beans;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 8/15/18
 * Time: 10:58 AM
 */
public class SchoolStudentEnrolledRow {
    private Long regionId;
    private String regionName;

    private Long cityCode;
    private String city;

    private Long schoolId;
    private Long extSchoolNumber;
    private String schoolName;
    private Long studentId;
    private String fullName;

    private String transferFromSchoolNumber;
    private String transferFromSchool;
    private String transferToSchoolNumber;
    private String transferToSchool;

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

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTransferToSchoolNumber() {
        return transferToSchoolNumber;
    }

    public void setTransferToSchoolNumber(String transferToSchoolNumber) {
        this.transferToSchoolNumber = transferToSchoolNumber;
    }

    public String getTransferFromSchoolNumber() {
        return transferFromSchoolNumber;
    }

    public void setTransferFromSchoolNumber(String transferFromSchoolNumber) {
        this.transferFromSchoolNumber = transferFromSchoolNumber;
    }

    public String getTransferFromSchool() {
        return transferFromSchool;
    }

    public void setTransferFromSchool(String transferFromSchool) {
        this.transferFromSchool = transferFromSchool;
    }

    public String getTransferToSchool() {
        return transferToSchool;
    }

    public void setTransferToSchool(String transferToSchool) {
        this.transferToSchool = transferToSchool;
    }
}