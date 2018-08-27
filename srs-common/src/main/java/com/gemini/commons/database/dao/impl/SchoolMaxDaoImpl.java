package com.gemini.commons.database.dao.impl;

import com.gemini.commons.beans.requests.StudentSearchRequest;
import com.gemini.commons.beans.types.SpecializedSchoolCategory;
import com.gemini.commons.database.beans.*;
import com.gemini.commons.utils.Utils;
import com.gemini.commons.utils.ValidationUtils;
import com.gemini.commons.database.dao.SchoolMaxDaoInterface;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/9/18
 * Time: 12:57 AM
 */
public class SchoolMaxDaoImpl extends NamedParameterJdbcDaoSupport implements SchoolMaxDaoInterface {

    final Logger logger = LoggerFactory.getLogger(SchoolMaxDaoImpl.class);

    @Autowired
    DataSource smaxDatasource;

    private final String PARENT_SQL = "SELECT * FROM VW_PARENT ";
    private final String STUDENT_SQL = "select * from VW_STUDENT ";
    private final String STUDENT_ADDRESS_SQL = "SELECT * FROM VW_STUDENT_ADDRESS ";
    private final String REGION_SQL = "SELECT * FROM VW_REGIONS ";
    private final String ENROLLMENT_SQL = "SELECT * FROM VW_SIE_NYE_STUDENT_ENROLLMENT ";
    private final String SIE_ENROLLMENT_SQL = "SELECT * FROM VW_SIE_ENROLLMENT ";

    private final String SCHOOL_SQL = "SELECT * FROM VW_SCHOOLS S ";
    private final String VOCATIONAL_SCHOOLS = "SELECT * FROM VW_VOCATIONAL_SCHOOLS S ";
    private final String SPECIALIZED_SCHOOLS = "SELECT * FROM VW_SPECIALIZED_SCHOOLS S ";
    private final String TECHNICAL_SCHOOLS = "SELECT * FROM VW_TECHNICAL_SCHOOLS S ";
    private final String VOCATIONAL_PROGRAMS = "SELECT * FROM VW_VOCATIONAL_PROGRAMS ";
    private final String SHARED_SCHOOLS = "SELECT * FROM VW_SHARED_SCHOOLS S ";
    private final String SCHOOL_GRADE_LEVELS = "SELECT * FROM VW_SCHOOLS_GRADE_LEVELS ";

    @PostConstruct
    private void init() {
        setDataSource(smaxDatasource);
    }

    @Override
    public Parent findHouseHead(String lastSsn, Date dob, String lastname) {
        String sql = PARENT_SQL.concat(" AND SUBSTR(SSN, -4) = ? AND DATE_OF_BIRTH = ? and LAST_NAME like '" + lastname + "%'");
        List<Parent> list = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(Parent.class), lastSsn, dob);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public boolean foundStudentBySsn(String ssn) {
        String sql = "SELECT COUNT(*) AS total FROM VW_STUDENT WHERE SSN = ?";
        int total = getJdbcTemplate().queryForObject(sql, new SingleColumnRowMapper<>(Integer.class), ssn);
        return total > 0;
    }

