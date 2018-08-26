package com.gemini.commons.database.jpa.respository;

import com.gemini.commons.database.jpa.entities.UserActionsLogEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/16/18
 * Time: 2:27 PM
 */
public interface UserActionsLogRepository extends CrudRepository<UserActionsLogEntity, Long> {
}