package com.gemini.commons.beans.responses;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/12/18
 * Time: 12:46 PM
 */
public class RegisterResponse {

    private String message;
    private boolean successfullyRegistered;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccessfullyRegistered() {
        return successfullyRegistered;
    }

    public void setSuccessfullyRegistered(boolean successfullyRegistered) {
        this.successfullyRegistered = successfullyRegistered;
    }

    public static RegisterResponse success() {
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.successfullyRegistered = true;
        return registerResponse;
    }

    public static RegisterResponse error(String message) {
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.message = message;
        return registerResponse;
    }
}