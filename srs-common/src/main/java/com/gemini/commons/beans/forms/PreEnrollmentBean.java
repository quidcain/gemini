package com.gemini.commons.beans.forms;

import com.gemini.commons.beans.integration.SchoolResponse;
import com.gemini.commons.beans.types.EnrollmentType;
import com.gemini.commons.beans.types.RequestStatus;
import com.gemini.commons.database.beans.School;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/21/18
 * Time: 11:58 AM
 */
public class PreEnrollmentBean {

    private Long id;
    private EnrollmentType type;
    private RequestStatus requestStatus;
    private String nextGradeLevel;
    private String nextGradeLevelDescription;
    private AddressBean schoolAddress;
    private Long schoolYear;
    private Long regionId = -1L;
    private Long districtId = -1L;
    private String municipalityCode = "NONE";
    private Long schoolId = -1L;
    private String schoolName;
    private Long extSchoolNumber = -1L;
    private String comments;
    private boolean hasPreviousEnrollment;
    private boolean hasPreEnrollment;
    private boolean canGoToFoundEnrollment;
    private boolean hasSchoolsSelected;
    private boolean deniedByUser;
    private Date submitDate;
    private PreEnrollmentStudentInfoBean student;
    private List<SchoolResponse> alternativeSchools;
    private Long sharedSchoolId;
    private SchoolResponse sharedSchool;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnrollmentType getType() {
        return type;
    }

    public void setType(EnrollmentType type) {
        this.type = type;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getNextGradeLevel() {
        return nextGradeLevel;
    }

    public void setNextGradeLevel(String nextGradeLevel) {
        this.nextGradeLevel = nextGradeLevel;
    }

    public String getNextGradeLevelDescription() {
        return nextGradeLevelDescription;
    }

    public void setNextGradeLevelDescription(String nextGradeLevelDescription) {
        this.nextGradeLevelDescription = nextGradeLevelDescription;
    }

    public AddressBean getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(AddressBean schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public Long getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(Long schoolYear) {
        this.schoolYear = schoolYear;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getMunicipalityCode() {
        return municipalityCode;
    }

    public void setMunicipalityCode(String municipalityCode) {
        this.municipalityCode = municipalityCode;
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

    public Long getExtSchoolNumber() {
        return extSchoolNumber;
    }

    public void setExtSchoolNumber(Long extSchoolNumber) {
        this.extSchoolNumber = extSchoolNumber;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean isHasPreviousEnrollment() {
        return hasPreviousEnrollment;
    }

    public void setHasPreviousEnrollment(boolean hasPreviousEnrollment) {
        this.hasPreviousEnrollment = hasPreviousEnrollment;
    }

    public boolean isHasPreEnrollment() {
        return hasPreEnrollment;
    }

    public void setHasPreEnrollment(boolean hasPreEnrollment) {
        this.hasPreEnrollment = hasPreEnrollment;
    }

    public boolean isCanGoToFoundEnrollment() {
        return canGoToFoundEnrollment;
    }

    public void setCanGoToFoundEnrollment(boolean canGoToFoundEnrollment) {
        this.canGoToFoundEnrollment = canGoToFoundEnrollment;
    }

    public boolean isHasSchoolsSelected() {
        return hasSchoolsSelected;
    }

    public void setHasSchoolsSelected(boolean hasSchoolsSelected) {
        this.hasSchoolsSelected = hasSchoolsSelected;
    }

    public boolean isDeniedByUser() {
        return deniedByUser;
    }

    public void setDeniedByUser(boolean deniedByUser) {
        this.deniedByUser = deniedByUser;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public PreEnrollmentStudentInfoBean getStudent() {
        return student;
    }

    public void setStudent(PreEnrollmentStudentInfoBean student) {
        this.student = student;
    }

    public List<SchoolResponse> getAlternativeSchools() {
        return alternativeSchools;
    }

    public void setAlternativeSchools(List<SchoolResponse> alternativeSchools) {
        this.alternativeSchools = alternativeSchools;
    }

    public Long getSharedSchoolId() {
        return sharedSchoolId;
    }

    public void setSharedSchoolId(Long sharedSchoolId) {
        this.sharedSchoolId = sharedSchoolId;
    }

    public SchoolResponse getSharedSchool() {
        return sharedSchool;
    }

    public void setSharedSchool(SchoolResponse sharedSchool) {
        this.sharedSchool = sharedSchool;
    }

    public String getRequestStatusText() {
        return this.requestStatus != null ? this.requestStatus.getDescription() : RequestStatus.ACTIVE.getDescription();
    }

    public String getEnrollmentTypeText() {
        return this.type != null ? this.type.getDescription() : "Ninguna";
    }
}