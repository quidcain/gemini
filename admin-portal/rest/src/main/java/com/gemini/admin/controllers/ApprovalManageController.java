package com.gemini.admin.controllers;

import com.gemini.admin.beans.AdminUser;
import com.gemini.admin.beans.forms.EditStudentRequest;
import com.gemini.admin.beans.forms.StudentSearchResult;
import com.gemini.admin.beans.requests.ApprovalInputRequest;
import com.gemini.admin.beans.requests.StudentMontessoriInfoRequest;
import com.gemini.admin.services.ApprovalService;
import com.gemini.commons.beans.integration.SchoolResponse;
import com.gemini.commons.database.beans.School;
import com.gemini.commons.utils.CopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/31/18
 * Time: 9:53 PM
 */
@RestController
@RequestMapping("/student/approval/manage")
public class ApprovalManageController {

    @Autowired
    private ApprovalService approvalService;

    @RequestMapping(value = "/schools")
    public ResponseEntity<List<SchoolResponse>> retrieveApprovalSchools (@AuthenticationPrincipal AdminUser adminUser) {
        List<School> schools = approvalService.getApprovalSchools(adminUser);
        return ResponseEntity.ok(CopyUtils.convert(schools, SchoolResponse.class));
    }

    @RequestMapping(value = "/search/{schoolId}/gradeLevel/{gradeLevel}")
    public ResponseEntity<List<StudentSearchResult>> searchCaps(@PathVariable Long schoolId, @PathVariable String gradeLevel) {
        return ResponseEntity.ok(approvalService.getStudentsBySchoolAndGradeLevel(schoolId, gradeLevel));
    }

    @RequestMapping(value = "/edit/{preEnrollmentId}/school/{schoolId}", method = RequestMethod.GET)
    public ResponseEntity<EditStudentRequest> getRequest(@PathVariable Long preEnrollmentId, @PathVariable Long schoolId, @AuthenticationPrincipal AdminUser adminUser) {
        return ResponseEntity.ok(approvalService.getStudentRequestByPreEnrollmentId(preEnrollmentId, schoolId));
    }

//    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity saveRequest(@RequestBody ApprovalInputRequest request, @AuthenticationPrincipal AdminUser adminUser) {
        return ResponseEntity.ok(approvalService.saveDirectorInput(request, adminUser));
    }

    @RequestMapping(value = "/montessori/check/save", method = RequestMethod.POST)
    public ResponseEntity saveMontessori(@RequestBody StudentMontessoriInfoRequest request, @AuthenticationPrincipal AdminUser adminUser) {
        return ResponseEntity.ok(approvalService.saveStudentMontessoriInfo(request, adminUser));
    }
}