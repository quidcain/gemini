package com.gemini.controllers;

import com.gemini.commons.beans.forms.EnumCode;
import com.gemini.beans.forms.User;
import com.gemini.commons.beans.forms.VocationalProgramSelection;
import com.gemini.commons.beans.integration.ParentResponse;
import com.gemini.commons.beans.integration.RegionResponse;
import com.gemini.commons.beans.integration.SchoolResponse;
import com.gemini.commons.beans.integration.StudentResponse;
import com.gemini.commons.beans.requests.StudentSearchRequest;
import com.gemini.commons.beans.types.SchoolCategory;
import com.gemini.commons.beans.types.SpecializedSchoolCategory;
import com.gemini.commons.database.beans.*;
import com.gemini.services.SchoolMaxSearchService;
import com.gemini.services.SchoolmaxService;
import com.gemini.commons.utils.CopyUtils;
import com.gemini.utils.MessageHelper;
import com.gemini.commons.utils.Utils;
import com.gemini.commons.utils.ValidationUtils;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/9/18
 * Time: 12:22 AM
 */
@RestController
@RequestMapping("/smax/interface")
public class SchoolmaxController {

    @Autowired
    private SchoolmaxService smaxService;
    @Autowired
    private SchoolMaxSearchService smaxSearchService;
    @Autowired
    private MessageHelper messageHelper;

    @RequestMapping(value = "/search/parent/lastssn/{lastSSN}/dob/{dob}/lastname/{lastname}")
    public ResponseEntity<ParentResponse> searchParent(@PathVariable(value = "lastSSN") String lastSSN,
                                                       @PathVariable(value = "dob") @DateTimeFormat(pattern = "yyyyMMdd") String dob,
                                                       @PathVariable(value = "lastname") String lastname) {
        Date dateOfBirth = ValidationUtils.validDate(dob);
        if (!(StringUtils.hasLength(lastname)
                && StringUtils.hasLength(lastSSN) && lastSSN.matches("[0-9]{4}")
                && dateOfBirth != null))
            return ResponseEntity.badRequest().body(null);


        Parent parentBean = smaxService.retrieveHouseHeadInfo(lastSSN, dateOfBirth, lastname);
        return ResponseEntity.ok().body(CopyUtils.convert(parentBean, ParentResponse.class));
    }

    @RequestMapping(value = "/student/search")
    public ResponseEntity<StudentResponse> searchStudent(@Valid @RequestBody StudentSearchRequest searchRequest, BindingResult result, @AuthenticationPrincipal User logged) {
        StudentResponse response = new StudentResponse();
        if (result.hasErrors()) {
            response = messageHelper.missingFieldsOnStudentSearch(result);
        }

        if (!(ValidationUtils.valid(searchRequest.getSsn()) || ValidationUtils.valid(searchRequest.getStudentNumber())))
            response.addError(messageHelper.processMessage("search.student.missing.required.fields"));

        if (response != null && response.hasError()) {
            response.setErrorOperation(true);
            return ResponseEntity.badRequest().body(response);
        }

        Student studentBean = smaxSearchService.retrieveStudentInfo(searchRequest, logged);
        response = CopyUtils.convert(studentBean, StudentResponse.class);
        response.setFound(studentBean != null);
        if (studentBean != null) {
            response.setStudentNumber(studentBean.getExtStudentNumber());
            response.setLastSsnFormatted(Utils.obfuscatedSsn(studentBean.getSsn()));
        }
        return ResponseEntity.ok().body(response);

    }

    @RequestMapping(value = "/retrieve/regions")
    public ResponseEntity<List<RegionResponse>> getAllRegions() {
        List<Region> regions = smaxService.getAllRegions();
        return ResponseEntity.ok(CopyUtils.convert(regions, RegionResponse.class));
    }


    @RequestMapping(value = "/retrieve/occupational/regions")
    public ResponseEntity<List<RegionResponse>> getOccupationalRegions() {
        List<Region> regions = smaxService.getAllRegions();
        return ResponseEntity.ok(CopyUtils.convert(regions, RegionResponse.class));
    }

    @RequestMapping(value = "/retrieve/grade/levels/school/category")
    public ResponseEntity<List<GradeLevel>> getGradeLevels(@RequestParam(name = "category", required = false) SchoolCategory category) {
        List<GradeLevel> levels = smaxService.getAllGradeLevels(category);
        return ResponseEntity.ok(levels);
    }

