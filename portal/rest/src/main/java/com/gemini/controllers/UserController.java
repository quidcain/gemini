package com.gemini.controllers;

import com.gemini.beans.forms.User;
import com.gemini.beans.requests.FamilyInfoRequest;
import com.gemini.beans.requests.ParentProfileInfoRequest;
import com.gemini.commons.beans.responses.ResponseBase;
import com.gemini.services.CommonService;
import com.gemini.services.UserService;
import com.gemini.commons.utils.DateUtils;
import com.gemini.utils.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/6/18
 * Time: 2:24 PM
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private MessageHelper messageHelper;

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBase> save(@Valid @RequestBody ParentProfileInfoRequest request, BindingResult result, @AuthenticationPrincipal User loggedUser) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(messageHelper.missingFormFields(result));
        }
        int userAge = DateUtils.toYears(request.getDateOfBirth());
        if (userAge < commonService.getMinUserAgeToSubmitRequest()) {
            ResponseBase response =
                    ResponseBase.error(messageHelper.processMessages("user.min.age.validation"));
            return ResponseEntity.badRequest().body(response);
        }

        request.setUserId(loggedUser.getId());
        boolean saved = userService.updateUser(request);
        if (!saved)
            return ResponseEntity.ok(ResponseBase.error(messageHelper.processMessage("error.saving.profile")));
        else
            completeProfile(request, loggedUser);

        return ResponseEntity.ok(ResponseBase.success(loggedUser));
    }

    @RequestMapping(value = "/complete/profile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBase> completeProfile(@Valid @RequestBody FamilyInfoRequest request, BindingResult result, @AuthenticationPrincipal User loggedUser) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(messageHelper.missingFormFields(result));
        }
        request.setUserId(loggedUser.getId());
        boolean saved = userService.completeProfile(request);
        if (!saved)
            return ResponseEntity.ok(ResponseBase.error(messageHelper.processMessage("error.saving.profile")));
        else
            loggedUser.setProfileCompleted(true);
        return ResponseEntity.ok(ResponseBase.success(loggedUser));
    }

    private void completeProfile(ParentProfileInfoRequest request, User loggedUser) {
        loggedUser.setFirstName(request.getFirstName());
        loggedUser.setMiddleName(request.getMiddleName());
        loggedUser.setLastName(request.getLastName());
        loggedUser.setProfileCompleted(true);
    }


}