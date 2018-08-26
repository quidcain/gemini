package com.gemini.codes;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/20/18
 * Time: 5:02 PM
 */

public class ProxyAuthenticator extends Authenticator {

    private String user, password;

    public ProxyAuthenticator(String user, String password) {
        this.user = user;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password.toCharArray());
    }
}