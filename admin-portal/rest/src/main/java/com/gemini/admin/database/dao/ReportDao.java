package com.gemini.admin.database.dao;

import com.gemini.admin.beans.CriteriaForm;
import com.gemini.admin.database.dao.beans.EnrolledStudent;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/17/18
 * Time: 3:19 PM
 */
public interface ReportDao {

    List<EnrolledStudent> getConfirmedBySchoolId(Long schoolId);

    List<EnrolledStudent> getDeniedBySchoolId(Long schoolId);

    List<EnrolledStudent> getAlternateEnrollmentsBySchoolId(Long schoolId);

    List<EnrolledStudent> getPendingEnrollmentsBySchoolId(Long schoolId);

    List<EnrolledStudent> getNewEnrollmentsBySchoolId(Long schoolId);

    List<EnrolledStudent> getTransportationRequestedStudents(Long schoolId);

    List<EnrolledStudent> getIncompleteEnrollments(CriteriaForm criteria);

    List<EnrolledStudent> getVocationalEnrollmentsBySchoolId(Long schoolId);

}