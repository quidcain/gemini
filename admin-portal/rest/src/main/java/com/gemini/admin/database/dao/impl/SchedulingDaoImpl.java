package com.gemini.admin.database.dao.impl;

import com.gemini.admin.beans.AdminUser;
import com.gemini.admin.beans.SchedulingCatCriteriaForm;
import com.gemini.admin.beans.SchedulingCriteriaForm;
import com.gemini.admin.database.AdminAccessHelper;
import com.gemini.admin.database.dao.SchedulingDao;
import com.gemini.admin.database.dao.beans.CategoryData;
import com.gemini.admin.database.dao.beans.RHStatData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 6/11/18
 * Time: 3:58 PM
 */
@Repository
public class SchedulingDaoImpl extends NamedParameterJdbcDaoSupport implements SchedulingDao {

    final String BASE_SQL_BY_REGION_AFTER = "select " +
            "region_id, " +
            "region " +
            ",(SUM(CASE WHEN NUM_EMPLEADO > 0 THEN 1 ELSE 0 END)) as asignados " +
            ", (SUM(CASE WHEN NUM_EMPLEADO < 0 THEN 1 ELSE 0 END)) as vacantes " +
            ", NVL((select count(*) from vw_excedentes_located where rh_report_cpy.region_id = vw_excedentes_located.region_id and rh_report_cpy.region = vw_excedentes_located.region group by region), 0) as excedentes " +
            "from rh_report_cpy ";

    final String BASE_SQL_BY_CITY_AFTER = "select " +
            "city " +
            ",(SUM(CASE WHEN NUM_EMPLEADO > 0 THEN 1 ELSE 0 END)) as asignados " +
            ", (SUM(CASE WHEN NUM_EMPLEADO < 0 THEN 1 ELSE 0 END)) as vacantes " +
            ", NVL((select count(*) from vw_excedentes_located where rh_report_cpy.city = vw_excedentes_located.city group by city), 0) as excedentes " +
            "from rh_report_cpy ";

    final String BASE_SQL_BY_SCHOOL_AFTER = "select " +
            "school_id " +
            ", school_name  " +
            ",(SUM(CASE WHEN NUM_EMPLEADO > 0 THEN 1 ELSE 0 END)) as asignados " +
            ", (SUM(CASE WHEN NUM_EMPLEADO < 0 THEN 1 ELSE 0 END)) as vacantes " +
            ", NVL((select count(*) from vw_excedentes_located where rh_report_cpy.school_id = vw_excedentes_located.school_id and rh_report_cpy.school_name = vw_excedentes_located.school_name group by school_id, school_name), 0) as excedentes " +
            "from rh_report_cpy ";

    final String BASE_SQL_BY_REGION_AFTER_EE = "select " +
            "region_id, " +
            "upper(region) as region" +
            ",(SUM(CASE WHEN NUM_EMPLEADO > 0 THEN 1 ELSE 0 END)) as asignados " +
            ", (SUM(CASE WHEN NUM_EMPLEADO < 0 THEN 1 ELSE 0 END)) as vacantes " +
            ", NVL((select count(*) from vw_excedentes_located_ee where  upper(ee_report_rh_cpy.region) = upper(vw_excedentes_located_ee.region) group by upper(region)), 0) as excedentes " +
            "from ee_report_rh_cpy ";

    final String BASE_SQL_BY_CITY_AFTER_EE = "select " +
            "upper(city) as city" +
            ",(SUM(CASE WHEN NUM_EMPLEADO > 0 THEN 1 ELSE 0 END)) as asignados " +
            ", (SUM(CASE WHEN NUM_EMPLEADO < 0 THEN 1 ELSE 0 END)) as vacantes " +
            ", NVL((select count(*) from vw_excedentes_located_ee where upper(ee_report_rh_cpy.city) = upper(vw_excedentes_located_ee.city) group by upper(city)), 0) as excedentes " +
            "from ee_report_rh_cpy ";

