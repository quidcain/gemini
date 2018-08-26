package com.gemini.admin.database.jpa.entities;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/31/18
 * Time: 11:28 PM
 */

import com.gemini.admin.beans.types.ReasonToDenyEnrollment;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "approval_director_log")
public class ApprovalDirectorLogEntity {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private Long preEnrollmentId;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long schoolId;
    @Column(nullable = false)
    private String gradeLevel;
    @Column
    private Boolean approved;
    @Enumerated(EnumType.STRING)
    @Column
    private ReasonToDenyEnrollment reasonToDenyEnrollment;
    @Column
    private String commentText;
    @Column
    private Date actionDate = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPreEnrollmentId() {
        return preEnrollmentId;
    }

    public void setPreEnrollmentId(Long preEnrollmentId) {
        this.preEnrollmentId = preEnrollmentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }


    public ReasonToDenyEnrollment getReasonToDenyEnrollment() {
        return reasonToDenyEnrollment;
    }

    public void setReasonToDenyEnrollment(ReasonToDenyEnrollment reasonToDenyEnrollment) {
        this.reasonToDenyEnrollment = reasonToDenyEnrollment;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }
}