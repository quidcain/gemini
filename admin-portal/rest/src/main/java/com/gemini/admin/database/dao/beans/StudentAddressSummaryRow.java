package com.gemini.admin.database.dao.beans;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 8/7/18
 * Time: 4:43 PM
 */
public class StudentAddressSummaryRow {
    //<Workbook.Column label="Región" value="regionName"/>
    //<Workbook.Column label="Municipio" value="city"/>
    //<Workbook.Column label="Numero De Escuela" value="extSchoolNumber"/>
    //<Workbook.Column label="Escuela" value="schoolName"/>
    //<Workbook.Column label="Estudiantes con Direccion Validada" value="validatedAddress"/>
    //<Workbook.Column label="Estudiantes sin Direccion Validada" value="notValidatedAddress"/>

    private String regionName;
    private String city;
    private String extSchoolNumber;
    private String schoolName;
    private Long validatedAddress;
    private Long notValidatedAddress;

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

    public String getExtSchoolNumber() {
        return extSchoolNumber;
    }

    public void setExtSchoolNumber(String extSchoolNumber) {
        this.extSchoolNumber = extSchoolNumber;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Long getValidatedAddress() {
        return validatedAddress;
    }

    public void setValidatedAddress(Long validatedAddress) {
        this.validatedAddress = validatedAddress;
    }

    public Long getNotValidatedAddress() {
        return notValidatedAddress;
    }

    public void setNotValidatedAddress(Long notValidatedAddress) {
        this.notValidatedAddress = notValidatedAddress;
    }
}