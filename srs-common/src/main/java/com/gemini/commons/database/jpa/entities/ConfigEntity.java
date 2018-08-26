package com.gemini.commons.database.jpa.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/21/18
 * Time: 5:34 PM
 */
//Todo: this should move to admin portal
@Entity
@Table(name = "config")
public class ConfigEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long currentSchoolYear;

    @Column(nullable = false)
    private Long preEnrollmentSchoolYear;

    @Column
    private Date preEnrollmentStart;

    @Column
    private Date preEnrollmentEnd;

    @Column(nullable = false)
    private boolean canEditFoundStudent = false;

    @Column(nullable = false)
    private Integer activationKeyExpireInHours;

    @Column(nullable = false)
    private Integer credKeyExpireInMinutes;

    @Column(nullable = false)
    private Integer minUserAgeToPreEnroll;

    @Column(nullable = false)
    private Integer maxAgeStudent;

    @Column(nullable = false)
    private Integer minAlternateSchools;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCurrentSchoolYear() {
        return currentSchoolYear;
    }

    public void setCurrentSchoolYear(Long currentSchoolYear) {
        this.currentSchoolYear = currentSchoolYear;
    }

    public Long getPreEnrollmentSchoolYear() {
        return preEnrollmentSchoolYear;
    }

    public void setPreEnrollmentSchoolYear(Long preEnrollmentSchoolYear) {
        this.preEnrollmentSchoolYear = preEnrollmentSchoolYear;
    }

    public Date getPreEnrollmentStart() {
        return preEnrollmentStart;
    }

    public void setPreEnrollmentStart(Date preEnrollmentStart) {
        this.preEnrollmentStart = preEnrollmentStart;
    }

    public Date getPreEnrollmentEnd() {
        return preEnrollmentEnd;
    }

    public void setPreEnrollmentEnd(Date preEnrollmentEnd) {
        this.preEnrollmentEnd = preEnrollmentEnd;
    }

    public boolean isCanEditFoundStudent() {
        return canEditFoundStudent;
    }

    public void setCanEditFoundStudent(boolean canEditFoundStudent) {
        this.canEditFoundStudent = canEditFoundStudent;
    }

    public Integer getActivationKeyExpireInHours() {
        return activationKeyExpireInHours;
    }

    public void setActivationKeyExpireInHours(Integer activationKeyExpireInHours) {
        this.activationKeyExpireInHours = activationKeyExpireInHours;
    }

    public Integer getCredKeyExpireInMinutes() {
        return credKeyExpireInMinutes;
    }

    public void setCredKeyExpireInMinutes(Integer credKeyExpireInMinutes) {
        this.credKeyExpireInMinutes = credKeyExpireInMinutes;
    }

    public Integer getMinUserAgeToPreEnroll() {
        return minUserAgeToPreEnroll;
    }

    public void setMinUserAgeToPreEnroll(Integer minUserAgeToPreEnroll) {
        this.minUserAgeToPreEnroll = minUserAgeToPreEnroll;
    }

    public Integer getMaxAgeStudent() {
        return maxAgeStudent;
    }

    public void setMaxAgeStudent(Integer maxAgeStudent) {
        this.maxAgeStudent = maxAgeStudent;
    }

    public Integer getMinAlternateSchools() {
        return minAlternateSchools;
    }

    public void setMinAlternateSchools(Integer minAlternateSchools) {
        this.minAlternateSchools = minAlternateSchools;
    }
}