    final String BASE_SQL_BY_SCHOOL_AFTER_EE = "select " +
            "school_id " +
            ", upper(school_name)  as school_name" +
            ",(SUM(CASE WHEN NUM_EMPLEADO > 0 THEN 1 ELSE 0 END)) as asignados " +
            ", (SUM(CASE WHEN NUM_EMPLEADO < 0 THEN 1 ELSE 0 END)) as vacantes " +
            ", NVL((select count(*) from vw_excedentes_located_ee where ee_report_rh_cpy.school_id = vw_excedentes_located_ee.school_id and upper(ee_report_rh_cpy.school_name) = upper(vw_excedentes_located_ee.school_name) group by school_id, upper(school_name)), 0) as excedentes " +
            "from ee_report_rh_cpy ";

    final String BASE_SQL_BY_REGION_OC = "select " +
            "region_id, " +
            "upper(region) as region " +
            ",(SUM(CASE WHEN NUM_EMPLEADO > 0 THEN 1 ELSE 0 END)) as asignados " +
            ", (SUM(CASE WHEN NUM_EMPLEADO < 0 THEN 1 ELSE 0 END)) as vacantes " +
            ", NVL((select count(*) from OC_EXCEDENTES where vw_rh_report_oc.region_id = OC_EXCEDENTES.region_id and upper(vw_rh_report_oc.region) = upper(OC_EXCEDENTES.region) ), 0) as excedentes " +
            "from vw_rh_report_oc ";

    final String BASE_SQL_BY_CITY_OC = "select " +
            "upper(city) as city " +
            ",(SUM(CASE WHEN NUM_EMPLEADO > 0 THEN 1 ELSE 0 END)) as asignados " +
            ", (SUM(CASE WHEN NUM_EMPLEADO < 0 THEN 1 ELSE 0 END)) as vacantes " +
            ", NVL((select count(*) from OC_EXCEDENTES where upper(vw_rh_report_oc.city) = upper(OC_EXCEDENTES.city) group by city), 0) as excedentes " +
            "from vw_rh_report_oc ";

    final String BASE_SQL_BY_SCHOOL_OC = "select " +
            "school_id " +
            ", upper(school_name) as school_name  " +
            ",(SUM(CASE WHEN NUM_EMPLEADO > 0 THEN 1 ELSE 0 END)) as asignados " +
            ", (SUM(CASE WHEN NUM_EMPLEADO < 0 THEN 1 ELSE 0 END)) as vacantes " +
            ", NVL((select count(*) from OC_EXCEDENTES where vw_rh_report_oc.school_id = OC_EXCEDENTES.school_id ), 0) as excedentes " +
            "from vw_rh_report_oc ";


    final String ALL_REGULAR = "select category, COUNT(*) as total from vw_rh_all ";
    final String ASIGNADOS_SQL = "select PUESTO as category, COUNT(*) as total from vw_asignados_located ";
    final String VACANTES_SQL = "select PUESTO as category, COUNT(*) as total from vw_vacantes_located ";
    final String EXCEDENTES_SQL = "select PUESTO as category, COUNT(*) as total from vw_excedentes_located ";

    final String ALL_EE = "select category, COUNT(*) as total from vw_rh_all_ee ";
    final String ASIGNADOS_SQL_EE = "select PUESTO as category, COUNT(*) as total from vw_asignados_located_ee ";
    final String VACANTES_SQL_EE = "select PUESTO as category, COUNT(*) as total from vw_vacantes_located_ee ";
    final String EXCEDENTES_SQL_EE = "select PUESTO as category, COUNT(*) as total from vw_excedentes_located_ee ";

    final String ALL_OC = "select category, COUNT(*) as total from VW_RH_OC ";
    final String ASIGNADOS_SQL_OC = "select PUESTO as category, COUNT(*) as total from OC_ASIGNADOS ";
    final String VACANTES_SQL_OC = "select PUESTO as category, COUNT(*) as total from OC_VACANTES ";
    final String EXCEDENTES_SQL_OC = "select PUESTO as category, COUNT(*) as total from OC_EXCEDENTES ";


    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void init() {
        setDataSource(dataSource);
    }

