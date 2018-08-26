package com.gemini.admin.database.dao;

import com.gemini.admin.beans.AdminUser;
import com.gemini.admin.beans.requests.ApprovalInputRequest;
import com.gemini.admin.beans.requests.StudentMontessoriInfoRequest;
import com.gemini.admin.database.dao.beans.SRSStudent;
import com.gemini.commons.beans.forms.VocationalProgramSelection;
import com.gemini.commons.database.beans.School;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/31/18
 * Time: 9:36 PM
 */
public interface ApprovalDao {

    List<School> getSchools(AdminUser user);

    List<SRSStudent> getStudentsBySchoolAndGradeLevel(Long schoolId, String gradeLevel);

    SRSStudent getStudentByPreEnrollmentId(Long preEnrollmentId);

    boolean updateRequest(ApprovalInputRequest inputRequest, Long userId);

    boolean saveStudentMontessoriInfo(StudentMontessoriInfoRequest request);

    //occupational
    List<VocationalProgramSelection> retrieveProgramsSelectedByPreEnrollmentAndSchool(Long preEnrollmentId, Long schoolId);
}