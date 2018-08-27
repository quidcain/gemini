package com.gemini.controllers;

import com.gemini.beans.forms.User;
import com.gemini.beans.internal.RequestSearchResult;
import com.gemini.beans.internal.SchoolValidationRequest;
import com.gemini.beans.requests.ReasonForNotAttendingRequest;
import com.gemini.beans.requests.SharedSchoolRequest;
import com.gemini.beans.requests.StudentAnswerRequest;
import com.gemini.beans.requests.enrollment.AlternateSchoolPreEnrollmentSubmitRequest;
import com.gemini.beans.requests.enrollment.PreEnrollmentInitialRequest;
import com.gemini.beans.requests.enrollment.PreEnrollmentSubmitRequest;
import com.gemini.beans.requests.enrollment.VocationalPreEnrollmentSubmitRequest;
import com.gemini.commons.beans.forms.*;
import com.gemini.commons.beans.integration.SchoolValidationResponse;
import com.gemini.commons.beans.responses.ResponseBase;
import com.gemini.commons.beans.types.ReasonForNotAttendingSchool;
import com.gemini.commons.utils.DateUtils;
import com.gemini.commons.utils.Utils;
import com.gemini.commons.utils.ValidationUtils;
import com.gemini.services.*;
import com.gemini.utils.MessageHelper;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/16/18
 * Time: 2:24 PM
 */
@RestController
@RequestMapping("/pre/enrollment")
public class PreEnrollmentRequestController {
    static Logger logger = LoggerFactory.getLogger(PreEnrollmentRequestController.class.getName());

