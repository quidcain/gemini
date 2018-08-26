package com.gemini.commons.database.jpa.entities;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/9/18
 * Time: 6:58 AM
 */
@Entity(name = "pre_enrollment_voc_schools")
public class PreEnrollmentVocationalSchoolEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long schoolId;

    @Column(nullable = false)
    private Long regionId = -1L;

    @Column(nullable = false)
    private Long districtId = -1L;

    @Column(nullable = false)
    private String municipalityCode = "NONE";

    @Column(nullable = false)
    private String programCode;

    @Column(nullable = false)
    private String programDescription;

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

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getProgramDescription() {
        return programDescription;
    }

    public void setProgramDescription(String programDescription) {
        this.programDescription = programDescription;
    }

    public PreEnrollmentRequestEntity getPreEnrollment() {
        return preEnrollment;
    }

    public void setPreEnrollment(PreEnrollmentRequestEntity preEnrollment) {
        this.preEnrollment = preEnrollment;
    }
}