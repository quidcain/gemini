package com.gemini.commons.database.jpa.entities;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/22/18
 * Time: 4:21 PM
 */
@Entity
@Table(name = "pre_enrollment_alt_schools")
public class PreEnrollmentAlternateSchoolEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long schoolId;

    @Column(nullable = false)
    private String municipalityCode;

    @Column(nullable = false)
    private Long regionId = -1L;

    @Column(nullable = false)
    private Long districtId = -1L;

    @Column(nullable = false)
    private Integer priority = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    private PreEnrollmentRequestEntity preEnrollment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public String getMunicipalityCode() {
        return municipalityCode;
    }

    public void setMunicipalityCode(String municipalityCode) {
        this.municipalityCode = municipalityCode;
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public PreEnrollmentRequestEntity getPreEnrollment() {
        return preEnrollment;
    }

    public void setPreEnrollment(PreEnrollmentRequestEntity preEnrollment) {
        this.preEnrollment = preEnrollment;
    }
}