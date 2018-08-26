package com.gemini.admin.beans.requests;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 6/28/18
 * Time: 9:10 PM
 */
public class TransferAccountRequest {

    private String email;
    private Long requestId;
    private String emailNew;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getEmailNew() {
        return emailNew;
    }

    public void setEmailNew(String emailNew) {
        this.emailNew = emailNew;
    }
}