package com.gemini.admin.database.dao.impl;

import com.gemini.admin.database.dao.PortalUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/7/18
 * Time: 1:53 PM
 */
@Repository()
public class PortalUserDaoImpl extends NamedParameterJdbcDaoSupport implements PortalUserDao {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void init() {
        setDataSource(dataSource);
    }

    @Override
    public boolean moveRequestsToUserId(Long actualUserId, Long prevailUserId) {
        String sql = "UPDATE users_requests SET user_entity_id = ? WHERE user_entity_id = ? ";
        logger.warn(String.format("merging enrollments UPDATE users_requests SET user_entity_id = %s WHERE user_entity_id = %s", prevailUserId, actualUserId));

        return getJdbcTemplate().update(sql, prevailUserId, actualUserId) > 0;
    }

    @Override
    public boolean transferRequestToUser(Long userId, Long requestId) {
        String sql = "UPDATE users_requests SET user_entity_id = ? WHERE requests_id = ? ";
        logger.warn(String.format("UPDATE users_requests SET user_entity_id = %s WHERE requests_id = %s", userId, requestId));

        return getJdbcTemplate().update(sql, userId, requestId) > 0;
    }
}