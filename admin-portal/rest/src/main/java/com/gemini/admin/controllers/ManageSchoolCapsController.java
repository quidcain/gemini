package com.gemini.admin.controllers;

import com.gemini.admin.beans.AdminUser;
import com.gemini.admin.beans.forms.EditSchoolGradeLimit;
import com.gemini.admin.beans.forms.SchoolGradeLimitResult;
import com.gemini.admin.beans.forms.UserSearchResult;
import com.gemini.admin.beans.requests.SchoolGradeLimitEditRequest;
import com.gemini.admin.services.ManagePortalUserService;
import com.gemini.admin.services.ManageSchoolCapsService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/28/18
 * Time: 7:53 PM
 */
@RestController
@RequestMapping("/school/caps/manage")
public class ManageSchoolCapsController {
    static Logger logger = LoggerFactory.getLogger(ManagePortalUserService.class.getName());

    @Autowired
    private ManageSchoolCapsService schoolCapsService;


    @RequestMapping(value = "/search/{schoolId}")
    public ResponseEntity<List<SchoolGradeLimitResult>> searchCaps(@PathVariable Long schoolId) {
        return ResponseEntity.ok(schoolCapsService.getGradeLimitsBySchool(schoolId));
    }

    @RequestMapping(value = "/edit/{schoolGradeLimitId}", method = RequestMethod.GET)
    public ResponseEntity<EditSchoolGradeLimit> getRequest(@PathVariable Long schoolGradeLimitId, @AuthenticationPrincipal AdminUser adminUser) {
        return ResponseEntity.ok(schoolCapsService.getGradeLimit(schoolGradeLimitId, adminUser.getUserId()));
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity saveRequest(@RequestBody SchoolGradeLimitEditRequest request, @AuthenticationPrincipal AdminUser adminUser) {
        return ResponseEntity.ok(schoolCapsService.saveChangeRequestForGradeLimit(request, adminUser));
    }
}