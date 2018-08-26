package com.gemini.admin.controllers;

import com.gemini.admin.beans.AdminUser;
import com.gemini.admin.beans.SchedulingCriteriaForm;
import com.gemini.admin.beans.report.SchedulingReport;
import com.gemini.admin.beans.report.StudentReport;
import com.gemini.admin.beans.types.ReportType;
import com.gemini.admin.services.ReportService;
import com.gemini.commons.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/17/18
 * Time: 12:59 PM
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/{reportType}/school/{schoolId}/students")
    public ResponseEntity<List<StudentReport>> retrieveReportData(@PathVariable("reportType") String reportType, @PathVariable("schoolId") Long schoolId, @AuthenticationPrincipal AdminUser user) {

        if (!StringUtils.hasText(reportType) || !ValidationUtils.valid(schoolId)) {
            return ResponseEntity.badRequest().build();
        }

        ReportType type = ReportType.valueOf(reportType);
//        CriteriaForm criteria = new CriteriaForm();
        List<StudentReport> data = reportService.retrieveReportData(type, schoolId);
        return ResponseEntity.ok(data);
    }

    @RequestMapping(value = "/scheduling/asignados", method = RequestMethod.POST)
    public ResponseEntity<List<SchedulingReport>> retrieveAsignados(@AuthenticationPrincipal AdminUser user, @RequestBody SchedulingCriteriaForm criteria) {
        return ResponseEntity.ok(reportService.retrieveAsignados(user, criteria));
    }

    @RequestMapping(value = "/scheduling/vacantes", method = RequestMethod.POST)
    public ResponseEntity<List<SchedulingReport>> retrieveVacantes(@AuthenticationPrincipal AdminUser user, @RequestBody SchedulingCriteriaForm criteria) {
        return ResponseEntity.ok(reportService.retrieveVacantes(user, criteria));
    }

    @RequestMapping(value = "/scheduling/excedentes", method = RequestMethod.POST)
    public ResponseEntity<List<SchedulingReport>> retrieveExcedentes(@AuthenticationPrincipal AdminUser user, @RequestBody SchedulingCriteriaForm criteria) {
        return ResponseEntity.ok(reportService.retrieveExcedentes(user, criteria));
    }

}