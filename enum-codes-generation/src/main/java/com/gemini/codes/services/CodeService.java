package com.gemini.codes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 1/29/18
 * Time: 2:53 PM
 */
@Service
public class CodeService extends JdbcDaoSupport {
    @Autowired
    DataSource dataSource;

    @PostConstruct
    void init() {
        this.setDataSource(dataSource);
    }

    public List<String> getCodesForTable(String enumTable) {
        return getJdbcTemplate().query(
                String.format("SELECT " +
                        "'{ name:  '''|| NAME || ''', description: '''|| DESCRIPTION||''', value: '''|| VALUE || ''', orderBy: '''|| ORDER_DEFAULT||'''}' as JSON_OBJECT " +
                        "FROM %s WHERE IS_ACTIVE_IND = 1 ORDER BY ORDER_DEFAULT ", enumTable), new SingleColumnRowMapper<String>());
    }

}
