package com.gemini.beans.internal;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/29/18
 * Time: 8:06 AM
 */
public class RequestSearchResult {
    private Long requestId;
    private boolean exists;
    private boolean belongsToUser;
    private boolean completed;
    private boolean ssnInUse;
    private boolean foundOnSieBySsn;
    private boolean allowedEditSsn = true;
    private boolean exceedMaxPreEnrollmentAllowed;

    public boolean cannotUseRequest() {
        return exists && !belongsToUser;
    }

    public boolean requestIsCompleted() {
        return exists && belongsToUser && completed;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public boolean isBelongsToUser() {
        return belongsToUser;
    }

    public void setBelongsToUser(boolean belongsToUser) {
        this.belongsToUser = belongsToUser;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isSsnInUse() {
        return ssnInUse;
    }

    public void setSsnInUse(boolean ssnInUse) {
        this.ssnInUse = ssnInUse;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public boolean isFoundOnSieBySsn() {
        return foundOnSieBySsn;
    }

    public void setFoundOnSieBySsn(boolean foundOnSieBySsn) {
        this.foundOnSieBySsn = foundOnSieBySsn;
    }

    public boolean isAllowedEditSsn() {
        return allowedEditSsn;
    }

    public void setAllowedEditSsn(boolean allowedEditSsn) {
        this.allowedEditSsn = allowedEditSsn;
    }

    public boolean isExceedMaxPreEnrollmentAllowed() {
        return exceedMaxPreEnrollmentAllowed;
    }

    public void setExceedMaxPreEnrollmentAllowed(boolean exceedMaxPreEnrollmentAllowed) {
        this.exceedMaxPreEnrollmentAllowed = exceedMaxPreEnrollmentAllowed;
    }
}