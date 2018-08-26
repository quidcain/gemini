package com.gemini.admin.database.dao;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/7/18
 * Time: 1:51 PM
 */
public interface PortalUserDao {

    boolean moveRequestsToUserId( Long actualUserId, Long prevailUserId);

    boolean transferRequestToUser(Long userId, Long requestId);
}