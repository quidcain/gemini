package com.gemini.admin.database.dao.impl;

import com.gemini.admin.beans.AdminUser;
import com.gemini.admin.beans.requests.ApprovalInputRequest;
import com.gemini.admin.beans.requests.StudentMontessoriInfoRequest;
import com.gemini.admin.database.AccessFrom;
import com.gemini.admin.database.AdminAccessHelper;
import com.gemini.admin.database.dao.ApprovalDao;
import com.gemini.admin.database.dao.beans.SRSStudent;
import com.gemini.commons.beans.forms.VocationalProgramSelection;
import com.gemini.commons.database.beans.City;
import com.gemini.commons.database.beans.Region;
import com.gemini.commons.database.beans.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/31/18
 * Time: 9:36 PM
 */

@Repository()
public class ApprovalDaoImpl extends NamedParameterJdbcDaoSupport implements ApprovalDao {

    private final String SCHOOLS_SQL = "select * from VW_NEED_APPROVAL_SCHOOLS  ";

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void init() {
        setDataSource(dataSource);
    }


    public List<School> getSchools(AdminUser user) {
        String query = SCHOOLS_SQL.concat(" WHERE 1=1 ");
        query = query
                .concat(AdminAccessHelper.addCriteria(user, AccessFrom.SCHOOLS))
                .concat(" ORDER BY REMOVE_SPANISH_ACCENTS(SCHOOL_NAME) ");
        return getJdbcTemplate().query(query, new BeanPropertyRowMapper<>(School.class));
    }

    @Override
    public List<SRSStudent> getStudentsBySchoolAndGradeLevel(Long schoolId, String gradeLevel) {
        String query = "SELECT * FROM VW_NEED_APPROVAL_STUDENTS WHERE (COALESCE_SCHOOL_ID_1 = ? OR COALESCE_SCHOOL_ID_2 = ?) AND GRADE_LEVEL = ? ORDER BY REMOVE_SPANISH_ACCENTS(FIRST_NAME)";
        return getJdbcTemplate().query(query, new BeanPropertyRowMapper<>(SRSStudent.class), schoolId, schoolId, gradeLevel);
    }

    @Override
    public SRSStudent getStudentByPreEnrollmentId(Long preEnrollmentId) {
        String query = "SELECT * FROM VW_NEED_APPROVAL_STUDENTS WHERE PRE_ENROLLMENT_ID =  ?";
        List<SRSStudent> results = getJdbcTemplate().query(query, new BeanPropertyRowMapper<>(SRSStudent.class), preEnrollmentId);
        return results.isEmpty() ? null : results.get(0);
    }

    public boolean updateRequest(ApprovalInputRequest inputRequest, Long userId) {
        String query = "UPDATE PRE_ENROLLMENT_REQUESTS " +
                "SET REQUEST_STATUS = ?" +
                ", TYPE = ?" +
                ", RESULT_SCHOOL_ID = ?" +
                ", REVISION_DATE = SYSDATE" +
                ", REVISION_USER_ID = ?" +
                ", REASON_TO_DENY_ENROLLMENT = ? " +
                "WHERE ID =  ?";
        int result = getJdbcTemplate().update(query,                                                                       
                inputRequest.getRequestStatus().name(),
                inputRequest.getType().name(),
                inputRequest.getResultSchoolId(),
                userId,
                inputRequest.getReasonToDenyString(),
                inputRequest.getPreEnrollmentId());
        return result > 0;
    }

    @Override
    public boolean saveStudentMontessoriInfo(StudentMontessoriInfoRequest request) {
        String query = "update students " +
                "set IS_MONTESSORI = ? " +
                "where id = (select student_id from pre_enrollment_requests where id = ?) ";

        return getJdbcTemplate().update(query, request.getMontessori(), request.getPreEnrollmentId()) > 0;
    }

    @Override
    public List<VocationalProgramSelection> retrieveProgramsSelectedByPreEnrollmentAndSchool(Long preEnrollmentId, Long schoolId) {
        String sql = "SELECT distinct pre_enrollment_id, school_id, program_code, program_description FROM PRE_ENROLLMENT_VOC_SCHOOLS " +
                "where pre_enrollment_id= ? and school_id = ? ";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(VocationalProgramSelection.class), preEnrollmentId, schoolId);
    }
}