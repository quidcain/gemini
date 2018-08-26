package com.gemini.commons.database.jpa.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/27/18
 * Time: 11:54 AM
 */
@Repository
public class CommonDaoImpl extends JdbcDaoSupport implements CommonDao {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void init() {
        setDataSource(dataSource);
    }

    @Override
    public Date getCurrentDate() {
        return getJdbcTemplate().queryForObject("select current_timestamp from dual", null, Date.class);
    }
}