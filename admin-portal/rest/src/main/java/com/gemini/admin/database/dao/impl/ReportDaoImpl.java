package com.gemini.admin.database.dao.impl;

import com.gemini.admin.beans.CriteriaForm;
import com.gemini.admin.database.dao.ReportDao;
import com.gemini.admin.database.dao.beans.EnrolledStudent;
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
 * Date: 4/19/18
 * Time: 9:28 AM
 */
@Repository
public class ReportDaoImpl extends NamedParameterJdbcDaoSupport implements ReportDao {

    final private String BASE_SQL = "SELECT DISTINCT \n" +
            "S.EXT_STUDENT_NUMBER\n" +
            ",S.FIRST_NAME \n" +
            ",S.MIDDLE_NAME \n" +
            ",S.LAST_NAME \n" +
            ",U.EMAIL\n" +
            ",FLOOR(MONTHS_BETWEEN(SYSDATE, S.DATE_OF_BIRTH)/12) AS AGE\n" +
            ", SC.REGION_NAME AS REGION \n" +
            ", SC.CITY \n" +
            ", SC.SCHOOL_NAME AS SCHOOL \n" +
            ", P.GRADE_LEVEL\n" +
            ", S.EXT_SCHOOL_NUMBER AS ORIGIN_SCHOOL_NUMBER\n" +
            ", S.SCHOOL_NAME AS ORIGIN_SCHOOL_NAME\n" +
            "FROM PRE_ENROLLMENT_REQUESTS P\n" +
            "INNER JOIN STUDENTS S ON P.STUDENT_ID = S.ID\n" +
            "LEFT JOIN TMP_ST_ENROLLMENT_2019 TM ON TM.STUDENT_ID = S.SIS_STUDENT_ID\n" +
            "LEFT JOIN ST_ENROLLMENT E ON E.ENROLLMENT_ID = TM.ENROLLMENT_ID\n" +
            "LEFT JOIN SY_SCHOOL S ON E.SCHOOL_ID = S.SCHOOL_ID\n" +
            "INNER JOIN USERS_REQUESTS UR ON UR.REQUESTS_ID = P.ID\n" +
            "INNER JOIN USERS U ON U.ID = UR.USER_ENTITY_ID ";

    final private String PENDING_SQL = "SELECT \n" +
            "S.EXT_STUDENT_NUMBER\n" +
            ",S.FIRST_NAME \n" +
            ",S.MIDDLE_NAME \n" +
            ",S.LAST_NAME \n" +
            ",FLOOR(MONTHS_BETWEEN(SYSDATE, S.DATE_OF_BIRTH)/12) AS AGE\n" +
            ", SC.REGION_NAME AS REGION \n" +
            ", SC.CITY \n" +
            ", SC.SCHOOL_NAME AS SCHOOL \n" +
            ", P.GRADE_LEVEL\n" +
            ", S.EXT_SCHOOL_NUMBER AS ORIGIN_SCHOOL_NUMBER\n" +
            ", S.SCHOOL_NAME AS ORIGIN_SCHOOL_NAME\n" +
            "FROM VW_SIE_NYE_STUDENT_ENROLLMENT P\n" +
            "LEFT JOIN ST_ENROLLMENT E ON E.ENROLLMENT_ID = P.ENROLLMENT_ID\n" +
            "LEFT JOIN SY_SCHOOL S ON E.SCHOOL_ID = S.SCHOOL_ID\n" +
            "INNER JOIN CE_FAMILY_MEMBER S ON P.STUDENT_ID = S.STUDENT_ID\n" +
            "INNER JOIN VW_SCHOOLS SC ON SC.SCHOOL_ID = P.SCHOOL_ID ";

