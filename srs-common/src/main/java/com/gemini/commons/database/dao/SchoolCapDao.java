package com.gemini.commons.database.dao;


import com.gemini.admin.beans.requests.SchoolLimitSearchRequest;
import com.gemini.commons.database.beans.SchoolGradeLimit;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/28/18
 * Time: 7:57 PM
 */
public interface SchoolCapDao {

    List<SchoolGradeLimit> getLimitBySchool(SchoolLimitSearchRequest request);

    SchoolGradeLimit getLimitBySchoolAndGradeLevel(Long schoolId, String gradeLevel);

    SchoolGradeLimit getLimitById(Long schoolGradeLimitId);

    boolean isOvercapacity(Long schoolId, String gradeLevel);

    boolean updateLimitBySchoolAndGradeLevel(Long schoolId, String gradeLevel, Long userId, Integer newCapacity);

}