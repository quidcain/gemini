package com.gemini.admin.database.dao.beans;

import com.gemini.commons.beans.forms.AddressBean;
import com.gemini.commons.utils.Utils;
import com.gemini.commons.utils.ValidationUtils;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/19/18
 * Time: 9:20 AM
 */
public class EnrolledStudent {

    private Long extStudentNumber;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private Integer age;
    private String region;
    private String city;
    private String school;
    private String gradeLevel;
    private String program;
    private String originSchoolNumber;
    private String originSchoolName;
    private AddressBean addressBean = new AddressBean();

    public Long getExtStudentNumber() {
        return extStudentNumber;
    }

    public void setExtStudentNumber(Long extStudentNumber) {
        this.extStudentNumber = extStudentNumber;
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

    public String getName() {
        return Utils.toFullName(firstName, middleName, lastName);
    }


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getOriginSchoolNumber() {
        return originSchoolNumber;
    }

    public void setOriginSchoolNumber(String originSchoolNumber) {
        this.originSchoolNumber = originSchoolNumber;
    }

    public String getOriginSchoolName() {
        return originSchoolName;
    }

    public void setOriginSchoolName(String originSchoolName) {
        this.originSchoolName = originSchoolName;
    }

    public String getLine1() {
        return addressBean.getLine1();
    }

    public void setLine1(String line1) {
        addressBean.setLine1(line1);
    }

    public String getLine2() {
        return addressBean.getLine2();
    }

    public void setLine2(String line2) {
        addressBean.setLine2(line2);
    }

    public String getAddressCity() {
        return addressBean.getCity();
    }

    public void setAddressCity(String city) {
        addressBean.setCity(city);

    }

    public String getZipcode() {
        return addressBean.getZipcode();

    }

    public void setZipcode(String zipcode) {
        addressBean.setZipcode(zipcode);
    }

    public String getAddressFormatted() {
        return addressBean.getAddressFormatted();
    }


}