package com.gemini.controllers;

import com.gemini.beans.requests.StudentAnswerAgreementRequest;
import com.gemini.commons.beans.forms.StudentDemographicsBean;
import com.gemini.beans.requests.StudentAnswerRequest;
import com.gemini.beans.requests.StudentDemographicsRequest;
import com.gemini.commons.beans.responses.ResponseBase;
import com.gemini.commons.utils.ValidationUtils;
import com.gemini.services.StudentService;
import com.gemini.utils.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/27/18
 * Time: 11:44 PM
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private MessageHelper messageHelper;


    @RequestMapping(value = "/{studentId}/demographics/retrieve")
    public ResponseEntity<ResponseBase> retrieveDemographics(@PathVariable Long studentId) {
        StudentDemographicsBean demographicsBean = null;
        if (ValidationUtils.valid(studentId)) {
            demographicsBean = studentService.retrieveDemographicsInfo(studentId);
        }
        return ResponseEntity.ok(ResponseBase.success(demographicsBean));
    }

    @RequestMapping(value = "/{studentId}/demographics/save", method = RequestMethod.POST)
    public ResponseEntity<ResponseBase> saveDemographicsInfo(@PathVariable Long studentId, @Valid @RequestBody StudentDemographicsRequest request, BindingResult result) {
        boolean saved;
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(messageHelper.missingFormFields(result));
        }

        request.setStudentId(studentId);
        saved = studentService.saveDemographicsInfo(request);
        if (saved) {
            return ResponseEntity.ok(ResponseBase.success());
        }

        return ResponseEntity.ok(ResponseBase.error(messageHelper.generalError()));
    }

    @RequestMapping(value = "/{studentId}/born/pr/save", method = RequestMethod.POST)
    public ResponseEntity<ResponseBase> saveBornPRAnswer(@PathVariable Long studentId, @Valid @RequestBody StudentAnswerRequest request, BindingResult result) {
        boolean saved;
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(messageHelper.missingFormFields(result));
        }

        request.setStudentId(studentId);
        saved = studentService.saveIsBornPR(request);
        if (saved) {
            return ResponseEntity.ok(ResponseBase.success());
        }

        return ResponseEntity.ok(ResponseBase.error(messageHelper.generalError()));
    }

    @RequestMapping(value = "/{studentId}/hispanic/save", method = RequestMethod.POST)
    public ResponseEntity<ResponseBase> saveHispanicAnswer(@PathVariable Long studentId, @Valid @RequestBody StudentAnswerRequest request, BindingResult result) {
        boolean saved;
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(messageHelper.missingFormFields(result));
        }


        request.setStudentId(studentId);
        saved = studentService.saveIsHispanic(request);
        if (saved) {
            return ResponseEntity.ok(ResponseBase.success());
        }

        return ResponseEntity.ok(ResponseBase.error(messageHelper.generalError()));
    }

    @RequestMapping(value = "/{studentId}/request/transportation/save", method = RequestMethod.POST)
    public ResponseEntity<ResponseBase> saveRequestTransportationAnswer(@PathVariable Long studentId, @Valid @RequestBody StudentAnswerRequest request, BindingResult result) {
        boolean saved;
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(messageHelper.missingFormFields(result));
        }

        request.setStudentId(studentId);
        saved = studentService.saveRequestTransportation(request);
        if (saved) {
            return ResponseEntity.ok(ResponseBase.success());
        }

        return ResponseEntity.ok(ResponseBase.error(messageHelper.generalError()));
    }

    @RequestMapping(value = "/{studentId}/agreement/save", method = RequestMethod.POST)
    public ResponseEntity<ResponseBase> saveAgreementAnswer(@PathVariable Long studentId, @Valid @RequestBody StudentAnswerAgreementRequest request, BindingResult result) {
        boolean saved = false;
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(messageHelper.missingFormFields(result));
        }

        request.setStudentId(studentId);
        saved = studentService.saveAgreementAnswer(request);
        if (saved) {
            return ResponseEntity.ok(ResponseBase.success());
        }

        return ResponseEntity.ok(ResponseBase.error(messageHelper.generalError()));
    }


}