package com.gemini.beans.internal;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/27/18
 * Time: 11:33 AM
 */
public class FailureLogin {

    private String username;
    private String remoteIp;
    private String sessionId;

    public FailureLogin(String username, String remoteIp, String sessionId) {
        this.username = username;
        this.remoteIp = remoteIp;
        this.sessionId = sessionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}