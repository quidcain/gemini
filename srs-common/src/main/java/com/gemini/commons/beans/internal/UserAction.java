package com.gemini.commons.beans.internal;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/16/18
 * Time: 2:23 PM
 */
public enum UserAction {

    ACCOUNT_ACTIVATION("/activate/%s"),
    RESET_PASSWORD("/reset/password/%s"),
    CANCEL_RESET_PASSWORD("/cancel/reset/password/%s");

    UserAction(String path) {
        this.path = path;
    }

    String path;

    public String getPath() {
        return path;
    }
}
