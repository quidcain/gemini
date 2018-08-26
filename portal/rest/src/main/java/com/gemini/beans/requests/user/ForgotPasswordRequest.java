package com.gemini.beans.requests.user;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/15/18
 * Time: 1:56 AM
 */
public class ForgotPasswordRequest{
    @NotBlank
    @NotNull
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}