package com.gemini.admin.controllers;

import com.gemini.admin.beans.*;
import com.gemini.admin.services.DashboardService;
import com.gemini.commons.beans.integration.RegionResponse;
import com.gemini.commons.beans.integration.SchoolResponse;
import com.gemini.commons.database.beans.City;
import com.gemini.commons.database.beans.GradeLevel;
import com.gemini.commons.database.beans.Region;
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
 * Date: 3/17/18
 * Time: 3:17 PM
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;


    @RequestMapping(value = "/retrieve/regions")
    public ResponseEntity<List<RegionResponse>> getAllRegions(@AuthenticationPrincipal AdminUser user) {
        List<Region> regions = dashboardService.getRegions(user);
        return ResponseEntity.ok(CopyUtils.convert(regions, RegionResponse.class));
    }

    @RequestMapping(value = "/retrieve/cities/region/{regionId}")
    public ResponseEntity<List<City>> getCities(@PathVariable Long regionId, @AuthenticationPrincipal AdminUser user) {
        List<City> cities = dashboardService.getCities(regionId, user);
        return ResponseEntity.ok(cities);
    }

    @RequestMapping(value = "/retrieve/schools/region/{regionId}/city/{cityCode}")
    public ResponseEntity<List<SchoolResponse>> getSchools(@PathVariable Long regionId, @PathVariable String cityCode, @AuthenticationPrincipal AdminUser user) {
        List<School> schools = dashboardService.getSchools(regionId, cityCode, user);
        return ResponseEntity.ok(CopyUtils.convert(schools, SchoolResponse.class));
    }

    @RequestMapping(value = "/retrieve/grade/levels/{schoolId}")
    public ResponseEntity<List<GradeLevel>> getGradeLevels(@PathVariable Long schoolId) {
        List<GradeLevel> gradeLevels = dashboardService.getGradeLevels(schoolId);
        return ResponseEntity.ok(CopyUtils.convert(gradeLevels, GradeLevel.class));
    }


    @RequestMapping(value = "/retrieve/summary", method = RequestMethod.POST)
    public ResponseEntity<EnrollmentSummary> getSummary(@RequestBody CriteriaForm criteria, @AuthenticationPrincipal AdminUser user) {
        EnrollmentSummary enrollmentSummary = dashboardService.retrieveSummary(user, criteria);
        return ResponseEntity.ok(enrollmentSummary);
    }

    @RequestMapping(value = "/scheduling/retrieve/summary", method = RequestMethod.POST)
    public ResponseEntity<SchedulingSummary> getSchedulingSummary(@RequestBody SchedulingCriteriaForm criteria, @AuthenticationPrincipal AdminUser user) {
        SchedulingSummary schedulingSummary = dashboardService.retrieveSchedulingSummary(user, criteria);
        return ResponseEntity.ok(schedulingSummary);
    }

    @RequestMapping(value = "/scheduling/retrieve/categories", method = RequestMethod.POST)
    public ResponseEntity<SchedulingCategorySummary> getSchedulingCategorySummary(@RequestBody SchedulingCatCriteriaForm criteria, @AuthenticationPrincipal AdminUser user) {
        SchedulingCategorySummary schedulingSummary = dashboardService.retrieveSchedulingCategorySummary(criteria);
        return ResponseEntity.ok(schedulingSummary);
    }

    @RequestMapping(value = "/scheduling/retrieve/vacants/categories", method = RequestMethod.POST)
    public ResponseEntity<SchedulingCategorySummary> getSchedulingVacantCategories(@RequestBody SchedulingCatCriteriaForm criteria, @AuthenticationPrincipal AdminUser user) {
        SchedulingCategorySummary schedulingSummary = dashboardService.retrieveCategoryVacants(criteria);
        return ResponseEntity.ok(schedulingSummary);
    }

}