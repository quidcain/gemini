package com.gemini.commons.database.jpa.respository;

import com.gemini.commons.beans.types.RequestStatus;
import com.gemini.commons.database.jpa.entities.PreEnrollmentRequestEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/20/18
 * Time: 10:18 PM
 */
public interface PreEnrollmentRepository extends CrudRepository<PreEnrollmentRequestEntity, Long> {

    @Query(value = "select CASE WHEN COUNT(e) > 0 THEN true ELSE false END from PreEnrollmentRequestEntity e  " +
            "inner join e.student s where s.extStudentNumber = :studentNumber")
    boolean existsByStudentNumber(@Param("studentNumber") Long studentNumber);

    @Query(value = "select e from PreEnrollmentRequestEntity e " +
            " inner join e.student s where s.extStudentNumber = :studentNumber and s.isActiveInd = true ")
    PreEnrollmentRequestEntity findByStudentNumber(@Param("studentNumber") Long studentNumber);

    @Query(value = "select e from UserEntity u " +
            " join u.requests e " +
            " join e.student s where (s.extStudentNumber = :studentNumber or s.ssn = :ssn or upper(u.email) = upper(:email)) and e.isActiveInd = true and s.isActiveInd = true")
    List<PreEnrollmentRequestEntity> findByStudentNumberOrSsnOrEmail(@Param("studentNumber") Long studentNumber, @Param("ssn") String ssn, @Param("email") String email);


    @Query(value = "select e from PreEnrollmentRequestEntity e " +
            " inner join e.student s where s.extStudentNumber = :studentNumber and s.dateOfBirth = :dateOfBirth " +
            " and s.firstName  = :firstName and lastName = :lastName")
    PreEnrollmentRequestEntity findByDateOfBirthAndFirstNameAndLastName(@Param("dateOfBirth") Date dateOfBirth,
                                                                        @Param("firstName") String firstName,
                                                                        @Param("lastName") String lastName);

    @Query(value = "select e from PreEnrollmentRequestEntity e " +
            " inner join e.student s where s.ssn = :ssn and s.isActiveInd = true ")
    PreEnrollmentRequestEntity findBySsn(@Param("ssn") String ssn);

    PreEnrollmentRequestEntity findByIdAndRequestStatusIs(Long id, RequestStatus requestStatus);

    @Query(value = "select e from UserEntity u " +
            " join u.requests e " +
            " where u.id = :userId and e.isActiveInd = true " +
            " order by e.submitDate desc, e.creationDate desc ")
    List<PreEnrollmentRequestEntity> findByUserId(@Param("userId") Long userId);

}