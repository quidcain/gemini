package com.gemini.admin.beans.response;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 6/28/18
 * Time: 9:10 PM
 */
public class TransferAccountResponse {

    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static TransferAccountResponse success(){
        TransferAccountResponse response = new TransferAccountResponse();
        response.success = true;
        return response;
    }

    public static TransferAccountResponse error(String message){
        TransferAccountResponse response = new TransferAccountResponse();
        response.success = false;
        response.message = message;
        return response;
    }
}