package com.gemini.admin.beans;

import com.gemini.admin.beans.types.SchedulingAnalysisType;
import com.gemini.commons.utils.ValidationUtils;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 6/11/18
 * Time: 1:52 PM
 */
public class SchedulingCriteriaForm implements Criteria {

    private SchedulingAnalysisType analysisType;
    private Long regionId;
    private String cityCode;
    private Long schoolId;

    public SchedulingAnalysisType getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(SchedulingAnalysisType analysisType) {
        this.analysisType = analysisType;
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

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public boolean isRegionOnly(){
        return ValidationUtils.valid(regionId) && !ValidationUtils.valid(cityCode) && !ValidationUtils.valid(schoolId);
    }

    public boolean isCityOnly(){
        return ValidationUtils.valid(regionId) && ValidationUtils.valid(cityCode) && !ValidationUtils.valid(schoolId);
    }

    public boolean isSchoolOnly(){
        return ValidationUtils.valid(regionId) && ValidationUtils.valid(cityCode) && ValidationUtils.valid(schoolId);
    }
}