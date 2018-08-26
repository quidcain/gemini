package com.gemini.commons.database.jpa.respository;

import com.gemini.commons.database.jpa.entities.StudentSearchLogEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/22/18
 * Time: 4:41 PM
 */
public interface StudentSearchLogRepository extends CrudRepository<StudentSearchLogEntity, Long>{
}