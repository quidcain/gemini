package com.gemini.admin.database.jpa.repositories;

import com.gemini.admin.database.jpa.entities.OccupationalApprovalEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 6/4/18
 * Time: 12:00 AM
 */
public interface OccupationalApprovalRepository extends CrudRepository<OccupationalApprovalEntity, Long> {

    OccupationalApprovalEntity findByPreEnrollmentId(Long preEnrollmentId);

    OccupationalApprovalEntity findByPreEnrollmentIdAndSchoolId(Long preEnrollmentId, Long schoolId);
}