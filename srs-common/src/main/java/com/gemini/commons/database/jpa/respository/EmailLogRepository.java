package com.gemini.commons.database.jpa.respository;

import com.gemini.commons.database.jpa.entities.EmailLogEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/12/18
 * Time: 4:33 PM
 */
public interface EmailLogRepository extends CrudRepository<EmailLogEntity, Long>{
}