package com.gemini.beans.requests;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/7/18
 * Time: 10:19 PM
 */
public class SharedSchoolRequest {

    @NotNull
    private Long requestId;
    @NotNull
    private Long schoolId;

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }
}