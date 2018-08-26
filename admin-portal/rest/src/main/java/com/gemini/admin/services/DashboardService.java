package com.gemini.admin.services;

import com.gemini.admin.beans.*;
import com.gemini.admin.beans.types.SchedulingAnalysisType;
import com.gemini.admin.database.dao.DashboardDao;
import com.gemini.admin.database.dao.SchedulingDao;
import com.gemini.admin.database.dao.beans.CategoryData;
import com.gemini.admin.database.dao.beans.EnrollmentProgress;
import com.gemini.admin.database.dao.beans.RHStatData;
import com.gemini.commons.database.beans.City;
import com.gemini.commons.database.beans.GradeLevel;
import com.gemini.commons.database.beans.Region;
import com.gemini.commons.database.beans.School;
import com.gemini.commons.utils.CopyUtils;
import com.gemini.commons.utils.DateUtils;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/9/18
 * Time: 10:29 AM
 */
@Service
public class DashboardService {
    @Autowired
    @Qualifier("dashboardDao")
    private DashboardDao dashboardDao;

    @Autowired
    private SchedulingDao schedulingDao;

    public List<Region> getRegions(AdminUser user) {
        return dashboardDao.getRegions(user);
    }

    public List<City> getCities(Long regionId, AdminUser user) {
        return dashboardDao.getCities(regionId, user);
    }

    public List<School> getSchools(Long regionId, String cityCode, AdminUser user) {
        return dashboardDao.getSchools(regionId, cityCode, user);
    }

    public List<GradeLevel> getGradeLevels(Long schoolId) {
        return dashboardDao.getGradeLevels(schoolId);
    }

    public EnrollmentSummary retrieveSummary(AdminUser user, CriteriaForm criteria) {
        EnrollmentSummary summary = new EnrollmentSummary();
        summary.setTotalPreEnrollments(dashboardDao.getPreEnrollmentBySIETotal(user, criteria));
        summary.setTotalConfirmed(dashboardDao.getConfirmedTotal(user, criteria));
        summary.setTotalDenied(dashboardDao.getDeniedTotal(user, criteria));
        summary.setTotalNewEntryEnrollments(dashboardDao.getNewEntryTotal(user, criteria));
        List<ProgressSummary> progressSummary = FluentIterable
                .from(dashboardDao.getProgress(user, criteria))
                .transform(new Function<EnrollmentProgress, ProgressSummary>() {
                    @Override
                    public ProgressSummary apply(EnrollmentProgress enrollmentProgress) {
                        ProgressSummary progress = CopyUtils.convert(enrollmentProgress, ProgressSummary.class);
                        progress.setDate(DateUtils.formatDate(enrollmentProgress.getActionDate()));
                        return progress;
                    }
                })
                .toList();
        List<String> labels = FluentIterable
                .from(progressSummary)
                .transform(new Function<ProgressSummary, String>() {
                    @Override
                    public String apply(ProgressSummary progressSummary) {
                        return progressSummary.getDate();
                    }
                }).toList();

        List<Integer> confirmedEnrollments = Lists.newArrayList();
        List<Integer> deniedEnrollments = Lists.newArrayList();
        List<Integer> newEnrollments = Lists.newArrayList();
        for (ProgressSummary progress : progressSummary) {
            confirmedEnrollments.add(progress.getTotalConfirmed());
            deniedEnrollments.add(progress.getTotalDenied());
            newEnrollments.add(progress.getTotalNewEntryEnrollments());
        }
        summary.setConfirmedEnrollments(confirmedEnrollments);
        summary.setDeniedEnrollments(deniedEnrollments);
        summary.setNewEntryEnrollments(newEnrollments);
        summary.setLabels(labels);

        return summary;
    }

    public SchedulingSummary retrieveSchedulingSummary(AdminUser user, SchedulingCriteriaForm criteria) {
        SchedulingSummary schedulingSummary = new SchedulingSummary();
        List<RHStatData> stat = Lists.newArrayList();
        if (criteria.getAnalysisType() != null)
            switch (criteria.getAnalysisType()) {
                case REGULAR:
                case REGULAR_AFTER_PLACEMENT:
                    stat = schedulingDao.getRegularStatAfterPlacement(criteria, user);
                    break;
                case SPECIAL_EDUCATION:
                case SPECIAL_EDUCATION_AFTER_PLACEMENT:
                    stat = schedulingDao.getEEStatPlacement(criteria, user);
                    break;
                case OCCUPATIONAL_PLACEMENT:
                    stat = schedulingDao.getOCStatPlacement(criteria, user);
                    break;
            }

        List<String> labels = Lists.newArrayList();
        List<String> labelsIds = Lists.newArrayList();

        List<Integer> asignadosTotales = Lists.newArrayList();
        List<Integer> vacantesTotales = Lists.newArrayList();
        List<Integer> excedentesTotales = Lists.newArrayList();

        for (RHStatData rhStatData : stat) {
            if (criteria.isCityOnly()) {
                labels.add(rhStatData.getCity());
                labelsIds.add(rhStatData.getCityCode());
            } else if (criteria.isSchoolOnly()) {
                labels.add(rhStatData.getSchoolName());
                labelsIds.add(rhStatData.getSchoolId().toString());
            } else {
                labels.add(rhStatData.getRegion());
                labelsIds.add(rhStatData.getRegionId().toString());
            }

            asignadosTotales.add(rhStatData.getAsignados());
            vacantesTotales.add(rhStatData.getVacantes());
            excedentesTotales.add(rhStatData.getExcedentes());
        }


        schedulingSummary.setLabels(labels);
        schedulingSummary.setLabelIds(labelsIds);
        schedulingSummary.setAsignadosTotales(asignadosTotales);
        schedulingSummary.setVacantesTotales(vacantesTotales);
        schedulingSummary.setExcedentesTotales(excedentesTotales);
        schedulingSummary.setNecesidadesTotales(calculateNecesidadesTotales(schedulingSummary));


        return schedulingSummary;
    }

