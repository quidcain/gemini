package com.gemini.commons.database.jpa.respository;

import com.gemini.commons.database.jpa.entities.SchoolsCapsEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/12/18
 * Time: 1:06 PM
 */
public interface SchoolCapsRepository extends CrudRepository<SchoolsCapsEntity, Long>{

    SchoolsCapsEntity findBySchoolIdAndGradeLevel(Long schoolId, String gradeLevel);
}