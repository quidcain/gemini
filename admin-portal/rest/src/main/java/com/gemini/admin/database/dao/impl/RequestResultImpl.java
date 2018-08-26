package com.gemini.admin.database.dao.impl;

import com.gemini.admin.database.dao.RequestResultDao;
import com.gemini.admin.database.dao.beans.RequestResult;
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
 * Date: 5/30/18
 * Time: 6:54 PM
 */

@Repository()
public class RequestResultImpl extends NamedParameterJdbcDaoSupport implements RequestResultDao {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void init() {
        setDataSource(dataSource);
    }

    @Override
    public RequestResult getRequestResult(Long preEnrollmentId) {
        String sql = "SELECT * FROM REQUEST_RESULT WHERE REQUEST_ID = ? ORDER BY RUN_SEQ DESC ";
        List<RequestResult> results = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(RequestResult.class), preEnrollmentId);
        return results.isEmpty() ? null : results.get(0);
    }
}