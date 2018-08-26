package com.gemini.admin.database.dao.impl;

import com.gemini.admin.beans.AdminUser;
import com.gemini.admin.beans.CriteriaForm;
import com.gemini.admin.database.AccessFrom;
import com.gemini.admin.database.AdminAccessHelper;
import com.gemini.admin.database.dao.DashboardDao;
import com.gemini.admin.database.dao.beans.EnrollmentProgress;
import com.gemini.commons.database.beans.City;
import com.gemini.commons.database.beans.GradeLevel;
import com.gemini.commons.database.beans.Region;
import com.gemini.commons.database.beans.School;
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
 * Date: 4/8/18
 * Time: 4:21 PM
 */
@Repository("dashboardDao")
public class DashboardDaoImpl extends NamedParameterJdbcDaoSupport implements DashboardDao {

    private final String REGION_SQL = "SELECT * FROM VW_REGIONS ";
    private final String CITIES_SQL = "SELECT distinct CITY_CD AS CITY_CODE, CITY AS FROM VW_SCHOOLS S ";
    private final String SCHOOL_SQL = "SELECT * FROM VW_SCHOOLS S ";

    private final String PRE_ENROLLMENT_BY_SIE = "SELECT SUM(TOTAL) AS TOTAL FROM VW_ST_ENROLLMENT_COUNT ";
    private final String CONFIRMED_ENROLLMENT = "SELECT COUNT(*) FROM VW_DASH_PRE_ENROLLMENTS P WHERE STAT_TYPE = 'APPROVED_REC' ";
    private final String NEW_ENTRY_ENROLLMENT = "SELECT COUNT(*) FROM VW_DASH_PRE_ENROLLMENTS P WHERE  STAT_TYPE = 'DENIED_BY_PARENT_REC' ";
    private final String DENIED_ENROLLMENT = "SELECT COUNT(*) FROM VW_DASH_PRE_ENROLLMENTS P WHERE STAT_TYPE = 'NEW_ENTRY_REC' ";

//    private final String APPROVED_BY_DIRECTOR = "SELECT COUNT(*) FROM VW_DASH_PRE_ENROLLMENTS P WHERE TYPE IN ('REGULAR_ALTERNATE_SCHOOLS') AND ENTRY_TYPE = 'EXISTING' AND STAT_TYPE = 'DENIED_BY_DIRECTOR_REC' ";
//    private final String DENIED_BY_DIRECTOR = "SELECT COUNT(*) FROM VW_DASH_PRE_ENROLLMENTS P WHERE TYPE IN ('REGULAR_ALTERNATE_SCHOOLS') AND ENTRY_TYPE = 'EXISTING' AND STAT_TYPE = 'DENIED_BY_DIRECTOR_REC' ";

    private final String ENROLLMENT_SUMMARY = "SELECT " +
            "ACTION_DATE, " +
            "SUM(CONFIRMADOS) AS TOTAL_CONFIRMED, " +
            "SUM(NUEVO_INGRESO) AS TOTAL_NEW_ENTRY_ENROLLMENTS, " +
            "SUM(RECHAZADOS) AS TOTAL_DENIED " +
            "FROM VW_ENROLLMENT_PROGRESS ";
    private final String ENROLLMENT_PROGRESS = "SELECT * FROM VW_ENROLLMENT_PROGRESS ";

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void init() {
        setDataSource(dataSource);
    }

    //  regions, municipios & schools

    @Override
    public List<Region> getRegions(AdminUser user) {
        String query = REGION_SQL.concat(" WHERE 1=1 ");
        query = query.concat(AdminAccessHelper.addCriteria(user, AccessFrom.REGIONS));
        return getJdbcTemplate().query(query, new BeanPropertyRowMapper<>(Region.class));
    }

    @Override
    public List<City> getCities(Long regionId, AdminUser user) {
        String query = CITIES_SQL.concat(" WHERE 1=1 ");
        query = query
                .concat(AdminAccessHelper.addCriteria(user, "REGION_ID", regionId))
                .concat(" ORDER BY CITY ");
        return getJdbcTemplate().query(query, new BeanPropertyRowMapper<>(City.class));
    }

    @Override
    public List<School> getSchools(Long regionId, String cityCode, AdminUser user) {
        String query = SCHOOL_SQL.concat(" WHERE 1=1 ");
        query = query
                .concat(AdminAccessHelper.addCriteria(user, "REGION_ID", regionId))
                .concat(AdminAccessHelper.addCriteria(user, "CITY_CD", cityCode))
                .concat(" ORDER BY REMOVE_SPANISH_ACCENTS(SCHOOL_NAME) ");
        return getJdbcTemplate().query(query, new BeanPropertyRowMapper<>(School.class));
    }

    @Override
    public List<GradeLevel> getGradeLevels(Long schoolId) {
        String query = "SELECT * FROM VW_SCHOOLS_GRADE_LEVELS WHERE SCHOOL_ID = ? AND SCHOOL_YEAR = 2019 ORDER BY NAME";
        return getJdbcTemplate().query(query, new BeanPropertyRowMapper<>(GradeLevel.class), schoolId);
    }

    @Override
    public int getPreEnrollmentBySIETotal(AdminUser user, CriteriaForm criteria) {
        String query = PRE_ENROLLMENT_BY_SIE.concat(" WHERE 1=1 ");
        query = query.concat(AdminAccessHelper.addCriteriaFromUserInput(user, criteria));
        return getJdbcTemplate().queryForObject(query, new SingleColumnRowMapper<>(Integer.class));
    }

    @Override
    public int getConfirmedTotal(AdminUser user, CriteriaForm criteria) {
        String query = CONFIRMED_ENROLLMENT;
        query = query.concat(AdminAccessHelper.addCriteriaFromUserInput(user, criteria));
        return getJdbcTemplate().queryForObject(query, new SingleColumnRowMapper<>(Integer.class));
    }

    @Override
    public int getNewEntryTotal(AdminUser user, CriteriaForm criteria) {
        String query = NEW_ENTRY_ENROLLMENT;
        query = query.concat(AdminAccessHelper.addCriteriaFromUserInput(user, criteria));
        return getJdbcTemplate().queryForObject(query, new SingleColumnRowMapper<>(Integer.class));
    }

    @Override
    public int getDeniedTotal(AdminUser user, CriteriaForm criteria) {
        String query = DENIED_ENROLLMENT;
        query = query.concat(AdminAccessHelper.addCriteriaFromUserInput(user, criteria));
        return getJdbcTemplate().queryForObject(query, new SingleColumnRowMapper<>(Integer.class));
    }

    @Override
    public List<EnrollmentProgress> getProgress(AdminUser user, CriteriaForm criteria) {
        String query = ENROLLMENT_SUMMARY.concat(" WHERE 1=1 ");
        query = query
                .concat(AdminAccessHelper.addCriteriaFromUserInput(user, criteria))
                .concat(" GROUP BY ACTION_DATE")
                .concat(" ORDER BY ACTION_DATE");
        return getJdbcTemplate().query(query, new BeanPropertyRowMapper<>(EnrollmentProgress.class));
    }


}
