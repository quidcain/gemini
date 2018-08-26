package com.gemini.commons.database.jpa.respository;

import com.gemini.commons.database.jpa.entities.PreEnrollmentEditLog;
import org.springframework.data.repository.CrudRepository;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/30/18
 * Time: 7:31 PM
 */
public interface PreEnrollmentEditLogRepository extends CrudRepository<PreEnrollmentEditLog, Long>{

    //buscar por id de preenrollment y fecha de editado debe mayor a sometido
}