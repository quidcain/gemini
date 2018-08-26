package com.gemini.admin.database.jpa.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 6/3/18
 * Time: 11:57 PM
 */
@Entity
@Table(name = "occupational_approvals", uniqueConstraints = {@UniqueConstraint(columnNames = {"preEnrollmentId"})})
public class OccupationalApprovalEntity {

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
    @Column(nullable = false)
    private String programCode;
    @Column
    private Boolean approved;
    @Column
    private Date creationDate = new Date();
    @Column
    private Date revisionDate;

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

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Date revisionDate) {
        this.revisionDate = revisionDate;
    }
}