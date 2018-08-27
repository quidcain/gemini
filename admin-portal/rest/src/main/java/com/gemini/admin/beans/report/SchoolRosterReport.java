package com.gemini.admin.beans.report;

import com.gemini.admin.database.dao.beans.SchoolEnrollmentSummaryRow;
import com.gemini.admin.database.dao.beans.SchoolStudentEnrolledRow;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 8/15/18
 * Time: 11:38 AM
 */
public class SchoolRosterReport {
    private List<SchoolEnrollmentSummaryRow> schoolEnrollmentSummaryRows;
    private List<SchoolStudentEnrolledRow> studentEnrolledRows;

    public SchoolRosterReport() {
    }

    public SchoolRosterReport(List<SchoolEnrollmentSummaryRow> schoolEnrollmentSummaryRows, List<SchoolStudentEnrolledRow> studentEnrolledRows) {
        super();
        this.schoolEnrollmentSummaryRows = schoolEnrollmentSummaryRows;
        this.studentEnrolledRows = studentEnrolledRows;
    }

    public List<SchoolEnrollmentSummaryRow> getSchoolEnrollmentSummaryRows() {
        return schoolEnrollmentSummaryRows;
    }

    public void setSchoolEnrollmentSummaryRows(List<SchoolEnrollmentSummaryRow> schoolEnrollmentSummaryRows) {
        this.schoolEnrollmentSummaryRows = schoolEnrollmentSummaryRows;
    }

    public List<SchoolStudentEnrolledRow> getStudentEnrolledRows() {
        return studentEnrolledRows;
    }

    public void setStudentEnrolledRows(List<SchoolStudentEnrolledRow> studentEnrolledRows) {
        this.studentEnrolledRows = studentEnrolledRows;
    }
}