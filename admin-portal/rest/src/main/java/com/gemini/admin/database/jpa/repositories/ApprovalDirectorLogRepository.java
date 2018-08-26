package com.gemini.admin.database.jpa.repositories;

import com.gemini.admin.database.jpa.entities.ApprovalDirectorLogEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/31/18
 * Time: 11:30 PM
 */
public interface ApprovalDirectorLogRepository extends CrudRepository<ApprovalDirectorLogEntity, Long> {
}