    final private String VOCATIONAL_SQL = "SELECT \n" +
            "S.EXT_STUDENT_NUMBER\n" +
            ",S.FIRST_NAME \n" +
            ",S.MIDDLE_NAME \n" +
            ",S.LAST_NAME \n" +
            ",U.EMAIL\n" +
            ",FLOOR(MONTHS_BETWEEN(SYSDATE, S.DATE_OF_BIRTH)/12) AS AGE\n" +
            ", SC.REGION_NAME AS REGION \n" +
            ", SC.CITY \n" +
            ", SC.SCHOOL_NAME AS SCHOOL \n" +
            ",P.GRADE_LEVEL\n" +
            ",PV.PROGRAM_DESCRIPTION AS PROGRAM\n" +
            ", S.EXT_SCHOOL_NUMBER AS ORIGIN_SCHOOL_NUMBER\n" +
            ", S.SCHOOL_NAME AS ORIGIN_SCHOOL_NAME\n" +
            "FROM PRE_ENROLLMENT_REQUESTS P\n" +
            "INNER JOIN PRE_ENROLLMENT_VOC_SCHOOLS PV ON P.ID = PV.PRE_ENROLLMENT_ID\n" +
            "INNER JOIN STUDENTS S ON P.STUDENT_ID = S.ID\n" +
            "LEFT JOIN TMP_ST_ENROLLMENT_2019 TM ON TM.STUDENT_ID = S.SIS_STUDENT_ID\n" +
            "LEFT JOIN ST_ENROLLMENT E ON E.ENROLLMENT_ID = TM.ENROLLMENT_ID\n" +
            "LEFT JOIN SY_SCHOOL S ON E.SCHOOL_ID = S.SCHOOL_ID\n" +
            "INNER JOIN USERS_REQUESTS UR ON UR.REQUESTS_ID = P.ID\n" +
            "INNER JOIN USERS U ON U.ID = UR.USER_ENTITY_ID \n" +
            "INNER JOIN VW_SCHOOLS SC ON SC.SCHOOL_ID = PV.SCHOOL_ID ";

    final private String TRANSPORTATION_REQUESTED_SQL = "SELECT DISTINCT \n" +
            "S.EXT_STUDENT_NUMBER\n" +
            ",S.FIRST_NAME \n" +
            ",S.MIDDLE_NAME \n" +
            ",S.LAST_NAME \n" +
            ",U.EMAIL\n" +
            ",FLOOR(MONTHS_BETWEEN(SYSDATE, S.DATE_OF_BIRTH)/12) AS AGE\n" +
            ", SC.REGION_NAME AS REGION\n" +
            ", SC.CITY \n" +
            ", SC.SCHOOL_NAME AS SCHOOL \n" +
            ", P.GRADE_LEVEL\n" +
            ", S.EXT_SCHOOL_NUMBER AS ORIGIN_SCHOOL_NUMBER\n" +
            ", S.SCHOOL_NAME AS ORIGIN_SCHOOL_NAME\n" +
            ", A.LINE1 \n" +
            ", A.LINE2 \n" +
            ", em.county AS ADDRESS_CITY\n" +
            ", A.ZIPCODE \n" +
            "FROM PRE_ENROLLMENT_REQUESTS P\n" +
            "INNER JOIN STUDENTS S ON P.STUDENT_ID = S.ID\n" +
            "INNER JOIN addresses A on A.id = S.PHYSICAL_ADDRESS_ID\n" +
            "INNER JOIN prde_srs.enum_map_county_zipcode em ON em.zipcode = A.zipcode\n" +
            "LEFT JOIN TMP_ST_ENROLLMENT_2019 TM ON TM.STUDENT_ID = S.SIS_STUDENT_ID\n" +
            "LEFT JOIN ST_ENROLLMENT E ON E.ENROLLMENT_ID = TM.ENROLLMENT_ID\n" +
            "LEFT JOIN SY_SCHOOL S ON E.SCHOOL_ID = S.SCHOOL_ID\n" +
            "INNER JOIN USERS_REQUESTS UR ON UR.REQUESTS_ID = P.ID\n" +
            "INNER JOIN USERS U ON U.ID = UR.USER_ENTITY_ID\n" +
            "INNER JOIN VW_SCHOOLS SC ON SC.SCHOOL_ID = COALESCE(P.RESULT_SCHOOL_ID, P.SCHOOL_ID)\n" +
            "WHERE S.TRANSPORTATION_REQUESTED = 1 and S.IS_ACTIVE_IND = 1 AND P.IS_ACTIVE_IND = 1 ";

    final private String ORDER_BY = " ORDER BY P.GRADE_LEVEL, S.FIRST_NAME, S.LAST_NAME ";

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void init() {
        setDataSource(dataSource);
    }


