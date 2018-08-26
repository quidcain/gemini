package com.gemini.admin.database.dao;

import com.gemini.admin.beans.AdminUser;
import com.gemini.admin.beans.CriteriaForm;
import com.gemini.admin.database.dao.beans.EnrollmentProgress;
import com.gemini.commons.database.beans.City;
import com.gemini.commons.database.beans.GradeLevel;
import com.gemini.commons.database.beans.Region;
import com.gemini.commons.database.beans.School;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/9/18
 * Time: 3:51 PM
 */
public interface DashboardDao {
    //  regions, municipios & schools

    List<Region> getRegions(AdminUser user);

    List<City> getCities(Long regionId, AdminUser user);

    List<School> getSchools(Long regionId, String cityCode, AdminUser user);

    List<GradeLevel> getGradeLevels(Long schoolId);

    int getPreEnrollmentBySIETotal(AdminUser user, CriteriaForm criteria);

    int getConfirmedTotal(AdminUser user, CriteriaForm criteria);

    int getNewEntryTotal(AdminUser user, CriteriaForm criteria);

    int getDeniedTotal(AdminUser user, CriteriaForm criteria);

    List<EnrollmentProgress> getProgress(AdminUser user, CriteriaForm criteria);
}