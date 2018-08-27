package com.gemini.admin.database.dao.impl;

import com.gemini.admin.beans.AdminUser;
import com.gemini.admin.beans.Criteria;
import com.gemini.admin.database.AdminAccessHelper;
import com.gemini.admin.database.dao.StaffReportDao;
import com.gemini.admin.database.dao.beans.*;
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
 * Date: 8/3/18
 * Time: 4:07 PM
 */
@Repository("staffReportDao")
public class StaffDaoImpl extends NamedParameterJdbcDaoSupport implements StaffReportDao {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void init() {
        setDataSource(dataSource);
    }

    @Override
    public List<StaffSummaryRow> getSummaryByRegion(AdminUser user, Criteria criteria) {
        String sql = "select \n" +
                "REGION_ID\n" +
                ", REGION_NAME\n" +
                ",SUM(NEEDS) AS NEEDS\n" +
                ",SUM(ASSIGNS) AS ASSIGNS\n" +
                ",SUM(VACANTS) AS VACANTS\n" +
                ",SUM(ENROLLMENT_TOTAL) AS ENROLLMENT_TOTAL\n" +
                ", SUM(ASSIGN_TRANSIT) as ASSIGN_TRANSIT\n" +
                "FROM VW_STAFF_ANAL WHERE 1=1 \n";

        sql = sql.concat(AdminAccessHelper.addCriteriaFromUserInput(user, criteria));
        sql = sql.concat(" GROUP BY REGION_ID, REGION_NAME");
        sql = sql.concat(" ORDER BY REGION_NAME");

        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(StaffSummaryRow.class));
    }

    @Override
    public List<StaffSummaryRow> getSummaryByCity(AdminUser user, Criteria criteria) {
        String sql = "select \n" +
                "CITY_CODE\n" +
                ", CITY\n" +
                ",SUM(NEEDS) AS NEEDS\n" +
                ",SUM(ASSIGNS) AS ASSIGNS\n" +
                ",SUM(VACANTS) AS VACANTS\n" +
                ",SUM(ENROLLMENT_TOTAL) AS ENROLLMENT_TOTAL" +
                ", SUM(ASSIGN_TRANSIT) as ASSIGN_TRANSIT\n" +
                "FROM VW_STAFF_ANAL WHERE 1=1 \n";

        sql = sql.concat(AdminAccessHelper.addCriteriaFromUserInput(user, criteria));
        sql = sql.concat(" GROUP BY CITY_CODE, CITY");
        sql = sql.concat(" ORDER BY CITY");

        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(StaffSummaryRow.class));
    }

    @Override
    public List<StaffSummaryRow> getSummaryBySchool(AdminUser user, Criteria criteria) {
        String sql = "select \n" +
                "SCHOOL_ID\n" +
                ", SCHOOL_NAME\n" +
                ",SUM(NEEDS) AS NEEDS\n" +
                ",SUM(ASSIGNS) AS ASSIGNS\n" +
                ",SUM(VACANTS) AS VACANTS\n" +
                ",SUM(ENROLLMENT_TOTAL) AS ENROLLMENT_TOTAL " +
                ",SUM(ASSIGN_TRANSIT) as ASSIGN_TRANSIT \n" +
                "FROM VW_STAFF_ANAL WHERE 1=1 ";

        sql = sql.concat(AdminAccessHelper.addCriteriaFromUserInput(user, criteria));
        sql = sql.concat(" GROUP BY SCHOOL_ID, SCHOOL_NAME");
        sql = sql.concat(" ORDER BY SCHOOL_NAME");

        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(StaffSummaryRow.class));
    }

    @Override
    public List<EnrollmentStaffingRow> getEnrollmentAndStaffSummary(AdminUser user, Criteria criteria) {
        String sql = "select  \n" +
                "GRADE_LEVEL\n" +
                ", CATEGORY\n" +
                ", SUM(ENROLLMENT_TOTAL) as ENROLLMENT_TOTAL\n" +
                ", SUM(NEEDS) as  NEEDS\n" +
                ", SUM(ASSIGNS) as  ASSIGNS\n" +
                ", SUM(VACANTS) as VACANTS\n" +
                ", SUM(ASSIGN_TRANSIT) as ASSIGN_TRANSIT\n" +
                "FROM VW_STAFF_SUMMARY WHERE 1=1 ";

        sql = sql.concat(AdminAccessHelper.addCriteriaFromUserInput(user, criteria));
        sql = sql.concat(" GROUP BY GRADE_LEVEL, CATEGORY");
        sql = sql.concat(" ORDER BY GRADE_LEVEL, CATEGORY");

        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(EnrollmentStaffingRow.class));
    }

    @Override
    public List<VacantSummaryRow> getVacantSummary(AdminUser user, Criteria criteria) {
        String sql = "select \n" +
                "CITY_CODE\n" +
                ",CITY\n" +
                ",SUM(K_3) AS K_3\n" +
                ",SUM(G4_6) AS G4_6\n" +
                ",SUM(ENGLISH_SUPERIOR) AS ENGLISH_SUPERIOR\n" +
                ",SUM(MATH_SUPERIOR) AS MATH_SUPERIOR\n" +
                ",SUM(SPANISH_SUPERIOR) AS SPANISH_SUPERIOR\n" +
                ",SUM(HISTORY_SUPERIOR) AS HISTORY_SUPERIOR\n" +
                ",SUM(SCIENCE_SUPERIOR) AS SCIENCE_SUPERIOR\n" +
                ",SUM(OTHER) AS OTHER\n" +
                ",SUM(TOTAL) AS TOTAL\n" +
                "from VW_VACANT_SUMMARY WHERE 1=1 ";

        sql = sql.concat(AdminAccessHelper.addCriteriaFromUserInput(user, criteria));
        sql = sql.concat(" GROUP BY CITY_CODE,CITY ");
        sql = sql.concat(" ORDER BY CITY");

        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(VacantSummaryRow.class));
    }

    @Override
    public List<DetailedRosterRow> getDetailedRoster(AdminUser user, Criteria criteria) {
        String sql = "select * from VW_DETAIL_ROOSTER where 1=1 ";

        sql = sql.concat(AdminAccessHelper.addCriteriaFromUserInput(user, criteria));
        sql = sql.concat(" ORDER BY REGION_NAME, CITY, SCHOOL_NAME, STUDENT_GROUP, PUESTO");

        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(DetailedRosterRow.class));
    }

    @Override
    public List<ClassGroupWithoutTeacherRow> getClassGroupWithoutTeacher(AdminUser user, Criteria criteria) {
        String sql = "select * from VW_CLASS_VACANT where 1=1 ";

        sql = sql.concat(AdminAccessHelper.addCriteriaFromUserInput(user, criteria));
        sql = sql.concat(" ORDER BY REGION_NAME, CITY, SCHOOL_NAME, STUDENT_GROUP, CATEGORY");

        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(ClassGroupWithoutTeacherRow.class));
    }
}