package com.gemini.admin.security;

import com.gemini.admin.beans.AdminUser;
import com.gemini.admin.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/23/18
 * Time: 3:15 PM
 */
@Component
public class AuthenticationEventImpl implements AuthenticationEventPublisher {

    @Autowired
    private UserService userService;

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        AdminUser user = (AdminUser) authentication.getPrincipal();
        userService.saveLastLogin(user);
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        String username;
        if (authentication.getPrincipal() instanceof String && authentication.getDetails() instanceof WebAuthenticationDetails) {
            username = (String) authentication.getPrincipal();
            userService.failureLogin(username);

        }
    }
}