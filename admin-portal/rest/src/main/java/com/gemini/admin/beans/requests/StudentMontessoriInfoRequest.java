package com.gemini.admin.beans.requests;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 6/22/18
 * Time: 7:05 PM
 */
public class StudentMontessoriInfoRequest {

    private Long preEnrollmentId;
    private Boolean montessori;

    public Long getPreEnrollmentId() {
        return preEnrollmentId;
    }

    public void setPreEnrollmentId(Long preEnrollmentId) {
        this.preEnrollmentId = preEnrollmentId;
    }

    public Boolean getMontessori() {
        return montessori;
    }

    public void setMontessori(Boolean montessori) {
        this.montessori = montessori;
    }
}