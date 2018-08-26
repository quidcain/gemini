package com.gemini.admin.beans.forms;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/7/18
 * Time: 3:12 PM
 */
public class EditUserResult {
    private Long userId;
    private String email;
    private String fatherName;
    private Date dateOfBirth;
    private Date lastLogin;
    private Date creationDate;
    private Date revisionDate;
    private boolean enabled;
    private boolean profileCompleted;


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

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Date revisionDate) {
        this.revisionDate = revisionDate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isProfileCompleted() {
        return profileCompleted;
    }

    public void setProfileCompleted(boolean profileCompleted) {
        this.profileCompleted = profileCompleted;
    }

    public String getActivated(){
        return enabled ? "Sí" : "No";
    }

    public String getProfileCompleted(){
        return profileCompleted ? "Sí" : "No";
    }
}