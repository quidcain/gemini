package com.gemini.commons.database.dao.impl;

import com.gemini.admin.beans.requests.SchoolLimitSearchRequest;
import com.gemini.commons.database.beans.SchoolGradeLimit;
import com.gemini.commons.database.dao.SchoolCapDao;
import com.gemini.commons.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/28/18
 * Time: 7:57 PM
 */
@Repository
public class SchoolCapsDaoImpl extends NamedParameterJdbcDaoSupport implements SchoolCapDao {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void init() {
        setDataSource(dataSource);
    }


    @Override
    public List<SchoolGradeLimit> getLimitBySchool(SchoolLimitSearchRequest request) {
        String sql = "SELECT * FROM VW_CONFIRMED_CAPS WHERE 1=1 ";

        if (ValidationUtils.valid(request.getRegionId()))
            sql = sql.concat(String.format(" AND REGION_ID = %s ", request.getRegionId()));

        if (ValidationUtils.valid(request.getCityCode()) && !"-1".equals(request.getCityCode()))
            sql = sql.concat(String.format(" AND CITY_CODE = '%s' ", request.getCityCode()));

        if (ValidationUtils.valid(request.getSchoolId()))
            sql = sql.concat(String.format(" AND SCHOOL_ID = %s ", request.getSchoolId()));

        if (ValidationUtils.valid(request.getGradeLevel()) && !"-1".equals(request.getGradeLevel()))
            sql = sql.concat(String.format(" AND GRADE_LEVEL = '%s' ", request.getGradeLevel()));

        sql = sql.concat(" ORDER BY GRADE_LEVEL ");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(SchoolGradeLimit.class));
    }

    @Override
    public SchoolGradeLimit getLimitBySchoolAndGradeLevel(Long schoolId, String gradeLevel) {
        String sql = "SELECT SGL.*, S.SCHOOL_NAME FROM SY_SCHOOL_GRADE_LIMITS SGL " +
                "LEFT JOIN SY_SCHOOL S ON S.SCHOOL_ID = SGL.SCHOOL_ID " +
                "WHERE SGL.SCHOOL_ID = ? AND SGL.GRADE_LEVEL = ? ";
        List<SchoolGradeLimit> results = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(SchoolGradeLimit.class), schoolId, gradeLevel);
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public SchoolGradeLimit getLimitById(Long schoolGradeLimitId) {
        String sql = "SELECT * FROM SY_SCHOOL_GRADE_LIMITS WHERE SCHOOL_GRADE_LIMIT_ID = ? ";
        List<SchoolGradeLimit> results = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(SchoolGradeLimit.class), schoolGradeLimitId);
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public boolean isOvercapacity(Long schoolId, String gradeLevel) {
        String sql = "SELECT count(*) AS total FROM ENROLLMENT_PLACEMENT_RESULTS WHERE SCHOOL_ID = ? AND GRADE_LEVEL = ? AND OVER_CAPACITY = 1 ";
        int total = getJdbcTemplate().queryForObject(sql, new SingleColumnRowMapper<>(Integer.class), schoolId, gradeLevel);
        return total > 0;
    }

    public boolean updateLimitBySchoolAndGradeLevel(Long schoolId, String gradeLevel, Long userId, Integer newCapacity) {
        String sql = "UPDATE SY_SCHOOL_GRADE_LIMITS SET MAX_CAPACITY = ?, REVISION_USER_ID = ?, REVISION_DATE = SYSDATE " +
                " WHERE SCHOOL_ID = ? AND GRADE_LEVEL = ? ";
        int res = getJdbcTemplate().update(sql, newCapacity, userId, schoolId, gradeLevel);
        return res > 0;

    }
}