    @Autowired
    private PreEnrollmentService preEnrollmentService;
    @Autowired
    private MailService mailService;
    @Autowired
    private MessageHelper messageHelper;
    @Autowired
    private CommonService commonService;
    @Autowired
    private SchoolAvailableSpaceService schoolAvailableSpaceService;
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/{requestId}")
    public ResponseEntity<ResponseBase> retrieve(@PathVariable Long requestId) {
        PreEnrollmentStudentInfoBean studentInfo = null;
        //todo: validate user has access to this enrollment
        if (ValidationUtils.valid(requestId))
            studentInfo = preEnrollmentService.findPreEnrollmentById(requestId);
        if (studentInfo != null)
            studentInfo.setSsn(Utils.formatSSN(studentInfo.getSsn()));
        return ResponseEntity.ok(ResponseBase.success(requestId, studentInfo));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<ResponseBase> savePreEnrollmentRequest(@Valid @RequestBody PreEnrollmentInitialRequest initialRequest, BindingResult result, @AuthenticationPrincipal User loggedUser) {
        if (result.hasErrors() && !ValidationUtils.valid(initialRequest.getStudentNumber())) {
            ResponseBase responseBase = messageHelper.missingFormFields(result);
            responseBase.setRequestId(initialRequest.getRequestId());
            return ResponseEntity.badRequest().body(responseBase);
        }

//        if (!ValidationUtils.valid(initialRequest.getStudentNumber())) {
//            ResponseBase responseBase = ResponseBase.error("Validaci\u00f3n", messageHelper.processMessages("enrollment.error"));
//            return ResponseEntity.ok().body(responseBase);
//        }

        RequestSearchResult searchResult = preEnrollmentService.exists(initialRequest, loggedUser);
        if (searchResult.cannotUseRequest()) {
            ResponseBase responseBase = ResponseBase.error("Validaci\u00f3n", messageHelper.processMessages("enrollment.already.exists"));
            responseBase.setRequestId(searchResult.getRequestId());
            return ResponseEntity.ok().body(responseBase);
        }

        if (searchResult.isSsnInUse()) {
            ResponseBase responseBase = ResponseBase.error("Validaci\u00f3n", messageHelper.processMessages("enrollment.already.ssn.sie"));
            responseBase.setRequestId(searchResult.getRequestId());
            return ResponseEntity.ok().body(responseBase);
        }


        if (searchResult.requestIsCompleted()) {
            ResponseBase responseBase = ResponseBase.error("Validaci\u00f3n", messageHelper.processMessages("enrollment.already.submitted"));
            responseBase.setRequestId(searchResult.getRequestId());
            return ResponseEntity.ok().body(responseBase);
        }

        if (searchResult.isExists() && !ValidationUtils.valid(initialRequest.getRequestId())) {
            ResponseBase responseBase = ResponseBase.error(messageHelper.processMessages("enrollment.already.active"));
            responseBase.setRequestId(searchResult.getRequestId());
            responseBase.setFound(true);
            return ResponseEntity.ok().body(responseBase);
        }

        if (searchResult.isFoundOnSieBySsn()) {
            ResponseBase responseBase = ResponseBase.error("Validaci\u00f3n", messageHelper.processMessages("enrollment.already.exists.sie"));
            responseBase.setFoundOnSIE(true);
            return ResponseEntity.ok().body(responseBase);
        }

        if (!searchResult.isAllowedEditSsn()) {
            ResponseBase responseBase = ResponseBase.error("Validaci\u00f3n", messageHelper.processMessages("enrollment.already.ssn.sie"));
            responseBase.setRequestId(initialRequest.getRequestId());
            return ResponseEntity.ok().body(responseBase);
        }

        int userAge = DateUtils.toYears(initialRequest.getDateOfBirth());
        if (userAge > commonService.getMaxAgeStudent()) {
            ResponseBase responseBase =
                    ResponseBase.error(messageHelper.processMessages("enrollment.student.max.age.validation"));
            responseBase.setRequestId(initialRequest.getRequestId());
            return ResponseEntity.badRequest().body(responseBase);
        }

        Date currentDate = commonService.getCurrentDate();
        if (currentDate.before(initialRequest.getDateOfBirth())) {
            ResponseBase responseBase =
                    ResponseBase.error(messageHelper.processMessages("enrollment.student.dob.future.date"));
            responseBase.setRequestId(initialRequest.getRequestId());
            return ResponseEntity.badRequest().body(responseBase);
        }


        if (preEnrollmentService.schoolInExclusionList(initialRequest)) {
            ResponseBase responseBase = ResponseBase.error("Validaci\u00f3n", messageHelper.processMessages("enrollment.school.exclusion.list"));
            responseBase.setRequestId(initialRequest.getRequestId());
            return ResponseEntity.ok().body(responseBase);
        }

        ResponseEntity<ResponseBase> response;
        if (!searchResult.isExists()) {
            if (searchResult.isExceedMaxPreEnrollmentAllowed()) {
                ResponseBase responseBase = ResponseBase.error("Validaci\u00f3n", messageHelper.processMessages("enrollment.max.exceed.per.user"));
                responseBase.setRequestId(initialRequest.getRequestId());
                return ResponseEntity.ok().body(responseBase);
            }
            response = handleCreatePreEnrollment(initialRequest, loggedUser);
        } else {
            if (!ValidationUtils.valid(initialRequest.getRequestId())) {
                initialRequest.setRequestId(searchResult.getRequestId());
            }
            response = handleEditPreEnrollment(initialRequest);
        }

        return response;
    }

//    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseEntity<ResponseBase> submitPreEnrollment(@RequestBody PreEnrollmentSubmitRequest submitRequest, @AuthenticationPrincipal User loggedUser) {
        boolean saved = false;
        try {
            saved = preEnrollmentService.submitPreEnrollment(submitRequest);
            mailService.sendPreEnrollmentSubmitEmail(loggedUser, submitRequest);
            cleanWorkingRequest(loggedUser);
        } catch (Exception e) {
            logger.error("error submitted pre-enrollment " + submitRequest.getRequestId(), e);
        }
        if (saved)
            return ResponseEntity.ok(ResponseBase.success(submitRequest.getRequestId()));
        return ResponseEntity.ok(ResponseBase.error("Error submitting pre-enrollmment"));
    }

    @RequestMapping(value = "/{requestId}/address")
    public ResponseEntity<PreEnrollmentAddressBean> getPreEnrollmentAddress(@PathVariable Long requestId) {
        PreEnrollmentAddressBean addressBean = preEnrollmentService.getAddress(requestId);
        return ResponseEntity.ok(addressBean);
    }

