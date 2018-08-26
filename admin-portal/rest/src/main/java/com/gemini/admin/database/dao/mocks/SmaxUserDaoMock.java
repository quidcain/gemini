package com.gemini.admin.database.dao.mocks;

import com.gemini.admin.database.dao.SmaxUserDao;
import com.gemini.admin.database.dao.beans.SieRole;
import com.gemini.admin.database.dao.beans.SieUser;
import com.gemini.commons.database.beans.School;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/9/18
 * Time: 3:52 PM
 */
@Repository("smaxUserDaoMock")
public class SmaxUserDaoMock implements SmaxUserDao {

    @Override
    public SieUser findByUserId(Long userId) {
        return null;
    }

    @Override
    public School getSchoolById(Long schoolId) {
        School school = new School();
        school.setSchoolId(1l);
        school.setRegionId(1l);
        school.setSchoolName("Estress level al maximo");
        return school;
    }

    @Override
    public SieUser loadByUsername(String username) {
        SieUser user = new SieUser();
        user.setFirstName("User");
        user.setLastName("Test");
        user.setPassword("cRDtpNCeBiql5KOQsKVyrA0sAiA=");
        user.setUsername(username);
        user.setUserId(1L);
        return user;
    }

    @Override
    public List<SieRole> loadRoles(Long userId) {
        List<SieRole> roles = new ArrayList<>();
        SieRole role = new SieRole();
        role.setRoleId(3641L);
        role.setPrecedence(2);
        role.setUserId(userId);
        role.setRegionId(1L);
        role.setSchoolId(1l);
        roles.add(role);
        return roles;
    }
}