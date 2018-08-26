package com.gemini.codes.beans;

import com.gemini.commons.utils.Utils;
import com.google.common.base.Joiner;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/22/18
 * Time: 9:35 AM
 */
public class StudentResult {
    private Long id;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String schoolName;
    private String extSchoolNumber;
//    school address
    private String line1;
    private String line2;
    private String city;
    private String state = "PR";
    private String zipcode;
    private String gradeLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getExtSchoolNumber() {
        return extSchoolNumber;
    }

    public void setExtSchoolNumber(String extSchoolNumber) {
        this.extSchoolNumber = extSchoolNumber;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getStudentName(){
        return Utils.toFullName(firstName, middleName, lastName);
    }

    public String getSchoolAddress(){
        return Joiner.on(" ")
                .skipNulls()
                .join(line1, line2, city, state.concat(","), zipcode);
    }
}