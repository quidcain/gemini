package com.gemini.controllers;

import com.gemini.beans.forms.User;
import com.gemini.commons.beans.responses.HomeResponse;
import com.gemini.services.PreEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/27/18
 * Time: 2:31 PM
 */
@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private PreEnrollmentService preEnrollmentService;

    @RequestMapping
    public ResponseEntity<HomeResponse> retrieveHome(@AuthenticationPrincipal User user) {
        HomeResponse response = new HomeResponse();
        response.setPreEnrollments(preEnrollmentService.findPreEnrollmentByUser(user));
        return ResponseEntity.ok(response);
    }
}