    @Override
    public List<EnrolledStudent> getConfirmedBySchoolId(Long schoolId) {
        String sql = BASE_SQL
                .concat(" INNER JOIN VW_SCHOOLS SC ON SC.SCHOOL_ID = COALESCE(P.RESULT_SCHOOL_ID, P.SCHOOL_ID) ")
                .concat(" WHERE P.TYPE = 'REGULAR' AND COALESCE(P.RESULT_SCHOOL_ID, P.SCHOOL_ID) = ? AND SUBMIT_DATE IS NOT NULL ")
                .concat(ORDER_BY);
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(EnrolledStudent.class), schoolId);
    }

    @Override
    public List<EnrolledStudent> getDeniedBySchoolId(Long schoolId) {
        String sql = BASE_SQL
                .concat(" INNER JOIN VW_SCHOOLS SC ON SC.SCHOOL_ID = P.SCHOOL_ID ")
                .concat(" WHERE P.TYPE = 'REGULAR_ALTERNATE_SCHOOLS' AND P.PRE_ENROLLMENT_ID IS NOT NULL AND SUBMIT_DATE IS NOT NULL AND P.SCHOOL_ID = ? AND P.RESULT_SCHOOL_ID IS NULL ")
                .concat(ORDER_BY);
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(EnrolledStudent.class), schoolId);
    }

    @Override
    public List<EnrolledStudent> getAlternateEnrollmentsBySchoolId(Long schoolId) {
        String sql = BASE_SQL
                .concat(" INNER JOIN PRE_ENROLLMENT_ALT_SCHOOLS PA ON P.ID = PA.PRE_ENROLLMENT_ID ")
                .concat(" INNER JOIN VW_SCHOOLS SC ON SC.SCHOOL_ID = PA.SCHOOL_ID ")
                .concat(" WHERE P.TYPE = 'REGULAR_ALTERNATE_SCHOOLS' AND  P.PRE_ENROLLMENT_ID IS NOT NULL AND PA.SCHOOL_ID = ? AND SUBMIT_DATE IS NOT NULL AND S.ENTRY_TYPE = 'EXISTING' AND P.RESULT_SCHOOL_ID IS NULL  ")
                .concat(ORDER_BY);
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(EnrolledStudent.class), schoolId);
    }

    @Override
    public List<EnrolledStudent> getPendingEnrollmentsBySchoolId(Long schoolId) {
        String sql = PENDING_SQL.concat(" WHERE P.SCHOOL_ID = ? AND S.STUDENT_ID NOT IN " +
                "(SELECT SI.SIS_STUDENT_ID FROM PRE_ENROLLMENT_REQUESTS PI INNER JOIN STUDENTS SI ON PI.STUDENT_ID = SI.ID WHERE SI.ENTRY_TYPE = 'EXISTING' AND PI.SCHOOL_ID = ? AND SUBMIT_DATE IS NOT NULL)")
                .concat(ORDER_BY);
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(EnrolledStudent.class), schoolId, schoolId);
    }

    @Override
    public List<EnrolledStudent> getNewEnrollmentsBySchoolId(Long schoolId) {
        String sql = BASE_SQL
                .concat(" INNER JOIN PRE_ENROLLMENT_ALT_SCHOOLS PA ON P.ID = PA.PRE_ENROLLMENT_ID ")
                .concat(" INNER JOIN VW_SCHOOLS SC ON SC.SCHOOL_ID = PA.SCHOOL_ID ")
                .concat(" WHERE P.TYPE = 'REGULAR_ALTERNATE_SCHOOLS' AND P.PRE_ENROLLMENT_ID IS NULL AND SUBMIT_DATE IS NOT NULL AND PA.SCHOOL_ID = ? AND P.RESULT_SCHOOL_ID IS NULL ")
                .concat(ORDER_BY);
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(EnrolledStudent.class), schoolId);
    }

    @Override
    public List<EnrolledStudent> getTransportationRequestedStudents(Long schoolId) {
        String sql = TRANSPORTATION_REQUESTED_SQL
                .concat(" and COALESCE(P.RESULT_SCHOOL_ID, P.SCHOOL_ID) = ? ")
                .concat(ORDER_BY);
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(EnrolledStudent.class), schoolId);
    }

    @Override
    public List<EnrolledStudent> getIncompleteEnrollments(CriteriaForm criteria) {
        return null;
    }

    @Override
    public List<EnrolledStudent> getVocationalEnrollmentsBySchoolId(Long schoolId) {
        String sql = VOCATIONAL_SQL.concat(" WHERE P.TYPE = 'OCCUPATIONAL' AND PV.SCHOOL_ID = ? ")
                .concat(ORDER_BY);
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(EnrolledStudent.class), schoolId);
    }
}