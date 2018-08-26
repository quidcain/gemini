package com.gemini.admin.beans.requests;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/7/18
 * Time: 9:51 AM
 */
public class PortalUserSearchRequest {

    private Long userId;
    private String email;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}