    @Override
    public Student findStudent(StudentSearchRequest searchRequest) {
        StringBuilder sql = new StringBuilder(STUDENT_SQL.concat(" WHERE 1=1 "));
        Map<String, Object> params = Maps.newTreeMap();

//        if (ValidationUtils.valid(searchRequest.getFirstName())) {
//
//            String firstName = Utils.removeAccents(searchRequest.getFirstName());
//            sql.append(" AND CLEAN_NAME(FIRST_NAME_CANON) like :firstName || '%'");
//            params.put("firstName", Utils.canonString(firstName));
//        }

        if (ValidationUtils.valid(searchRequest.getLastName())) {
            String lastName = Utils.removeAccents(searchRequest.getLastName());
            sql.append(" AND CLEAN_NAME(LAST_NAME_CANON) like :lastName || '%'");
            params.put("lastName", Utils.canonString(lastName));
        }

        if (ValidationUtils.valid(searchRequest.getSsn())) {
//            sql.append(" AND SUBSTR(SSN, -4) = :lastSsn");
            sql.append(" AND SSN = :ssn");
            params.put("ssn", searchRequest.getSsn());
        }

//        if (ValidationUtils.valid(searchRequest.getDateOfBirth())) {
//            Date dob = DateUtils.toDate(searchRequest.getDateOfBirth());
//            sql.append(" AND DATE_OF_BIRTH = trunc(:dob)");
//            params.put("dob", dob);
//        }

        if (ValidationUtils.valid(searchRequest.getStudentNumber())) {
            sql.append(" AND EXT_STUDENT_NUMBER = :studentNumber");
            params.put("studentNumber", searchRequest.getStudentNumber());
        }
        logger.info("params are " + searchRequest);

        logger.info("sql executed is " + sql.toString());
        List<Student> list = getNamedParameterJdbcTemplate().query(sql.toString(), params, new BeanPropertyRowMapper<>(Student.class));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Student findStudent(Long studentNumber) {
        String sql = STUDENT_SQL.concat(" WHERE EXT_STUDENT_NUMBER = ?");
        List<Student> list = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(Student.class), studentNumber);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public StudentAddress findAddress(Long studentNumber) {
        String sql = STUDENT_ADDRESS_SQL.concat(" WHERE EXT_STUDENT_NUMBER = ? ");
        List<StudentAddress> list = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(StudentAddress.class), studentNumber);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public EnrollmentInfo findRecentStudentEnrollment(Long studentId) {
        String sql = ENROLLMENT_SQL.concat(" WHERE ENROLLMENT_ID = (SELECT MAX(ENROLLMENT_ID) FROM VW_SIE_NYE_STUDENT_ENROLLMENT WHERE STUDENT_ID = ? )");
        List<EnrollmentInfo> enrollments = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(EnrollmentInfo.class), studentId);
        return enrollments.isEmpty() ? null : enrollments.get(0);
    }

