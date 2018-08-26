package com.gemini.admin.database.dao;

import com.gemini.admin.database.dao.beans.SieRole;
import com.gemini.admin.database.dao.beans.SieUser;
import com.gemini.commons.database.beans.School;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/9/18
 * Time: 3:50 PM
 */
public interface SmaxUserDao {
    SieUser findByUserId(Long userId);

    SieUser loadByUsername(String username);

    List<SieRole> loadRoles(Long userId);

    School getSchoolById(Long schoolId);
}