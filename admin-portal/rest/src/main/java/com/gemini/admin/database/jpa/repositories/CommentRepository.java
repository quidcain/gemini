package com.gemini.admin.database.jpa.repositories;

import com.gemini.admin.database.jpa.entities.CommentEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 4/24/18
 * Time: 5:41 AM
 */
public interface CommentRepository extends CrudRepository<CommentEntity, Long> {

    List<CommentEntity> getAllByReferenceIdOrderByCreationDate(Long referenceId);
}