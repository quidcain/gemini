package com.gemini.commons.database.jpa.respository;

import com.gemini.commons.database.jpa.entities.ConfigEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 3/13/18
 * Time: 12:52 AM
 */
public interface ConfigRepository extends CrudRepository<ConfigEntity, Long> {
}