package com.gemini.commons.database.jpa.respository;

import com.gemini.commons.database.jpa.entities.AddressEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/20/18
 * Time: 10:19 PM
 */
public interface AddressRepository extends CrudRepository<AddressEntity, Long>{
}