package com.gemini.admin.database.jpa.repositories;

import com.gemini.admin.database.jpa.entities.SchoolGradeLimitEditRequestEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/28/18
 * Time: 8:06 PM
 */
public interface SchoolGradeLimitEditRequestRepository extends CrudRepository<SchoolGradeLimitEditRequestEntity, Long> {

    SchoolGradeLimitEditRequestEntity findByUserIdAndSchoolIdAndGradeLevel(Long userId, Long schoolId, String gradeLevel);
}