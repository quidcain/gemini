package com.gemini.commons.database.dao.impl;

import com.gemini.commons.database.beans.SchoolGradeLimit;
import com.gemini.commons.database.dao.SchoolCapDao;
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
    public List<SchoolGradeLimit> getLimitBySchool(Long schoolId) {
        String sql = "SELECT * FROM VW_CONFIRMED_CAPS WHERE SCHOOL_ID = ? ORDER BY GRADE_LEVEL ";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(SchoolGradeLimit.class), schoolId);
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