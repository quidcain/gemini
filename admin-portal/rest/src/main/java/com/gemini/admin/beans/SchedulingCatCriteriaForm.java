package com.gemini.admin.beans;

import com.gemini.admin.beans.types.SchedulingAnalysisType;
import com.gemini.commons.utils.ValidationUtils;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 6/13/18
 * Time: 5:38 PM
 */
public class SchedulingCatCriteriaForm {
    private SchedulingAnalysisType analysisType;
    private int selectedType; //0-asignado, 1-vacantes, 2-excedentes
    private Long regionId;
    private String cityCode;
    private String city;
    private Long schoolId;
    private boolean orderByCount;

    public SchedulingAnalysisType getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(SchedulingAnalysisType analysisType) {
        this.analysisType = analysisType;
    }

    public int getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(int selectedType) {
        this.selectedType = selectedType;
    }

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

    public boolean isRegionOnly() {
        return ValidationUtils.valid(regionId) && ((!ValidationUtils.valid(cityCode) || "-1".equals(cityCode)) && !ValidationUtils.valid(city))  && !ValidationUtils.valid(schoolId);
    }

    public boolean isCityOnly() {
        return ValidationUtils.valid(regionId) && ((ValidationUtils.valid(cityCode) && !"-1".equals(cityCode)) || ValidationUtils.valid(city)) && !ValidationUtils.valid(schoolId);
    }

    public boolean isSchoolOnly() {
        return ValidationUtils.valid(schoolId);
    }

    public boolean isOrderByCount() {
        return orderByCount;
    }

    public void setOrderByCount(boolean orderByCount) {
        this.orderByCount = orderByCount;
    }
}