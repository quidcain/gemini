package com.gemini.commons.database.beans;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/21/18
 * Time: 4:38 PM
 */
public class EnrollmentInfo {

    private Long schoolId;
    private Long schoolYear;
    private String gradeLevel;
    private Integer supplementEduSvcInd;
    private Long studentId;
    private Long enrollmentId;
    private Long counserlorStaffId;
    private String counserlorFirstName;
    private String counserlorLastName;
    private String entryCd;
    private String entryCdDesc;
    private Integer adaEligibleInd;
    private Date beginEnrollmentDate;
    private Date endEnrollmentDate;
    private String endStatusCd;
    private Long enrollCreatedBy;
    private Date enrollCreateTs;
    private Long enrollUpdatedBy;
    private Date enrollUpdateTs;
    private String endStatusDesc;
    private String specialProgCategoryCd;
    private String programDesc;

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public Long getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(Long schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public Integer getSupplementEduSvcInd() {
        return supplementEduSvcInd;
    }

    public void setSupplementEduSvcInd(Integer supplementEduSvcInd) {
        this.supplementEduSvcInd = supplementEduSvcInd;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(Long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Long getCounserlorStaffId() {
        return counserlorStaffId;
    }

    public void setCounserlorStaffId(Long counserlorStaffId) {
        this.counserlorStaffId = counserlorStaffId;
    }

    public String getCounserlorFirstName() {
        return counserlorFirstName;
    }

    public void setCounserlorFirstName(String counserlorFirstName) {
        this.counserlorFirstName = counserlorFirstName;
    }

    public String getCounserlorLastName() {
        return counserlorLastName;
    }

    public void setCounserlorLastName(String counserlorLastName) {
        this.counserlorLastName = counserlorLastName;
    }

    public String getEntryCd() {
        return entryCd;
    }

    public void setEntryCd(String entryCd) {
        this.entryCd = entryCd;
    }

    public String getEntryCdDesc() {
        return entryCdDesc;
    }

    public void setEntryCdDesc(String entryCdDesc) {
        this.entryCdDesc = entryCdDesc;
    }

    public Integer getAdaEligibleInd() {
        return adaEligibleInd;
    }

    public void setAdaEligibleInd(Integer adaEligibleInd) {
        this.adaEligibleInd = adaEligibleInd;
    }

    public Date getBeginEnrollmentDate() {
        return beginEnrollmentDate;
    }

    public void setBeginEnrollmentDate(Date beginEnrollmentDate) {
        this.beginEnrollmentDate = beginEnrollmentDate;
    }

    public Date getEndEnrollmentDate() {
        return endEnrollmentDate;
    }

    public void setEndEnrollmentDate(Date endEnrollmentDate) {
        this.endEnrollmentDate = endEnrollmentDate;
    }

    public String getEndStatusCd() {
        return endStatusCd;
    }

    public void setEndStatusCd(String endStatusCd) {
        this.endStatusCd = endStatusCd;
    }

    public Long getEnrollCreatedBy() {
        return enrollCreatedBy;
    }

    public void setEnrollCreatedBy(Long enrollCreatedBy) {
        this.enrollCreatedBy = enrollCreatedBy;
    }

    public Date getEnrollCreateTs() {
        return enrollCreateTs;
    }

    public void setEnrollCreateTs(Date enrollCreateTs) {
        this.enrollCreateTs = enrollCreateTs;
    }

    public Long getEnrollUpdatedBy() {
        return enrollUpdatedBy;
    }

    public void setEnrollUpdatedBy(Long enrollUpdatedBy) {
        this.enrollUpdatedBy = enrollUpdatedBy;
    }

    public Date getEnrollUpdateTs() {
        return enrollUpdateTs;
    }

    public void setEnrollUpdateTs(Date enrollUpdateTs) {
        this.enrollUpdateTs = enrollUpdateTs;
    }

    public String getEndStatusDesc() {
        return endStatusDesc;
    }

    public void setEndStatusDesc(String endStatusDesc) {
        this.endStatusDesc = endStatusDesc;
    }

    public String getSpecialProgCategoryCd() {
        return specialProgCategoryCd;
    }

    public void setSpecialProgCategoryCd(String specialProgCategoryCd) {
        this.specialProgCategoryCd = specialProgCategoryCd;
    }

    public String getProgramDesc() {
        return programDesc;
    }

    public void setProgramDesc(String programDesc) {
        this.programDesc = programDesc;
    }
}