    @RequestMapping(value = "/{requestId}/address/save", method = RequestMethod.POST)
    public ResponseEntity<ResponseBase> savePreEnrollmentAddress(@PathVariable Long requestId, @Valid @RequestBody PreEnrollmentAddressBean request, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(messageHelper.missingFormFields(result));
        }

        if (request.getPhysical() != null && (request.getPhysical().getLatitude() == null || request.getPhysical().getLongitude() == null)) {
            return ResponseEntity.badRequest().body(ResponseBase.error("Debe localizar su dirección en el mapa"));
        }

        if (request.getPhysical() != null && !commonService.isPRZipcode(request.getPhysical().getZipcode())) {
            return ResponseEntity.badRequest().body(ResponseBase.error("Dirección debe ser en Puerto Rico"));
        }
        boolean saved;
        saved = preEnrollmentService.updateStudentAddress(request.getPhysical());

        studentService.saveRequestTransportation(request.getNeedTransportation(), requestId);
//            saved &= preEnrollmentService.updateStudentAddress(request.getPostal());
        return ResponseEntity.ok(saved ? ResponseBase.success(requestId) : ResponseBase.error("Error saving address"));
    }

    @RequestMapping(value = "/reasons/for/not/attending/school", method = RequestMethod.GET)
    public ResponseEntity<List<EnumCode>> getReasonCodes() {
        List<EnumCode> enumCodes = FluentIterable
                .from(ReasonForNotAttendingSchool.values())
                .transform(new Function<ReasonForNotAttendingSchool, EnumCode>() {
                    @Override
                    public EnumCode apply(ReasonForNotAttendingSchool reason) {
                        return new EnumCode(reason.name(), reason.getDescription());
                    }
                })
                .toList();
        return ResponseEntity.ok(enumCodes);
    }

//    @RequestMapping(value = "/reason/for/not/attending/school/save", method = RequestMethod.POST)
    public ResponseEntity<ResponseBase> saveReasonForNotAttendingSchool(@Valid @RequestBody ReasonForNotAttendingRequest request, BindingResult result) {
        boolean saved;
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(messageHelper.missingFormFields(result));
        }
        saved = preEnrollmentService.saveReasonForNotAttending(request);
        if (saved)
            return ResponseEntity.ok(ResponseBase.success(request.getRequestId()));
        return ResponseEntity.ok(ResponseBase.error("Error saving reason"));
    }

