package com.gemini.admin.database.dao;

import com.gemini.admin.database.dao.beans.RequestResult;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/30/18
 * Time: 6:54 PM
 */
public interface RequestResultDao {

    RequestResult getRequestResult(Long preEnrollmentId);
}