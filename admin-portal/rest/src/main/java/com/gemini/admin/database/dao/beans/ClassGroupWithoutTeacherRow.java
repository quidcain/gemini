package com.gemini.admin.database.dao.beans;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 8/7/18
 * Time: 4:43 PM
 */
public class ClassGroupWithoutTeacherRow {
//    <Workbook.Column label="Región" value="regionName"/>
//    <Workbook.Column label="Municipio" value="city"/>
//    <Workbook.Column label="Numero De Escuela" value="extSchoolNumber"/>
//    <Workbook.Column label="Escuela" value="schoolName"/>
//    <Workbook.Column label="Curso" value="category"/>
//    <Workbook.Column label="Student Group" value="studentGroup"/>
//    <Workbook.Column label="Student Per Group" value="studentPerGroup"/>

    private String regionName;
    private String city;
    private String extSchoolNumber;
    private String schoolName;
    private String category;
    private String studentGroup;
    private Long studentPerGroup;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
}