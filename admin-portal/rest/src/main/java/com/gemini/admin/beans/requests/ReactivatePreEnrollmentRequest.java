package com.gemini.admin.beans.requests;

import com.gemini.commons.beans.types.EnrollmentType;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/23/18
 * Time: 7:44 AM
 */
public class ReactivatePreEnrollmentRequest {
    private Long requestId;
    private EnrollmentType type;
    private String comment;

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public EnrollmentType getType() {
        return type;
    }

    public void setType(EnrollmentType type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}