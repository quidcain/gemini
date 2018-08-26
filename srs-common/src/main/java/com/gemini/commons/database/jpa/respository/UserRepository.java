package com.gemini.commons.database.jpa.respository;

import com.gemini.commons.database.jpa.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/12/18
 * Time: 3:20 PM
 */
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    @Query(value = "select u from UserEntity u join u.requests e where e.id = :preEnrollmentId ")
    UserEntity findByPreEnrollmentId(@Param("preEnrollmentId") Long preEnrollmentId);

    @Query(value = "select u from UserEntity u where upper(u.email) = upper(:email) and u.isActiveInd = true")
    UserEntity findByEmail(@Param("email") String email);

    @Query(value = "select u from UserEntity u where upper(u.email) = upper(:email) and u.enabled = true and u.isActiveInd = true")
    UserEntity findByEmailAndEnabledTrue(@Param("email") String email);

    UserEntity findByActivationKeyAndActivationKeyExpireDateIsAfterAndIsActiveIndTrue(String activationKey, Date currentDate);

    UserEntity findByCredLostKeyAndCredLostKeyExpireDateIsAfterAndIsActiveIndTrue(String credLostKey, Date currentDate);

    @Query(value = "select u from UserEntity u where upper(u.email) = upper(:email) and u.isActiveInd = true order by id desc ")
    List<UserEntity> searchByEmail(@Param("email") String email);

    @Query(value = "select e from PreEnrollmentRequestEntity e " +
            " inner join e.student s where s.extStudentNumber = :studentNumber", nativeQuery = true)
    boolean isUserRequest(Long requestId);

}