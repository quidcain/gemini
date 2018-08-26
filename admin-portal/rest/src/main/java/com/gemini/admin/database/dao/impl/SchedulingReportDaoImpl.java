package com.gemini.admin.database.dao.impl;

import com.gemini.admin.beans.AdminUser;
import com.gemini.admin.beans.SchedulingCriteriaForm;
import com.gemini.admin.beans.report.SchedulingReport;
import com.gemini.admin.database.AdminAccessHelper;
import com.gemini.admin.database.dao.SchedulingReportDao;
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
 * Date: 6/19/18
 * Time: 5:35 PM
 */
@Repository
public class SchedulingReportDaoImpl extends NamedParameterJdbcDaoSupport implements SchedulingReportDao {

    final String ASIGNADOS_SQL = "select * from vw_asignados_located ";
    final String VACANTES_SQL = "select * from vw_vacantes_located ";
    final String EXCEDENTES_SQL = "select * from vw_excedentes_located ";

    final String ASIGNADOS_SQL_EE = "select * from vw_asignados_located_ee ";
    final String VACANTES_SQL_EE = "select * from vw_vacantes_located_ee ";
    final String EXCEDENTES_SQL_EE = "select * from vw_excedentes_located_ee ";


    final String ASIGNADOS_SQL_OC = "select * from OC_ASIGNADOS ";
    final String VACANTES_SQL_OC = "select * from OC_VACANTES ";
    final String EXCEDENTES_SQL_OC = "select * from OC_EXCEDENTES  ";


    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void init() {
        setDataSource(dataSource);
    }

    @Override
    public List<SchedulingReport> getAsignadosRegular(SchedulingCriteriaForm criteria, AdminUser adminUser) {
        String sql = ASIGNADOS_SQL.concat(" WHERE 1=1 ")
                .concat(AdminAccessHelper.addCriteriaFromUserInput(adminUser, criteria))
                .concat(" ORDER BY REGION, CITY, SCHOOL_NAME, PUESTO, FULL_NAME ");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(SchedulingReport.class));
    }

    @Override
    public List<SchedulingReport> getVacantesRegular(SchedulingCriteriaForm criteria, AdminUser adminUser) {
        String sql = VACANTES_SQL.concat(" WHERE 1=1 ")
                .concat(AdminAccessHelper.addCriteriaFromUserInput(adminUser, criteria))
                .concat(" ORDER BY REGION, CITY, SCHOOL_NAME, PUESTO, FULL_NAME ");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(SchedulingReport.class));
    }

    @Override
    public List<SchedulingReport> getExcedentesRegular(SchedulingCriteriaForm criteria, AdminUser adminUser) {
        String sql = EXCEDENTES_SQL.concat(" WHERE 1=1 ")
                .concat(AdminAccessHelper.addCriteriaFromUserInput(adminUser, criteria))
                .concat(" ORDER BY REGION, CITY, SCHOOL_NAME, PUESTO, FULL_NAME ");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(SchedulingReport.class));
    }

    @Override
    public List<SchedulingReport> getAsignadosEE(SchedulingCriteriaForm criteria, AdminUser adminUser) {
        String sql = ASIGNADOS_SQL_EE.concat(" WHERE 1=1 ")
                .concat(AdminAccessHelper.addCriteriaFromUserInput(adminUser, criteria))
                .concat(" ORDER BY REGION, CITY, SCHOOL_NAME, PUESTO, FULL_NAME ");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(SchedulingReport.class));
    }

    @Override
    public List<SchedulingReport> getVacantesEE(SchedulingCriteriaForm criteria, AdminUser adminUser) {
        String sql = VACANTES_SQL_EE.concat(" WHERE 1=1 ")
                .concat(AdminAccessHelper.addCriteriaFromUserInput(adminUser, criteria))
                .concat(" ORDER BY REGION, CITY, SCHOOL_NAME, PUESTO, FULL_NAME ");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(SchedulingReport.class));
    }

    @Override
    public List<SchedulingReport> getExcedentesEE(SchedulingCriteriaForm criteria, AdminUser adminUser) {
        String sql = EXCEDENTES_SQL_EE.concat(" WHERE 1=1 ")
                .concat(AdminAccessHelper.addCriteriaFromUserInput(adminUser, criteria))
                .concat(" ORDER BY REGION, CITY, SCHOOL_NAME, PUESTO, FULL_NAME ");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(SchedulingReport.class));
    }

    @Override
    public List<SchedulingReport> getAsignadosOC(SchedulingCriteriaForm criteria, AdminUser adminUser) {
        String sql = ASIGNADOS_SQL_OC.concat(" WHERE 1=1 ")
                .concat(AdminAccessHelper.addCriteriaFromUserInput(adminUser, criteria))
                .concat(" ORDER BY REGION, CITY, SCHOOL_NAME, PUESTO, FULL_NAME ");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(SchedulingReport.class));
    }

    @Override
    public List<SchedulingReport> getVacantesOC(SchedulingCriteriaForm criteria, AdminUser adminUser) {
        String sql = VACANTES_SQL_OC.concat(" WHERE 1=1 ")
                .concat(AdminAccessHelper.addCriteriaFromUserInput(adminUser, criteria))
                .concat(" ORDER BY REGION, CITY, SCHOOL_NAME, PUESTO, FULL_NAME ");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(SchedulingReport.class));
    }

    @Override
    public List<SchedulingReport> getExcedentesOC(SchedulingCriteriaForm criteria, AdminUser adminUser) {
        String sql = EXCEDENTES_SQL_OC.concat(" WHERE 1=1 ")
                .concat(AdminAccessHelper.addCriteriaFromUserInput(adminUser, criteria))
                .concat(" ORDER BY REGION, CITY, SCHOOL_NAME, PUESTO, FULL_NAME ");
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(SchedulingReport.class));
    }
}