package com.gemini.commons.database.jpa.entities;

import com.gemini.commons.beans.types.EnrollmentType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/30/18
 * Time: 7:28 PM
 */
@Entity
@Table(name = "pre_enrollment_edit_log")
public class PreEnrollmentEditLog {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long preEnrollmentId = -1L;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentType type = EnrollmentType.REGULAR;

    @Column(nullable = false)
    private Long preSelectedSchoolId = -1L;

    @Column(nullable = false)
    private Long firstOptionSchoolId = -1L;

    @Column(nullable = false)
    private Long secondOptionSchoolId = -1L;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

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

    public EnrollmentType getType() {
        return type;
    }

    public void setType(EnrollmentType type) {
        this.type = type;
    }

    public Long getPreSelectedSchoolId() {
        return preSelectedSchoolId;
    }

    public void setPreSelectedSchoolId(Long preSelectedSchoolId) {
        this.preSelectedSchoolId = preSelectedSchoolId;
    }

    public Long getFirstOptionSchoolId() {
        return firstOptionSchoolId;
    }

    public void setFirstOptionSchoolId(Long firstOptionSchoolId) {
        this.firstOptionSchoolId = firstOptionSchoolId;
    }

    public Long getSecondOptionSchoolId() {
        return secondOptionSchoolId;
    }

    public void setSecondOptionSchoolId(Long secondOptionSchoolId) {
        this.secondOptionSchoolId = secondOptionSchoolId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}