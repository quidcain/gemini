package com.gemini.admin.database.dao;

import com.gemini.admin.beans.AdminUser;
import com.gemini.admin.beans.SchedulingCriteriaForm;
import com.gemini.admin.beans.report.SchedulingReport;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 6/19/18
 * Time: 5:34 PM
 */
public interface SchedulingReportDao {

    //regular
    List<SchedulingReport> getAsignadosRegular(SchedulingCriteriaForm criteria, AdminUser adminUser);

    List<SchedulingReport> getVacantesRegular(SchedulingCriteriaForm criteria, AdminUser adminUser);

    List<SchedulingReport> getExcedentesRegular(SchedulingCriteriaForm criteria, AdminUser adminUser);

    //Educacion Especial
    List<SchedulingReport> getAsignadosEE(SchedulingCriteriaForm criteria, AdminUser adminUser);

    List<SchedulingReport> getVacantesEE(SchedulingCriteriaForm criteria, AdminUser adminUser);

    List<SchedulingReport> getExcedentesEE(SchedulingCriteriaForm criteria, AdminUser adminUser);

    //ocupacional
    List<SchedulingReport> getAsignadosOC(SchedulingCriteriaForm criteria, AdminUser adminUser);

    List<SchedulingReport> getVacantesOC(SchedulingCriteriaForm criteria, AdminUser adminUser);

    List<SchedulingReport> getExcedentesOC(SchedulingCriteriaForm criteria, AdminUser adminUser);



}