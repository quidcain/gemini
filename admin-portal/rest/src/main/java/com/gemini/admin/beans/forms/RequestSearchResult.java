package com.gemini.admin.beans.forms;

import com.gemini.commons.beans.integration.SchoolResponse;
import com.gemini.commons.database.beans.School;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/23/18
 * Time: 5:44 AM
 */
public class RequestSearchResult {

    private Long requestId;
    private String email;
    private String studentName;
    private String gradeLevel;
    private String sieStudentId;
    private String status;
    private String cssStatusColor;
    private Date creationDate;
    private Date submitDate;
    private boolean canApprove;
    private List<SchoolResponse> schoolsSelected;


    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getSieStudentId() {
        return sieStudentId;
    }

    public void setSieStudentId(String sieStudentId) {
        this.sieStudentId = sieStudentId;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public String getSubmitted(){
        return submitDate == null ? "No" : "Sí";
    }

    public boolean isCanApprove() {
        return canApprove;
    }

    public void setCanApprove(boolean canApprove) {
        this.canApprove = canApprove;
    }

    public List<SchoolResponse> getSchoolsSelected() {
        return schoolsSelected;
    }

    public void setSchoolsSelected(List<SchoolResponse> schoolsSelected) {
        this.schoolsSelected = schoolsSelected;
    }
}