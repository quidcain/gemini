package com.gemini.admin.beans;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 6/11/18
 * Time: 6:22 PM
 */
public interface Criteria {

    Long getRegionId();

    void setRegionId(Long regionId);

    String getCityCode();

    void setCityCode(String cityCode);

    Long getSchoolId();

    void setSchoolId(Long schoolId);
}