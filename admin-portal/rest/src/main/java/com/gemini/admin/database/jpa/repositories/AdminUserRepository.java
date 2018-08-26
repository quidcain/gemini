package com.gemini.admin.database.jpa.repositories;

import com.gemini.admin.database.jpa.entities.AdminUserEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/8/18
 * Time: 4:19 PM
 */
public interface AdminUserRepository extends CrudRepository<AdminUserEntity, Long> {

    AdminUserEntity findByUserId(Long userId);

    AdminUserEntity findByUsername(String username);

}
