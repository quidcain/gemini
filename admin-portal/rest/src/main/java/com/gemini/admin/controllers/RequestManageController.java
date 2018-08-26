package com.gemini.admin.controllers;

import com.gemini.admin.beans.AdminUser;
import com.gemini.admin.beans.forms.Comment;
import com.gemini.admin.beans.forms.EditRequestResult;
import com.gemini.admin.beans.forms.RequestSearchCriteria;
import com.gemini.admin.beans.forms.RequestSearchResult;
import com.gemini.admin.beans.requests.ApprovalCentralRequest;
import com.gemini.admin.beans.requests.ReactivatePreEnrollmentRequest;
import com.gemini.admin.beans.requests.TransferAccountRequest;
import com.gemini.admin.beans.response.TransferAccountResponse;
import com.gemini.admin.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/23/18
 * Time: 5:46 AM
 */
@RestController
@RequestMapping("/request/manage")
public class RequestManageController {

    @Autowired
    private RequestService requestService;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<List<RequestSearchResult>> searchRequest(@RequestBody RequestSearchCriteria criteria) {
        return ResponseEntity.ok(requestService.searchRequest(criteria));
    }

    @RequestMapping(value = "/edit/{preEnrollmentId}", method = RequestMethod.GET)
    public ResponseEntity<EditRequestResult> getRequest(@PathVariable Long preEnrollmentId) {
        return ResponseEntity.ok(requestService.getRequest(preEnrollmentId));
    }

    @RequestMapping(value = "/comments/{preEnrollmentId}", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> getRequestComments(@PathVariable Long preEnrollmentId) {
        return ResponseEntity.ok(requestService.getRequestComments(preEnrollmentId));
    }

//    @RequestMapping(value = "/reactivate", method = RequestMethod.POST)
    public ResponseEntity reactivate(@RequestBody ReactivatePreEnrollmentRequest request, @AuthenticationPrincipal AdminUser user) {
        boolean saved = requestService.reactivatePreEnrollment(request, user);
        if (saved)
            return ResponseEntity.ok().build();
        return ResponseEntity.status(500).build();
    }

    @RequestMapping(value = "/deactivate", method = RequestMethod.POST)
    public ResponseEntity deactivate(@RequestBody ReactivatePreEnrollmentRequest request, @AuthenticationPrincipal AdminUser user) {
        boolean saved = requestService.deactivatePreEnrollment(request, user);
        if (saved)
            return ResponseEntity.ok().build();
        return ResponseEntity.status(500).build();
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public ResponseEntity<TransferAccountResponse> transferRequest(@RequestBody TransferAccountRequest request, @AuthenticationPrincipal AdminUser user) {
        return ResponseEntity.ok(requestService.transferRequest(request, user));
    }

//    @RequestMapping(value = "/manual/approval", method = RequestMethod.POST)
    public ResponseEntity manualApproval(@RequestBody ApprovalCentralRequest request, @AuthenticationPrincipal AdminUser user) {
        boolean saved = requestService.approveRequest(request, user);
        if (saved)
            return ResponseEntity.ok().build();
        return ResponseEntity.status(500).build();
    }

}