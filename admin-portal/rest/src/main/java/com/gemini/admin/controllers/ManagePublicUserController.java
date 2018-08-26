package com.gemini.admin.controllers;

import com.gemini.admin.beans.AdminUser;
import com.gemini.admin.beans.forms.EditRequestResult;
import com.gemini.admin.beans.forms.EditUserResult;
import com.gemini.admin.beans.forms.UserSearchResult;
import com.gemini.admin.beans.requests.ActivateUserRequest;
import com.gemini.admin.beans.requests.PortalUserSearchRequest;
import com.gemini.admin.services.ManagePortalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/7/18
 * Time: 9:49 AM
 */
@RestController
@RequestMapping("/portal/user/manage")
public class ManagePublicUserController {

    @Autowired
    ManagePortalUserService managePortalUserService;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<List<UserSearchResult>> searchRequest(@RequestBody PortalUserSearchRequest request, @AuthenticationPrincipal AdminUser adminUser) {
        return ResponseEntity.ok(managePortalUserService.searchUser(request, adminUser));
    }

    @RequestMapping(value = "/edit/{userId}", method = RequestMethod.GET)
    public ResponseEntity<EditUserResult> getRequest(@PathVariable Long userId) {
        return ResponseEntity.ok(managePortalUserService.getUser(userId));
    }



    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity saveUser(@RequestBody ActivateUserRequest request, @AuthenticationPrincipal AdminUser adminUser) {
        return ResponseEntity.ok(managePortalUserService.saveUser(request, adminUser));
    }
}