    @RequestMapping(value = "/retrieve/specialized/school/categories")
    public ResponseEntity<List<EnumCode>> getSpecializedSchoolCategories() {
        List<EnumCode> enumCodes = FluentIterable
                .from(SpecializedSchoolCategory.values())
                .transform(new Function<SpecializedSchoolCategory, EnumCode>() {
                    @Override
                    public EnumCode apply(SpecializedSchoolCategory category) {
                        return new EnumCode(category.name(), category.getDescription());
                    }
                }).toList();

        return ResponseEntity.ok(enumCodes);
    }

    //schools
    @RequestMapping(value = "/retrieve/school/{regionId}/grade/level/{gradeLevel}")
    public ResponseEntity<List<SchoolResponse>> getSchoolByRegionAndGradeLevel(@PathVariable Long regionId, @PathVariable String gradeLevel) {
        List<School> schools = smaxService.findSchoolByRegionAndGradeLevel(regionId, gradeLevel);
        List<SchoolResponse> schoolReturned = new ArrayList<>();
        for (School school : schools) {
            SchoolResponse response = CopyUtils.createSchoolResponse(school);
            schoolReturned.add(response);
        }

        return ResponseEntity.ok(schoolReturned);
    }

    @RequestMapping(value = "/retrieve/specialized/school/{regionId}/grade/level/{gradeLevel}/category")
    public ResponseEntity<List<SchoolResponse>> getSpecializedSchoolByRegionAndGradeLevel(@PathVariable Long regionId
            , @PathVariable String gradeLevel
            , @RequestParam(name = "category", required = false) SpecializedSchoolCategory category) {
        List<School> schools = smaxService.findSpecializedSchoolsByRegionAndGradeLevel(regionId, gradeLevel, category);
        List<SchoolResponse> schoolReturned = new ArrayList<>();
        for (School school : schools) {
            SchoolResponse response = CopyUtils.createSchoolResponse(school);
            schoolReturned.add(response);
        }

        return ResponseEntity.ok(schoolReturned);
    }

    @RequestMapping(value = "/retrieve/occupational/school/{regionId}/grade/level/{gradeLevel}")
    public ResponseEntity<List<SchoolResponse>> getOccupationalSchoolByRegionAndGradeLevel(@PathVariable Long regionId, @PathVariable String gradeLevel) {
        List<School> schools = smaxService.findOccupationalSchoolsByRegionAndGradeLevel(regionId, gradeLevel);
        List<SchoolResponse> schoolReturned = new ArrayList<>();
        for (School school : schools) {
            SchoolResponse response = CopyUtils.createSchoolResponse(school);
            schoolReturned.add(response);
        }

        return ResponseEntity.ok(schoolReturned);
    }

    @RequestMapping(value = "/retrieve/occupational/programs/school/{schoolId}")
    public ResponseEntity<List<VocationalProgramSelection>> getOccupationalSchoolByRegionAndGradeLevel(@PathVariable Long schoolId) {
        List<VocationalProgram> programs = smaxService.getVocationalPrograms(schoolId);
        Function<VocationalProgram, VocationalProgramSelection> toSelection = new Function<VocationalProgram, VocationalProgramSelection>() {
            @Override
            public VocationalProgramSelection apply(VocationalProgram program) {
                VocationalProgramSelection selection = new VocationalProgramSelection();
                selection.setProgramCode(program.getCode());
                selection.setProgramDescription(program.getDescription());
                return selection;
            }
        };

        return ResponseEntity.ok(Lists.transform(programs, toSelection));
    }


    @RequestMapping(value = "/retrieve/technical/schools")
    public ResponseEntity<List<SchoolResponse>> getTechnicalSchools() {
        List<School> schools = smaxService.findTechnicalSchools();
        List<SchoolResponse> schoolReturned = new ArrayList<>();
        for (School school : schools) {
            SchoolResponse response = CopyUtils.createSchoolResponse(school);
            schoolReturned.add(response);
        }

        return ResponseEntity.ok(schoolReturned);
    }

    @RequestMapping(value = "/retrieve/shared/schools/grade/level/{gradeLevel}")
    public ResponseEntity<List<SchoolResponse>> getSharedSchoolsByGradeLevel(@PathVariable String gradeLevel) {
        List<School> schools = smaxService.findSharedSchoolsByGradeLevel(gradeLevel);
        List<SchoolResponse> schoolReturned = new ArrayList<>();
        for (School school : schools) {
            SchoolResponse response = CopyUtils.createSchoolResponse(school);
            schoolReturned.add(response);
        }

        return ResponseEntity.ok(schoolReturned);
    }



}