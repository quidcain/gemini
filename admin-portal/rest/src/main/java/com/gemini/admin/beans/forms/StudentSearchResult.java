package com.gemini.admin.beans.forms;

import com.gemini.commons.utils.ValidationUtils;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/31/18
 * Time: 10:07 PM
 */
public class StudentSearchResult {

    private Long preEnrollmentId;
    private Long studentId;
    private String studentName;
    private Integer age;
    private String originSchoolNumber;
    private String status;
    private String haveDisability;
    private String requestedPrograms;
    private String colorCss;
    private Boolean isMontessori;

    public Long getPreEnrollmentId() {
        return preEnrollmentId;
    }

    public void setPreEnrollmentId(Long preEnrollmentId) {
        this.preEnrollmentId = preEnrollmentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getOriginSchoolNumber() {
        return originSchoolNumber;
    }

    public void setOriginSchoolNumber(String originSchoolNumber) {
        this.originSchoolNumber = originSchoolNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHaveDisability() {
        return haveDisability;
    }

    public void setHaveDisability(String haveDisability) {
        this.haveDisability = haveDisability;
    }

    public String getRequestedPrograms() {
        return requestedPrograms;
    }

    public void setRequestedPrograms(String requestedPrograms) {
        this.requestedPrograms = requestedPrograms;
    }

    public String getColorCss() {
        return colorCss;
    }

    public void setColorCss(String colorCss) {
        this.colorCss = colorCss;
    }

    public boolean isCanEdit() {
        return ValidationUtils.valid(preEnrollmentId);
    }

    public Boolean getMontessori() {
        return isMontessori;
    }

    public void setMontessori(Boolean montessori) {
        isMontessori = montessori;
    }
}