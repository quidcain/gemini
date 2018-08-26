package com.gemini.admin.beans.forms;

import com.gemini.admin.beans.types.ReportType;
import com.gemini.commons.beans.types.EnrollmentType;
import com.gemini.commons.database.beans.School;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/24/18
 * Time: 5:22 AM
 */
public class EditRequestResult {
    private Long requestId;
    private EnrollmentType type;
    private ReportType summaryCount;
    private Long sieNumber;
    private String ssn;
    private String fatherName;
    private String studentName;
    private String gradeLevel;
    private Date submitDate;
    private Date dateOfBirth;
    private String schoolLabel;
    private List<School> schoolsSelected;
    private String resultSchool;
    private String email;
    private Date emailSentDate;
    private String status;
    private String cssStatusColor;


    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getEnrollmentTypeDescription(){
        return type != null ? type.getDescription(): "";
    }

    public EnrollmentType getType() {
        return type;
    }

    public void setType(EnrollmentType type) {
        this.type = type;
    }

    public String getSummaryDescription(){
        return summaryCount != null ? summaryCount.getDescription() : "";
    }

    public ReportType getSummaryCount() {
        return summaryCount;
    }

    public void setSummaryCount(ReportType summaryCount) {
        this.summaryCount = summaryCount;
    }

    public Long getSieNumber() {
        return sieNumber;
    }

    public void setSieNumber(Long sieNumber) {
        this.sieNumber = sieNumber;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSubmitted(){
        return submitDate == null ? "No" : "Sí";
    }

    public String getSchoolLabel() {
        return schoolLabel;
    }

    public void setSchoolLabel(String schoolLabel) {
        this.schoolLabel = schoolLabel;
    }

    public List<School> getSchoolsSelected() {
        return schoolsSelected;
    }

    public void setSchoolsSelected(List<School> schoolsSelected) {
        this.schoolsSelected = schoolsSelected;
    }

    public String getResultSchool() {
        return resultSchool;
    }

    public void setResultSchool(String resultSchool) {
        this.resultSchool = resultSchool;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getEmailSentDate() {
        return emailSentDate;
    }

    public void setEmailSentDate(Date emailSentDate) {
        this.emailSentDate = emailSentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCssStatusColor() {
        return cssStatusColor;
    }

    public void setCssStatusColor(String cssStatusColor) {
        this.cssStatusColor = cssStatusColor;
    }
}