package com.gemini.admin.database.dao.impl;

import com.gemini.admin.database.dao.SmaxUserDao;
import com.gemini.admin.database.dao.beans.SieRole;
import com.gemini.admin.database.dao.beans.SieUser;
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
 * Date: 4/8/18
 * Time: 11:56 AM
 */
@Repository("smaxUserDao")
public class SmaxUserDaoImpl extends NamedParameterJdbcDaoSupport implements SmaxUserDao {

    final String ADMIN_USERS_SQL = "SELECT * FROM VW_ADMIN_SEC_USER";
    final String ADMIN_ROLES_SQL = "SELECT * FROM VW_ADMIN_SEC_ROLE";
    final String SCHOOL_SQL = "SELECT * FROM VW_SCHOOLS S ";
    final String SCHOOL_ADMIN_SQL = "SELECT * FROM VW_SCHOOLS_ADMIN S ";



    @Autowired
    private DataSource dataSource;


    @PostConstruct
    private void init() {
        setDataSource(dataSource);
    }

    @Override
    public SieUser findByUserId(Long userId) {
        String query = ADMIN_USERS_SQL.concat(" WHERE USER_ID = ? ");
        List<SieUser> list = getJdbcTemplate().query(query, new BeanPropertyRowMapper<>(SieUser.class), userId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public SieUser loadByUsername(String username) {
        String query = ADMIN_USERS_SQL.concat(" WHERE UPPER(USERNAME) = upper(?) ");
        List<SieUser> list = getJdbcTemplate().query(query, new BeanPropertyRowMapper<>(SieUser.class), username);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<SieRole> loadRoles(Long userId) {
        String query = ADMIN_ROLES_SQL.concat(" WHERE USER_ID = ? ORDER BY PRECEDENCE ");
        return getJdbcTemplate().query(query, new BeanPropertyRowMapper<>(SieRole.class), userId);
    }

    @Override
    public School getSchoolById(Long schoolId) {
        String query = SCHOOL_ADMIN_SQL.concat(" WHERE SCHOOL_ID = ? ");
        List<School> list = getJdbcTemplate().query(query, new BeanPropertyRowMapper<>(School.class), schoolId);
        return list.isEmpty() ? null : list.get(0);
    }
}
