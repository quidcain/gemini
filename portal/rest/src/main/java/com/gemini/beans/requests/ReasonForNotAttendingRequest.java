package com.gemini.beans.requests;

import com.gemini.commons.beans.types.ReasonForNotAttendingSchool;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/30/18
 * Time: 4:13 PM
 */
public class ReasonForNotAttendingRequest {

    @NotNull
    private Long requestId;
    @NotNull
    private ReasonForNotAttendingSchool reason;

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public ReasonForNotAttendingSchool getReason() {
        return reason;
    }

    public void setReason(ReasonForNotAttendingSchool reason) {
        this.reason = reason;
    }
}