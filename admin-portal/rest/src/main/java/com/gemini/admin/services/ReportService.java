package com.gemini.admin.services;

import com.gemini.admin.beans.AdminUser;
import com.gemini.admin.beans.SchedulingCriteriaForm;
import com.gemini.admin.beans.report.SchedulingReport;
import com.gemini.admin.beans.report.StudentReport;
import com.gemini.admin.beans.types.ReportType;
import com.gemini.admin.database.dao.ReportDao;
import com.gemini.admin.database.dao.SchedulingReportDao;
import com.gemini.admin.database.dao.beans.EnrolledStudent;
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


}