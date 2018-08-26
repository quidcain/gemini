package com.gemini.security;

import com.gemini.beans.forms.User;
import com.gemini.beans.internal.FailureLogin;
import com.gemini.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
        User user = (User) authentication.getPrincipal();
        userService.saveLastLogin(user);
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        WebAuthenticationDetails details;
        String username;
        if (authentication.getPrincipal() instanceof String && authentication.getDetails() instanceof WebAuthenticationDetails) {
            username = (String) authentication.getPrincipal();
            details = (WebAuthenticationDetails) authentication.getDetails();
            FailureLogin failureLogin = new FailureLogin(username, details.getRemoteAddress(), details.getSessionId());
            if (StringUtils.hasText(username))
                userService.saveFailureLogin(failureLogin);
        }
    }
}