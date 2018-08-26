package com.gemini.admin.database.dao;

import com.gemini.admin.beans.AdminUser;
import com.gemini.admin.beans.SchedulingCatCriteriaForm;
import com.gemini.admin.beans.SchedulingCriteriaForm;
import com.gemini.admin.database.dao.beans.CategoryData;
import com.gemini.admin.database.dao.beans.RHStatData;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 6/11/18
 * Time: 1:49 PM
 */
public interface SchedulingDao {

    List<RHStatData> getRegularStatAfterPlacement(SchedulingCriteriaForm schedulingCriteriaForm, AdminUser adminUser);

    List<RHStatData> getEEStatPlacement(SchedulingCriteriaForm schedulingCriteriaForm, AdminUser adminUser);

    List<RHStatData> getOCStatPlacement(SchedulingCriteriaForm schedulingCriteriaForm, AdminUser adminUser);

    //regular
    List<CategoryData> getAllCategories(SchedulingCatCriteriaForm criteria);

    List<CategoryData> getAsignadosCategories(SchedulingCatCriteriaForm criteria);

    List<CategoryData> getVacantesCategories(SchedulingCatCriteriaForm criteria);

    List<CategoryData> getExcedentesCategories(SchedulingCatCriteriaForm criteria);

    // educacion especial
    List<CategoryData> getAllCategoriesEE(SchedulingCatCriteriaForm criteria);

    List<CategoryData> getAsignadosCategoriesEE(SchedulingCatCriteriaForm criteria);

    List<CategoryData> getVacantesCategoriesEE(SchedulingCatCriteriaForm criteria);

    List<CategoryData> getExcedentesCategoriesEE(SchedulingCatCriteriaForm criteria);

    // ocupacional
    List<CategoryData> getAllCategoriesOC(SchedulingCatCriteriaForm criteria);

    List<CategoryData> getAsignadosCategoriesOC(SchedulingCatCriteriaForm criteria);

    List<CategoryData> getVacantesCategoriesOC(SchedulingCatCriteriaForm criteria);

    List<CategoryData> getExcedentesCategoriesOC(SchedulingCatCriteriaForm criteria);

}