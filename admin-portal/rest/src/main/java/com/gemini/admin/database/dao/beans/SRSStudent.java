package com.gemini.admin.database.dao.beans;

import com.gemini.admin.beans.types.ReasonToDenyEnrollment;
import com.gemini.commons.beans.types.RequestStatus;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/31/18
 * Time: 10:11 PM
 */
public class SRSStudent {

    private Long preEnrollmentId;
    private Long sisStudentId;
    private String gradeLevel;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date dateOfBirth;
    private Integer occupationalEnrollment;
    private RequestStatus requestStatus;
    private ReasonToDenyEnrollment reasonToDenyEnrollment;
    private Long coalesceSchoolId_1;
    private Long coalesceSchoolId_2;
    private String impedimento;
    private Integer isMontessori;

    private String originSchoolNumber;
    private String originSchoolName;
    //0 origen, 1- primera opcion, 2- segunda opcion
    private Integer option0;
    private Integer option1;
    private Integer option2;
    private Long school;
    private Long school1;
    private Long school2;


    public Long getPreEnrollmentId() {
        return preEnrollmentId;
    }

    public void setPreEnrollmentId(Long preEnrollmentId) {
        this.preEnrollmentId = preEnrollmentId;
    }

    public Long getSisStudentId() {
        return sisStudentId;
    }

    public void setSisStudentId(Long sisStudentId) {
        this.sisStudentId = sisStudentId;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getOccupationalEnrollment() {
        return occupationalEnrollment;
    }

    public void setOccupationalEnrollment(Integer occupationalEnrollment) {
        this.occupationalEnrollment = occupationalEnrollment;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public ReasonToDenyEnrollment getReasonToDenyEnrollment() {
        return reasonToDenyEnrollment;
    }

    public void setReasonToDenyEnrollment(ReasonToDenyEnrollment reasonToDenyEnrollment) {
        this.reasonToDenyEnrollment = reasonToDenyEnrollment;
    }

    public Long getCoalesceSchoolId_1() {
        return coalesceSchoolId_1;
    }

    public void setCoalesceSchoolId_1(Long coalesceSchoolId_1) {
        this.coalesceSchoolId_1 = coalesceSchoolId_1;
    }

    public Long getCoalesceSchoolId_2() {
        return coalesceSchoolId_2;
    }

    public void setCoalesceSchoolId_2(Long coalesceSchoolId_2) {
        this.coalesceSchoolId_2 = coalesceSchoolId_2;
    }

    public String getImpedimento() {
        return impedimento;
    }

    public void setImpedimento(String impedimento) {
        this.impedimento = impedimento;
    }

    public Integer getIsMontessori() {
        return isMontessori;
    }

    public void setIsMontessori(Integer isMontessori) {
        this.isMontessori = isMontessori;
    }

    public boolean isMontessori() {
        return isMontessori != null && isMontessori > 0;
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

    public Integer getOption0() {
        return option0;
    }

    public void setOption0(Integer option0) {
        this.option0 = option0;
    }

    public Integer getOption1() {
        return option1;
    }

    public void setOption1(Integer option1) {
        this.option1 = option1;
    }

    public Integer getOption2() {
        return option2;
    }

    public void setOption2(Integer option2) {
        this.option2 = option2;
    }

    public Long getSchool() {
        return school;
    }

    public void setSchool(Long school) {
        this.school = school;
    }

    public Long getSchool1() {
        return school1;
    }

    public void setSchool1(Long school1) {
        this.school1 = school1;
    }

    public Long getSchool2() {
        return school2;
    }

    public void setSchool2(Long school2) {
        this.school2 = school2;
    }
}