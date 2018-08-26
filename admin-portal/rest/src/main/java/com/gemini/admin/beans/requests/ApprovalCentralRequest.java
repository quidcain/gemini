package com.gemini.admin.beans.requests;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 6/28/18
 * Time: 9:13 PM
 */
public class ApprovalCentralRequest {

    private Long requestId;
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