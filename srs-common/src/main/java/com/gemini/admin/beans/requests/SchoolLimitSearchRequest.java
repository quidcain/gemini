package com.gemini.admin.beans.requests;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 8/1/18
 * Time: 9:57 PM
 */
public class SchoolLimitSearchRequest {
    private Long regionId;
    private String cityCode;
    private Long schoolId;
    private String gradeLevel;

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
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