    @Override
    public EnrollmentInfo findSIEStudentEnrollment(Long studentId) {
        String sql = SIE_ENROLLMENT_SQL.concat(" WHERE STUDENT_ID = ?");
        List<EnrollmentInfo> enrollments = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(EnrollmentInfo.class), studentId);
        return enrollments.isEmpty() ? null : enrollments.get(0);
    }

    @Override
    public List<School> findSchoolsByRegionAndGradeLevel(Long regionId, Long schoolYear, String gradeLevel) {
        String sql = SCHOOL_SQL.concat("WHERE REGION_ID = ? " +
                "AND EXISTS(SELECT 1 FROM VW_SCHOOLS_GRADE_LEVELS SGL " +
                "WHERE SGL.SCHOOL_ID = S.SCHOOL_ID " +
                "AND SGL.SCHOOL_YEAR = ? AND SGL.VALUE = ?) ORDER BY SCHOOL_NAME");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(School.class), regionId, schoolYear, gradeLevel);
    }

    @Override
    public List<School> findOccupationalSchoolsByRegionAndGradeLevel(Long regionId, Long schoolYear, String gradeLevel) {
        String sql = VOCATIONAL_SCHOOLS.concat(" WHERE REGION_ID = ? " +
                "AND EXISTS(SELECT 1 FROM VW_SCHOOLS_GRADE_LEVELS SGL " +
                "WHERE SGL.SCHOOL_ID = S.SCHOOL_ID " +
                "AND SGL.SCHOOL_YEAR = ? AND SGL.VALUE = ?) ORDER BY SCHOOL_NAME");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(School.class), regionId, schoolYear, gradeLevel);
    }

    @Override
    public List<School> findSpecializedSchoolsByRegionAndGradeLevel(Long regionId, Long schoolYear, String gradeLevel, SpecializedSchoolCategory category) {
        Map<String, Object> params = ImmutableMap.<String, Object>of("regionId", regionId, "schoolYear", schoolYear, "gradeLevel", gradeLevel);
        String sql = SPECIALIZED_SCHOOLS.concat(" WHERE REGION_ID = :regionId " +
                "AND EXISTS(SELECT 1 FROM VW_SCHOOLS_GRADE_LEVELS SGL " +
                "WHERE SGL.SCHOOL_ID = S.SCHOOL_ID " +
                "AND SGL.SCHOOL_YEAR = :schoolYear AND SGL.VALUE = :gradeLevel) ");
        if (category != null) {
            sql = sql.concat(" AND SPECIALIZED_CATEGORY_CODE = :category");
            params.put("category", category);
        }
        sql = sql.concat(" ORDER BY SCHOOL_NAME");

        return getNamedParameterJdbcTemplate().query(sql, params, new BeanPropertyRowMapper<>(School.class));
    }

    @Override
    public List<School> findTechnicalSchools(Long schoolYear) {
        return getJdbcTemplate().query(TECHNICAL_SCHOOLS, new BeanPropertyRowMapper<>(School.class));
    }

    @Override
    public List<School> findSharedSchools(Long schoolYear, String gradeLevel) {
        String sql = SHARED_SCHOOLS.concat(" WHERE EXISTS(SELECT 1 FROM VW_SCHOOLS_GRADE_LEVELS SGL " +
                "WHERE SGL.SCHOOL_ID = S.SCHOOL_ID " +
                "AND SGL.SCHOOL_YEAR = ? AND SGL.VALUE = ?) ");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(School.class), schoolYear, gradeLevel);
    }


    @Override
    public School findSharedSchoolById(Long schoolId) {
        String sql = SHARED_SCHOOLS.concat(" WHERE S.SCHOOL_ID = ? ");
        List<School> schools = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(School.class), schoolId);
        return schools.isEmpty() ? null : schools.get(0);
    }

    @Override
    public School findSchoolById(Long schoolId) {
        String sql = SCHOOL_SQL.concat(" WHERE SCHOOL_ID = ?");
        List<School> schools = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(School.class), schoolId);
        return schools.isEmpty() ? null : schools.get(0);
    }

    @Override
    public List<Region> getAllRegions() {
        return getJdbcTemplate().query(REGION_SQL, new BeanPropertyRowMapper<>(Region.class));
    }

    @Override
    public List<Region> getVocationalRegions() {
        String sql = REGION_SQL.concat(" WHERE REGION_ID IN (select distinct REGION_ID from VW_VOCATIONAL_SCHOOLS) ");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(Region.class));
    }

    @Override
    public SchoolGradeLevel findGradeLevelInfo(Long schoolYear, Long schoolId, String gradeLevel) {
        String sql = SCHOOL_GRADE_LEVELS.concat(" WHERE SCHOOL_YEAR = ? AND SCHOOL_ID = ? AND VALUE = ?");
        List<SchoolGradeLevel> levels = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(SchoolGradeLevel.class), schoolYear, schoolId, gradeLevel);
        return levels.isEmpty() ? null : levels.get(0);
    }

    @Override
    public List<VocationalProgram> getVocationalPrograms(Long schoolId, Long schoolYear) {
        String sql = VOCATIONAL_PROGRAMS.concat(" WHERE SCHOOL_ID = ? AND SCHOOL_YEAR = ?");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(VocationalProgram.class), schoolId, schoolYear);
    }

    @Override
    public boolean schoolInExclusionList(Long schoolId) {
        String sql = "SELECT COUNT(*) AS total FROM SCHOOL_EXCLUSION_LIST WHERE SCHOOL_ID = ? AND IS_ACTIVE_IND = 1 ";
        int total = getJdbcTemplate().queryForObject(sql, new SingleColumnRowMapper<>(Integer.class), schoolId);
        return total > 0;
    }

    @Override
    public boolean schoolInMontesorri(Long schoolId) {
        String sql = "SELECT COUNT(*) AS total " +
                "FROM VW_MONTESSORI_SCHOOLS RE " +
                "WHERE RE.SCHOOL_ID = ? ";
        int total = getJdbcTemplate().queryForObject(sql, new SingleColumnRowMapper<>(Integer.class), schoolId);
        return total > 0;
    }

    @Override
    public List<StudentScheduleView> getStudentScheduleView(Long studentId) {
        String sql = "select * from VW_STUDENT_SCHEDULE WHERE STUDENT_ID = ? ";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(StudentScheduleView.class), studentId);

    }
}