    public SchedulingCategorySummary retrieveSchedulingCategorySummary(SchedulingCatCriteriaForm criteria) {
        SchedulingCategorySummary schedulingCategorySummary = new SchedulingCategorySummary();
        List<CategoryData> categoryData = null;
        //to be implemented
        switch (criteria.getSelectedType()) {
            case 0:
                if (SchedulingAnalysisType.SPECIAL_EDUCATION_AFTER_PLACEMENT.equals(criteria.getAnalysisType())) {
                    categoryData = schedulingDao.getAsignadosCategoriesEE(criteria);
                } else if (SchedulingAnalysisType.OCCUPATIONAL_PLACEMENT.equals(criteria.getAnalysisType())) {
                    categoryData = schedulingDao.getAsignadosCategoriesOC(criteria);
                } else {
                    categoryData = schedulingDao.getAsignadosCategories(criteria);
                }
                break;
            case 1:
                if (SchedulingAnalysisType.SPECIAL_EDUCATION_AFTER_PLACEMENT.equals(criteria.getAnalysisType())) {
                    categoryData = schedulingDao.getVacantesCategoriesEE(criteria);
                } else if (SchedulingAnalysisType.OCCUPATIONAL_PLACEMENT.equals(criteria.getAnalysisType())) {
                    categoryData = schedulingDao.getVacantesCategoriesOC(criteria);
                } else {
                    categoryData = schedulingDao.getVacantesCategories(criteria);
                }
                break;
            case 2:

                if (SchedulingAnalysisType.SPECIAL_EDUCATION_AFTER_PLACEMENT.equals(criteria.getAnalysisType())) {
                    categoryData = schedulingDao.getExcedentesCategoriesEE(criteria);
                } else if (SchedulingAnalysisType.OCCUPATIONAL_PLACEMENT.equals(criteria.getAnalysisType())) {
                    categoryData = schedulingDao.getExcedentesCategoriesOC(criteria);
                } else {
                    categoryData = schedulingDao.getExcedentesCategories(criteria);
                }
                break;

        }

        if (categoryData == null) {
            if (SchedulingAnalysisType.SPECIAL_EDUCATION_AFTER_PLACEMENT.equals(criteria.getAnalysisType())) {
                categoryData = schedulingDao.getAllCategoriesEE(criteria);
            } else if (SchedulingAnalysisType.OCCUPATIONAL_PLACEMENT.equals(criteria.getAnalysisType())) {
                categoryData = schedulingDao.getAllCategoriesOC(criteria);
            } else {
                categoryData = schedulingDao.getAllCategories(criteria);
            }

        }


        schedulingCategorySummary.setLabels(new ArrayList<String>());
        schedulingCategorySummary.setCategoryTotals(new ArrayList<Integer>());

        for (CategoryData category : categoryData) {
            schedulingCategorySummary.getLabels().add(category.getCategory());
            schedulingCategorySummary.getCategoryTotals().add(category.getTotal());
        }


        return schedulingCategorySummary;
    }


    public SchedulingCategorySummary retrieveCategoryVacants(SchedulingCatCriteriaForm criteria) {
        SchedulingCategorySummary schedulingCategorySummary = new SchedulingCategorySummary();
        criteria.setOrderByCount(true);
        List<CategoryData> categoryData = null;

        if (SchedulingAnalysisType.SPECIAL_EDUCATION_AFTER_PLACEMENT.equals(criteria.getAnalysisType())) {
            categoryData = schedulingDao.getVacantesCategoriesEE(criteria);
        } else if (SchedulingAnalysisType.OCCUPATIONAL_PLACEMENT.equals(criteria.getAnalysisType())) {
            categoryData = schedulingDao.getVacantesCategoriesOC(criteria);
        } else {
            categoryData = schedulingDao.getVacantesCategories(criteria);
        }


        schedulingCategorySummary.setLabels(new ArrayList<String>());
        schedulingCategorySummary.setCategoryTotals(new ArrayList<Integer>());

        for (CategoryData category : categoryData) {
            schedulingCategorySummary.getLabels().add(category.getCategory());
            schedulingCategorySummary.getCategoryTotals().add(category.getTotal());
        }


        return schedulingCategorySummary;
    }


    private List<Integer> calculateNecesidadesTotales(SchedulingSummary summary) {
        List<Integer> necesidadesTotales = new ArrayList<>();
        for (int i = 0; i < summary.getAsignadosTotales().size(); i++) {
            Integer asignados = summary.getAsignadosTotales().get(i);
            Integer vacantes = summary.getVacantesTotales().get(i);

            necesidadesTotales.add(asignados + vacantes);

        }
        return necesidadesTotales;
    }


}