//    @RequestMapping(value = "/add/shared/school/save", method = RequestMethod.POST)
    public ResponseEntity<ResponseBase> addSharedSchoolToPreEnrollment(@Valid @RequestBody SharedSchoolRequest request, BindingResult result) {
        boolean saved;
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(messageHelper.missingFormFields(result));
        }
        saved = preEnrollmentService.addSharedSchoolToPreEnrollment(request);
        if (saved)
            return ResponseEntity.ok(ResponseBase.success(request.getRequestId()));
        return ResponseEntity.ok(ResponseBase.error("Error saving reason"));
    }

    //alternate schools
    @RequestMapping(value = "/alternate/{requestId}")
    public ResponseEntity<ResponseBase> retrieveAlternatePreEnrollment(@PathVariable Long requestId) {
        AlternateSchoolPreEnrollmentBean alternatePreEnrollmentBean = null;
        //todo: validate user has access to this enrollment
        if (ValidationUtils.valid(requestId))
            alternatePreEnrollmentBean = preEnrollmentService.findAlternatePreEnrollmentById(requestId);
        return ResponseEntity.ok(ResponseBase.success(requestId, alternatePreEnrollmentBean));
    }

    @RequestMapping(value = "/alternate/partial/save", method = RequestMethod.POST)
    public ResponseEntity<ResponseBase> partialAlternatePreEnrollmentSave(@RequestBody AlternateSchoolPreEnrollmentSubmitRequest request) {
        /*
            List<SchoolValidationResponse> validationResponse = doSchoolAvailableSpaceValidation(request);
            ResponseEntity<ResponseBase> response = doValidation(validationResponse);
            if (response != null) {
                return response;
            }
        */
        boolean saved = preEnrollmentService.partialAlternatePreEnrollmentSave(request);
        if (saved)
            return ResponseEntity.ok(ResponseBase.success(request.getRequestId()));
        return ResponseEntity.ok(ResponseBase.error("Error submitting alternate pre-enrolmment"));
    }

    @RequestMapping(value = "/alternate/submit", method = RequestMethod.POST)
    public ResponseEntity<ResponseBase> alternateSchoolSubmit(@RequestBody AlternateSchoolPreEnrollmentSubmitRequest request, @AuthenticationPrincipal User loggedUser) {
        AlternateSchoolPreEnrollmentBean alternatePreEnrollment = preEnrollmentService.findAlternatePreEnrollmentById(request.getRequestId());
        if (commonService.isInvalidMinAlternateSchools(alternatePreEnrollment.getAlternateSchools())) {
            ResponseBase base = ResponseBase.error("Validaci\u00f3n", messageHelper.processMessages("enrollment.regular.validation"));
            return ResponseEntity.badRequest().body(base);
        }

//        List<SchoolValidationResponse> validationResponse = doSchoolAvailableSpaceValidation(request);
//        ResponseEntity<ResponseBase> response = doValidation(validationResponse);
//        if (response != null) {
//            return response;
//        }

        boolean saved = false;
        try {
            saved = preEnrollmentService.alternatePreEnrollmentSubmit(request);
            mailService.sendPreEnrollmentWithAlternateSchoolsSubmitEmail(loggedUser, request);
            cleanWorkingRequest(loggedUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (saved)
            return ResponseEntity.ok(ResponseBase.success(request.getRequestId()));
        return ResponseEntity.ok(ResponseBase.error("Error submitting alternate pre-enrollment"));
    }

    //vocational
    @RequestMapping(value = "/vocational/{requestId}")
    public ResponseEntity<ResponseBase> retrieveVocationalPreEnrollment(@PathVariable Long requestId) {
        VocationalPreEnrollmentBean vocationalPreEnrollment = null;
        //todo: validate user has access to this enrollment
        if (ValidationUtils.valid(requestId))
            vocationalPreEnrollment = preEnrollmentService.findVocationalPreEnrollmentById(requestId);
        return ResponseEntity.ok(ResponseBase.success(requestId, vocationalPreEnrollment));
    }

//    @RequestMapping(value = "/vocational/partial/save", method = RequestMethod.POST)
    public ResponseEntity<ResponseBase> partialVocationalSubmit(@RequestBody VocationalPreEnrollmentSubmitRequest request) {
        boolean saved = preEnrollmentService.partialVocationalPreEnrollmentSave(request);

        if (request.isValidateSchoolSelection() && !ValidationUtils.valid(request.getSchoolId())) {
            ResponseBase base = ResponseBase.error("Validaci\u00f3n", messageHelper.processMessages("enrollment.occupational.school.validation"));
            return ResponseEntity.badRequest().body(base);
        }

        if (request.isValidateProgramSchoolSelection() && (request.getPrograms() == null || request.getPrograms().isEmpty())) {
            ResponseBase base = ResponseBase.error("Validaci\u00f3n", messageHelper.processMessages("enrollment.occupational.program.validation"));
            return ResponseEntity.badRequest().body(base);
        }

        if (saved)
            return ResponseEntity.ok(ResponseBase.success(request.getRequestId()));
        return ResponseEntity.ok(ResponseBase.error(messageHelper.processMessage("enrollment.error")));
    }

    @RequestMapping(value = "/vocational/submit", method = RequestMethod.POST)
    public ResponseEntity<ResponseBase> vocationalSubmit(@RequestBody VocationalPreEnrollmentSubmitRequest request, @AuthenticationPrincipal User loggedUser) {
        VocationalPreEnrollmentBean vocational = preEnrollmentService.findVocationalPreEnrollmentById(request.getRequestId());
        if (vocational.getEnrollments() == null || vocational.getEnrollments().isEmpty()) {
            ResponseBase base = ResponseBase.error("Validaci\u00f3n", messageHelper.processMessages("enrollment.occupational.validation"));
            return ResponseEntity.badRequest().body(base);
        }

        boolean saved = false;
        try {
            saved = preEnrollmentService.vocationalPreEnrollmentSubmit(request);
            mailService.sendPreEnrollmentWithAlternateSchoolsSubmitEmail(loggedUser, request);
            cleanWorkingRequest(loggedUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (saved)
            return ResponseEntity.ok(ResponseBase.success(request.getRequestId()));
        return ResponseEntity.ok(ResponseBase.error("Error submitting vocational pre-enrollment"));
    }

    private ResponseEntity<ResponseBase> handleCreatePreEnrollment(PreEnrollmentInitialRequest initialRequest, User loggedUser) {
        PreEnrollmentBean preEnrollmentBean = preEnrollmentService.createPreEnrollment(initialRequest, loggedUser);

        ResponseBase response;
        if (preEnrollmentBean != null)
            response = ResponseBase.success(preEnrollmentBean.getId(), preEnrollmentBean);
        else
            response = ResponseBase.error(messageHelper.processMessage("general.unknown.error"));
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<ResponseBase> handleEditPreEnrollment(PreEnrollmentInitialRequest initialRequest) {
        PreEnrollmentBean preEnrollmentBean = preEnrollmentService.updatePreEnrollment(initialRequest);
        if (preEnrollmentBean != null)
            return ResponseEntity.ok(ResponseBase.success(preEnrollmentBean.getId(), preEnrollmentBean));
        return ResponseEntity.ok(ResponseBase.error("Error updating pre-enrollment"));
    }

    private ResponseEntity<ResponseBase> doValidation(List<SchoolValidationResponse> validationResponse) {
        if (validationResponse != null && !validationResponse.isEmpty()) {
            boolean atLeastOneSchoolIsFull = false;
            ResponseBase response =
                    ResponseBase.error("Validaci\u00f3n de Espacios Disponibles en las Escuelas", Lists.<String>newArrayList());
            for (SchoolValidationResponse schoolResponse : validationResponse) {
                if (schoolResponse.isFull()) {
                    atLeastOneSchoolIsFull = true;
                    String message = messageHelper.processMessage("enrollment.school.available.space.validation");
                    response.addError(String.format("%s %s", schoolResponse.toDisplayName(), message));
                }
            }
            return atLeastOneSchoolIsFull ? ResponseEntity.ok(response) : null;
        }
        return null;
    }

    private List<SchoolValidationResponse> doSchoolAvailableSpaceValidation(final AlternateSchoolPreEnrollmentSubmitRequest request) {
        List<SchoolValidationRequest> schoolsIdsToValidate = FluentIterable
                .from(request.getAlternateSchools())
                .filter(new Predicate<AlternateSchoolBean>() {
                    @Override
                    public boolean apply(AlternateSchoolBean alternateSchoolBean) {
                        return alternateSchoolBean.getPriority() == 1;
                    }
                })
                .transform(new Function<AlternateSchoolBean, SchoolValidationRequest>() {
                    @Override
                    public SchoolValidationRequest apply(AlternateSchoolBean alternateSchool) {
                        return new SchoolValidationRequest(alternateSchool.getSchoolId(), request.getNextGradeLevel());
                    }
                })
                .toList();
        return doSchoolAvailableSpaceValidation(schoolsIdsToValidate);
    }

    private List<SchoolValidationResponse> doSchoolAvailableSpaceValidation(final VocationalPreEnrollmentSubmitRequest request) {
        List<SchoolValidationRequest> schoolsIdsToValidate = FluentIterable
                .from(request.getPrograms())
                .transform(new Function<VocationalProgramSelection, SchoolValidationRequest>() {
                    @Override
                    public SchoolValidationRequest apply(VocationalProgramSelection vocationalProgram) {
                        return new SchoolValidationRequest(vocationalProgram.getSchoolId(), request.getNextGradeLevel());
                    }
                }).toList();
        return doSchoolAvailableSpaceValidation(schoolsIdsToValidate);
    }

    private List<SchoolValidationResponse> doSchoolAvailableSpaceValidation(List<SchoolValidationRequest> requests) {
        List<SchoolValidationResponse> responses = Lists.newArrayList();
        for (SchoolValidationRequest request : requests) {
            responses.add(schoolAvailableSpaceService.hasAvailableSpace(request));
        }
        return responses;
    }

    private void cleanWorkingRequest(User user) {
        user.setWorkingPreEnrollmentId(null);
    }

}