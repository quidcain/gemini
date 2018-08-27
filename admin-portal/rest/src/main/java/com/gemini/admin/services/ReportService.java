package com.gemini.admin.services;

import com.gemini.admin.beans.AdminUser;
import com.gemini.admin.beans.Criteria;
import com.gemini.admin.beans.SchedulingCriteriaForm;
import com.gemini.admin.beans.report.*;
import com.gemini.admin.beans.types.ReportType;
import com.gemini.admin.database.dao.ReportDao;
import com.gemini.admin.database.dao.SchedulingReportDao;
import com.gemini.admin.database.dao.StaffReportDao;
import com.gemini.admin.database.dao.beans.*;
import com.gemini.commons.utils.CopyUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/19/18
 * Time: 10:00 AM
 */
@Service
public class ReportService {

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private StaffReportDao staffReportDao;

    @Autowired
    private SchedulingReportDao schedulingReportDao;

    public List<StudentReport> retrieveReportData(ReportType type, Long schoolId) {
        List<EnrolledStudent> enrolledStudents = new ArrayList<>();
        switch (type) {
            case CONFIRMED:
                enrolledStudents = reportDao.getConfirmedBySchoolId(schoolId);
                break;
            case DENIED:
                enrolledStudents = reportDao.getDeniedBySchoolId(schoolId);
                break;
            case ALTERNATE_ENROLLMENT:
                enrolledStudents = reportDao.getAlternateEnrollmentsBySchoolId(schoolId);
                break;
            case PENDING_ENROLLMENT:
                enrolledStudents = reportDao.getPendingEnrollmentsBySchoolId(schoolId);
                break;
            case NEW_ENTRY_ENROLLMENT:
                enrolledStudents = reportDao.getNewEnrollmentsBySchoolId(schoolId);
                break;
            case VOCATIONAL_ENROLLMENT:
                enrolledStudents = reportDao.getVocationalEnrollmentsBySchoolId(schoolId);
                break;
            case TRANSPORTATION_REQUESTED:
                enrolledStudents = reportDao.getTransportationRequestedStudents(schoolId);
                break;
        }

        return CopyUtils.convert(enrolledStudents, StudentReport.class);
    }

    public List<SchedulingReport> retrieveAsignados(AdminUser adminUser, SchedulingCriteriaForm criteria) {
        List<SchedulingReport> data = Lists.newArrayList();
        if (criteria.getAnalysisType() != null)
            switch (criteria.getAnalysisType()) {
                case REGULAR:
                case REGULAR_AFTER_PLACEMENT:
                    data = schedulingReportDao.getAsignadosRegular(criteria, adminUser);
                    break;
                case SPECIAL_EDUCATION:
                case SPECIAL_EDUCATION_AFTER_PLACEMENT:
                    data = schedulingReportDao.getAsignadosEE(criteria, adminUser);
                    break;
                case OCCUPATIONAL_PLACEMENT:
                    data = schedulingReportDao.getAsignadosOC(criteria, adminUser);
                    break;
            }
        return data;
    }

    public List<SchedulingReport> retrieveVacantes(AdminUser adminUser, SchedulingCriteriaForm criteria) {
        List<SchedulingReport> data = Lists.newArrayList();
        if (criteria.getAnalysisType() != null)
            switch (criteria.getAnalysisType()) {
                case REGULAR:
                case REGULAR_AFTER_PLACEMENT:
                    data = schedulingReportDao.getVacantesRegular(criteria, adminUser);
                    break;
                case SPECIAL_EDUCATION:
                case SPECIAL_EDUCATION_AFTER_PLACEMENT:
                    data = schedulingReportDao.getVacantesEE(criteria, adminUser);
                    break;
                case OCCUPATIONAL_PLACEMENT:
                    data = schedulingReportDao.getVacantesOC(criteria, adminUser);
                    break;
            }
        return data;
    }

    public List<SchedulingReport> retrieveExcedentes(AdminUser adminUser, SchedulingCriteriaForm criteria) {
        List<SchedulingReport> data = Lists.newArrayList();
        if (criteria.getAnalysisType() != null)
            switch (criteria.getAnalysisType()) {
                case REGULAR:
                case REGULAR_AFTER_PLACEMENT:
                    data = schedulingReportDao.getExcedentesRegular(criteria, adminUser);
                    break;
                case SPECIAL_EDUCATION:
                case SPECIAL_EDUCATION_AFTER_PLACEMENT:
                    data = schedulingReportDao.getExcedentesEE(criteria, adminUser);
                    break;
                case OCCUPATIONAL_PLACEMENT:
                    data = schedulingReportDao.getExcedentesOC(criteria, adminUser);
                    break;
            }
        return data;
    }

    //staff and enrollment info
    public List<StaffSummary> getSummaryByRegion(AdminUser user, Criteria criteria) {
        List<StaffSummaryRow> rows = staffReportDao.getSummaryByRegion(user, criteria);
        return CopyUtils.convert(rows, StaffSummary.class);
    }

    public List<StaffSummary> getSummaryByCity(AdminUser user, Criteria criteria) {
        List<StaffSummaryRow> rows = staffReportDao.getSummaryByCity(user, criteria);
        return CopyUtils.convert(rows, StaffSummary.class);
    }

    public List<StaffSummary> getSummaryBySchool(AdminUser user, Criteria criteria) {
        List<StaffSummaryRow> rows = staffReportDao.getSummaryBySchool(user, criteria);
        return CopyUtils.convert(rows, StaffSummary.class);
    }

    public List<EnrollmentAndStaffSummary> getEnrollmentAndStaffSummary(AdminUser user, Criteria criteria) {
        List<EnrollmentStaffingRow> rows = staffReportDao.getEnrollmentAndStaffSummary(user, criteria);
        return CopyUtils.convert(rows, EnrollmentAndStaffSummary.class);
    }

    public List<VacantSummary> getVacantSummary(AdminUser user, Criteria criteria) {
        List<VacantSummaryRow> rows = staffReportDao.getVacantSummary(user, criteria);
        return CopyUtils.convert(rows, VacantSummary.class);
    }

    public List<DetailedRosterReport> getDetailedReportData(AdminUser user, Criteria criteria) {
        List<DetailedRosterRow> rows = staffReportDao.getDetailedRoster(user, criteria);
        return CopyUtils.convert(rows, DetailedRosterReport.class);
    }

    public List<ClassGroupWithoutTeacher> getClassGroupWithoutTeacher(AdminUser user, Criteria criteria) {
        List<ClassGroupWithoutTeacherRow> rows = staffReportDao.getClassGroupWithoutTeacher(user, criteria);
        return CopyUtils.convert(rows, ClassGroupWithoutTeacher.class);
    }

    public List<StudentAddressSummary> getStudentAddressSummary(AdminUser user, Criteria criteria) {
        List<StudentAddressSummaryRow> rows = reportDao.getStudentAddressSummary(user, criteria);
        return CopyUtils.convert(rows, StudentAddressSummary.class);
    }

    //enrollment monitor info
    public SchoolRosterReport getSchoolRosterData(AdminUser user, Criteria criteria) {
        List<SchoolEnrollmentSummaryRow> schoolRows = reportDao.getSchoolEnrollmentSummary(user, criteria);
        List<SchoolStudentEnrolledRow> studentRows = reportDao.getSchoolStudentEnrolledSummary(user, criteria);
        return new SchoolRosterReport(schoolRows, studentRows);

    }


}