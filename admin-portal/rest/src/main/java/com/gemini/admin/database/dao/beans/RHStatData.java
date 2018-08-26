package com.gemini.admin.database.dao.beans;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 6/11/18
 * Time: 4:19 PM
 */
public class RHStatData {
    private Long regionId;
    private String region;
    private String cityCode;
    private String city;
    private Long schoolId;
    private String schoolName;
    private Integer asignados;
    private Integer vacantes;
    private Integer excedentes;

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Integer getAsignados() {
        return asignados;
    }

    public void setAsignados(Integer asignados) {
        this.asignados = asignados;
    }

    public Integer getVacantes() {
        return vacantes;
    }

    public void setVacantes(Integer vacantes) {
        this.vacantes = vacantes;
    }

    public Integer getExcedentes() {
        return excedentes;
    }

    public void setExcedentes(Integer excedentes) {
        this.excedentes = excedentes;
    }
}