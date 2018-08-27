package com.gemini.admin.database.dao;

import com.gemini.admin.beans.AdminUser;
import com.gemini.admin.beans.Criteria;
import com.gemini.admin.database.dao.beans.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 8/3/18
 * Time: 3:44 PM
 */
public interface StaffReportDao {

    List<StaffSummaryRow> getSummaryByRegion(AdminUser user, Criteria criteria);

    List<StaffSummaryRow> getSummaryByCity(AdminUser user, Criteria criteria);

    List<StaffSummaryRow> getSummaryBySchool(AdminUser user, Criteria criteria);

    List<EnrollmentStaffingRow> getEnrollmentAndStaffSummary(AdminUser user, Criteria criteria);

    List<VacantSummaryRow> getVacantSummary(AdminUser user, Criteria criteria);

    List<DetailedRosterRow> getDetailedRoster(AdminUser user, Criteria criteria);

    List<ClassGroupWithoutTeacherRow> getClassGroupWithoutTeacher(AdminUser user, Criteria criteria);

}