    @Override
    public List<RHStatData> getRegularStatAfterPlacement(SchedulingCriteriaForm criteria, AdminUser adminUser) {
        String sql = BASE_SQL_BY_REGION_AFTER.concat(" WHERE 1=1 ")
                .concat(AdminAccessHelper.addCriteriaFromUserInput(adminUser, criteria))
                .concat(" group by region_id, region ")
                .concat(" ORDER BY REGION ");

        if (criteria.isCityOnly()) {
            sql = BASE_SQL_BY_CITY_AFTER.concat(" WHERE 1=1 ")
                    .concat(AdminAccessHelper.addCriteriaFromUserInput(adminUser, criteria))
                    .concat(" group by city ")
                    .concat(" ORDER BY CITY ");
        }

        if (criteria.isSchoolOnly())
            sql = BASE_SQL_BY_SCHOOL_AFTER.concat(" WHERE 1=1 ")
                    .concat(AdminAccessHelper.addCriteriaFromUserInput(adminUser, criteria))
                    .concat(" group by school_id, school_name ")
                    .concat(" ORDER BY school_name ");


        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(RHStatData.class));
    }

    @Override
    public List<RHStatData> getEEStatPlacement(SchedulingCriteriaForm criteria, AdminUser adminUser) {
        String sql = BASE_SQL_BY_REGION_AFTER_EE.concat(" WHERE 1=1 ")
                .concat(AdminAccessHelper.addCriteriaFromUserInput(adminUser, criteria))
                .concat(" group by region_id, upper(region) ")
                .concat(" ORDER BY REGION ");

        if (criteria.isCityOnly()) {
            sql = BASE_SQL_BY_CITY_AFTER_EE.concat(" WHERE 1=1 ")
                    .concat(AdminAccessHelper.addCriteriaFromUserInput(adminUser, criteria))
                    .concat(" group by upper(city) ")
                    .concat(" ORDER BY CITY ");
        }

        if (criteria.isSchoolOnly())
            sql = BASE_SQL_BY_SCHOOL_AFTER_EE.concat(" WHERE 1=1 ")
                    .concat(AdminAccessHelper.addCriteriaFromUserInput(adminUser, criteria))
                    .concat(" group by school_id, upper(school_name) ")
                    .concat(" ORDER BY school_name ");

        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(RHStatData.class));
    }

    @Override
    public List<RHStatData> getOCStatPlacement(SchedulingCriteriaForm criteria, AdminUser adminUser) {
        String sql = BASE_SQL_BY_REGION_OC.concat(" WHERE 1=1 ")
                .concat(AdminAccessHelper.addCriteriaFromUserInput(adminUser, criteria))
                .concat(" group by region_id, upper(region) ")
                .concat(" ORDER BY REGION ");

        if (criteria.isCityOnly()) {
            sql = BASE_SQL_BY_CITY_OC.concat(" WHERE 1=1 ")
                    .concat(AdminAccessHelper.addCriteriaFromUserInput(adminUser, criteria))
                    .concat(" group by upper(city) ")
                    .concat(" ORDER BY CITY ");
        }

        if (criteria.isSchoolOnly())
            sql = BASE_SQL_BY_SCHOOL_OC.concat(" WHERE 1=1 ")
                    .concat(AdminAccessHelper.addCriteriaFromUserInput(adminUser, criteria))
                    .concat(" group by school_id, upper(school_name) ")
                    .concat(" ORDER BY school_name ");

        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(RHStatData.class));
    }

    @Override
    public List<CategoryData> getAllCategories(SchedulingCatCriteriaForm criteria) {
        String sql = ALL_REGULAR.concat(" WHERE 1=1 ");
        sql = sqlCriteria(sql, criteria);
        sql = sql.concat("group by category ")
                .concat("order by category ");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(CategoryData.class));
    }

    @Override
    public List<CategoryData> getAsignadosCategories(SchedulingCatCriteriaForm criteria) {
        String sql = ASIGNADOS_SQL.concat(" WHERE 1=1 ");

        sql = sqlCriteria(sql, criteria);

        sql = sql.concat("group by PUESTO ")
                .concat("order by category ");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(CategoryData.class));
    }

    @Override
    public List<CategoryData> getVacantesCategories(SchedulingCatCriteriaForm criteria) {
        String sql = VACANTES_SQL.concat(" WHERE 1=1 ");
        sql = sqlCriteria(sql, criteria);
        sql = sql.concat("group by PUESTO ")
                .concat(criteria.isOrderByCount() ? "order by total desc " : "order by category ");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(CategoryData.class));
    }

    @Override
    public List<CategoryData> getExcedentesCategories(SchedulingCatCriteriaForm criteria) {
        String sql = EXCEDENTES_SQL.concat(" WHERE 1=1 ");
        sql = sqlCriteria(sql, criteria);
        sql = sql.concat("group by PUESTO ")
                .concat("order by category ");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(CategoryData.class));
    }

    @Override
    public List<CategoryData> getAllCategoriesEE(SchedulingCatCriteriaForm criteria) {
        String sql = ALL_EE.concat(" WHERE 1=1 ");
        sql = sqlCriteria(sql, criteria);
        sql = sql.concat("group by category ")
                .concat("order by category ");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(CategoryData.class));
    }

    @Override
    public List<CategoryData> getAsignadosCategoriesEE(SchedulingCatCriteriaForm criteria) {
        String sql = ASIGNADOS_SQL_EE.concat(" WHERE 1=1 ");
        sql = sqlCriteria(sql, criteria);
        sql = sql.concat("group by PUESTO ")
                .concat("order by category ");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(CategoryData.class));
    }

    @Override
    public List<CategoryData> getVacantesCategoriesEE(SchedulingCatCriteriaForm criteria) {
        String sql = VACANTES_SQL_EE.concat(" WHERE 1=1 ");
        sql = sqlCriteria(sql, criteria);
        sql = sql.concat("group by PUESTO ")
                .concat(criteria.isOrderByCount() ? "order by total desc " : "order by category ");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(CategoryData.class));
    }

    @Override
    public List<CategoryData> getExcedentesCategoriesEE(SchedulingCatCriteriaForm criteria) {
        String sql = EXCEDENTES_SQL_EE.concat(" WHERE 1=1 ");
        sql = sqlCriteria(sql, criteria);
        sql = sql.concat("group by PUESTO ")
                .concat("order by category ");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(CategoryData.class));
    }

    @Override
    public List<CategoryData> getAllCategoriesOC(SchedulingCatCriteriaForm criteria) {
        String sql = ALL_OC.concat(" WHERE 1=1 ");
        sql = sqlCriteria(sql, criteria);
        sql = sql.concat("group by category ")
                .concat("order by category ");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(CategoryData.class));
    }

    @Override
    public List<CategoryData> getAsignadosCategoriesOC(SchedulingCatCriteriaForm criteria) {
        String sql = ASIGNADOS_SQL_OC.concat(" WHERE 1=1 ");
        sql = sqlCriteria(sql, criteria);
        sql = sql.concat("group by PUESTO ")
                .concat("order by category ");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(CategoryData.class));
    }

    @Override
    public List<CategoryData> getVacantesCategoriesOC(SchedulingCatCriteriaForm criteria) {
        String sql = VACANTES_SQL_OC.concat(" WHERE 1=1 ");
        sql = sqlCriteria(sql, criteria);
        sql = sql.concat("group by PUESTO ")
                .concat(criteria.isOrderByCount() ? "order by total desc " : "order by category ");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(CategoryData.class));
    }

    @Override
    public List<CategoryData> getExcedentesCategoriesOC(SchedulingCatCriteriaForm criteria) {
        String sql = EXCEDENTES_SQL_OC.concat(" WHERE 1=1 ");
        sql = sqlCriteria(sql, criteria);
        sql = sql.concat("group by PUESTO ")
                .concat("order by category ");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(CategoryData.class));
    }

    private String sqlCriteria(String sql, SchedulingCatCriteriaForm criteria) {
        if (criteria.isRegionOnly()) {
            sql = sql.concat(String.format("and REGION_ID = %s ", criteria.getRegionId()));
        } else if (criteria.isCityOnly()) {

            if (!StringUtils.hasText(criteria.getCityCode()) || "-1".equals(criteria.getCityCode())) {
                sql = sql.concat(String.format("and UPPER(REMOVE_SPANISH_ACCENTS(CITY)) = UPPER(REMOVE_SPANISH_ACCENTS('%s')) ", criteria.getCity()));
            } else
                sql = sql.concat(String.format("and CITY_CODE = '%s' ", criteria.getCityCode()));

        } else if (criteria.isSchoolOnly()) {
            sql = sql.concat(String.format("and SCHOOL_ID =  %s ", criteria.getSchoolId()));
        }
        return sql;
    }

}