package com.gemini.admin.beans.requests;

import com.gemini.admin.beans.types.ReasonToDenyEnrollment;
import com.gemini.commons.beans.forms.VocationalProgramSelection;
import com.gemini.commons.beans.types.EnrollmentType;
import com.gemini.commons.beans.types.RequestStatus;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/31/18
 * Time: 11:18 PM
 */
public class ApprovalInputRequest {

    private Long preEnrollmentId;
    private String gradeLevel;
    private boolean approve;
    private Long schoolId;
    private ReasonToDenyEnrollment reasonToDeny;
    private String comment;
    private boolean occupationEnrollment;
    private VocationalProgramSelection program;
    //these fields are determine by service
    private RequestStatus requestStatus;
    private EnrollmentType type;

    public Long getPreEnrollmentId() {
        return preEnrollmentId;
    }

    public void setPreEnrollmentId(Long preEnrollmentId) {
        this.preEnrollmentId = preEnrollmentId;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public boolean isApprove() {
        return approve;
    }

    public void setApprove(boolean approve) {
        this.approve = approve;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public ReasonToDenyEnrollment getReasonToDeny() {
        return reasonToDeny;
    }

    public void setReasonToDeny(ReasonToDenyEnrollment reasonToDeny) {
        this.reasonToDeny = reasonToDeny;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isOccupationEnrollment() {
        return occupationEnrollment;
    }

    public void setOccupationEnrollment(boolean occupationEnrollment) {
        this.occupationEnrollment = occupationEnrollment;
    }

    public VocationalProgramSelection getProgram() {
        return program;
    }

    public void setProgram(VocationalProgramSelection program) {
        this.program = program;
    }

    public  String getReasonToDenyString(){
        return  reasonToDeny != null ? reasonToDeny.name() : null;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public EnrollmentType getType() {
        return type;
    }

    public void setType(EnrollmentType type) {
        this.type = type;
    }

    public Long getResultSchoolId() {
        return approve ? getSchoolId() : null;
    }
}