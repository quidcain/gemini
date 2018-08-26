package com.gemini.security;

import com.gemini.services.ReCaptchaValidatorService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/1/18
 * Time: 5:48 PM
 */

@Component
public class ReCaptchaSecurityFilter extends OncePerRequestFilter {

    private ReCaptchaValidatorService reCaptchaValidator;

    public ReCaptchaSecurityFilter(ReCaptchaValidatorService reCaptchaValidator) {
        this.reCaptchaValidator = reCaptchaValidator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String queryPath = new UrlPathHelper().getPathWithinApplication(request);


        if (queryPath.startsWith("/account/") && !RequestMethod.GET.name().equals(request.getMethod())) {
            String token = request.getHeader("recaptcha-token");
            boolean valid = reCaptchaValidator.verifyReCaptcha(token);
            if (!valid)
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            else
                filterChain.doFilter(request, response);

        } else {
            filterChain.doFilter(request, response);
        }

    }
}