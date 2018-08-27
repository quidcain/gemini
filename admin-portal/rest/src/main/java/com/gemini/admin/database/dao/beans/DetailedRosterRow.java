package com.gemini.admin.database.dao.beans;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 8/4/18
 * Time: 3:10 AM
 */
public class DetailedRosterRow {
    private String regionName;
    private String city;
    private Long extSchoolNumber;
    private String schoolName;
    private String studentGroup;
    private Long studentPerGroup;
    private String puesto;
    private String numEmpleado;
    private String fullName;
    private String status;

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

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Long getStudentPerGroup() {
        return studentPerGroup;
    }

    public void setStudentPerGroup(Long studentPerGroup) {
        this.studentPerGroup = studentPerGroup;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getNumEmpleado() {
        return numEmpleado;
    }

    public void setNumEmpleado(String numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}