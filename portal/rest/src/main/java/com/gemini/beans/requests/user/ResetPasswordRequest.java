package com.gemini.beans.requests.user;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/16/18
 * Time: 1:26 AM
 */
public class ResetPasswordRequest {

    @NotNull
    @NotBlank
    private String credentialLostKey;
    @NotNull
    @NotBlank
    private String password;
    @NotNull
    @NotBlank
    private String confirmPassword;

    public String getCredentialLostKey() {
        return credentialLostKey;
    }

    public void setCredentialLostKey(String credentialLostKey) {
        this.credentialLostKey = credentialLostKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}