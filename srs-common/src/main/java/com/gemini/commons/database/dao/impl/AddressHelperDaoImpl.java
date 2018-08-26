package com.gemini.commons.database.dao.impl;

import com.gemini.commons.database.dao.AddressHelperDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 6/21/18
 * Time: 5:35 PM
 */
@Repository
public class AddressHelperDaoImpl  extends NamedParameterJdbcDaoSupport implements AddressHelperDao {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void init() {
        setDataSource(dataSource);
    }


    @Override
    public boolean isPRZipcode(String zipcode) {
        String sql = "select count(*) from ENUM_MAP_COUNTY_ZIPCODE " +
                "where zipcode = ? ";
        int total = getJdbcTemplate().queryForObject(sql, new SingleColumnRowMapper<>(Integer.class), zipcode);
        return total > 0;
    }

    @Override
    public String getCountyCode(String zipcode) {
        String sql = "select NVL(VALUE, NULL) from ENUM_MAP_COUNTY_ZIPCODE " +
                "where zipcode = ? ";
        List<String> list = getJdbcTemplate().query(sql, new SingleColumnRowMapper<>(String.class), zipcode);
        return list.isEmpty() ? null : list.get(0);
    }
}