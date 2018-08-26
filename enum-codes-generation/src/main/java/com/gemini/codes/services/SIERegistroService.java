package com.gemini.codes.services;

import com.gemini.codes.beans.StudentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/22/18
 * Time: 9:33 AM
 */
@Service
public class SIERegistroService extends JdbcDaoSupport {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    void init() {
        this.setDataSource(dataSource);
    }

    public List<StudentResult> retrieve() {
        String sql = "SELECT * FROM REQUEST_RESULT WHERE SENT_DATE IS NULL ORDER BY ID ";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(StudentResult.class));
    }


    public List<StudentResult> retrieveSentByError() {
        String sql = "SELECT * FROM SENT_ERROR ORDER BY ID ";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(StudentResult.class));
    }

    public boolean maskAsSent(Long id) {
        String sql = "UPDATE REQUEST_RESULT SET SENT_DATE = SYSDATE WHERE ID = ?";
        return getJdbcTemplate().update(sql, id) > 0;
    }

    public boolean maskErrorAsSent(Long id) {
        String sql = "UPDATE SENT_ERROR SET SENT_DATE = SYSDATE WHERE ID = ?";
        return getJdbcTemplate().update(sql, id) > 0;
    }

    public boolean maskTry(Long id) {
        String sql = "UPDATE REQUEST_RESULT SET TRIES = TRIES +1 WHERE ID = ? ";
        return getJdbcTemplate().update(sql, id) > 0;
    }
}