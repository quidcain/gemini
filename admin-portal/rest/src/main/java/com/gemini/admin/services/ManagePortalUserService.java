package com.gemini.admin.services;

import com.gemini.admin.beans.AdminUser;
import com.gemini.admin.beans.forms.EditUserResult;
import com.gemini.admin.beans.forms.UserSearchResult;
import com.gemini.admin.beans.requests.ActivateUserRequest;
import com.gemini.admin.beans.requests.PortalUserSearchRequest;
import com.gemini.admin.database.dao.PortalUserDao;
import com.gemini.admin.database.jpa.entities.CommentEntity;
import com.gemini.admin.database.jpa.repositories.CommentRepository;
import com.gemini.commons.database.jpa.entities.PreEnrollmentRequestEntity;
import com.gemini.commons.database.jpa.entities.UserEntity;
import com.gemini.commons.database.jpa.respository.PreEnrollmentRepository;
import com.gemini.commons.database.jpa.respository.UserRepository;
import com.gemini.commons.utils.Utils;
import com.gemini.commons.utils.ValidationUtils;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/7/18
 * Time: 10:19 AM
 */
@Service
public class ManagePortalUserService {

    static Logger logger = LoggerFactory.getLogger(ManagePortalUserService.class.getName());


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PreEnrollmentRepository preEnrollmentRepository;

    @Autowired
    private PortalUserDao portalUserDao;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    @Qualifier("portalEncoder")
    private PasswordEncoder portalEncoder;

    @Transactional
    public List<UserSearchResult> searchUser(PortalUserSearchRequest request, AdminUser adminUser) {
        List<UserEntity> users;
        if (ValidationUtils.valid(request.getEmail()))
            users = userRepository.searchByEmail(request.getEmail());
        else
            users = Lists.newArrayList(userRepository.findOne(request.getUserId()));

        if (users.size() > 2)
            return Collections.emptyList();

        if (users.size() == 2) {
            UserEntity user1 = users.get(0);
            UserEntity user2 = users.get(1);
            UserEntity prevailsAccount;
            UserEntity inactiveAccount;
            List<PreEnrollmentRequestEntity> prevailsEnrollments = new ArrayList<>();
            List<PreEnrollmentRequestEntity> inactiveEnrollments = new ArrayList<>();
            if (isEnable(user1) && !isEnable(user2)) {
                prevailsAccount = user1;
                inactiveAccount = user2;
                logger.info("rule 1 to merge -> account one is enabled and account two is not enabled");
            } else if (isEnable(user2) && !isEnable(user1)) {
                prevailsAccount = user2;
                inactiveAccount = user1;
                logger.info("rule 2 to merge -> account two is enabled and account one is not enabled");
            } else if (isEnable(user1) && isEnable(user2) && isProfileCompleted(user1) && !isProfileCompleted(user2)) {
                prevailsAccount = user1;
                inactiveAccount = user2;
                logger.info("rule 3 to merge -> account one is profile completed and account two is not profile completed");

            } else if (isEnable(user1) && isEnable(user2) && isProfileCompleted(user2) && !isProfileCompleted(user1)) {
                prevailsAccount = user2;
                inactiveAccount = user1;
                logger.info("rule 4 to merge -> account two is profile completed and account one is not profile completed");
            } else {
                List<PreEnrollmentRequestEntity> prevailsEnrollmentsTmp;
                List<PreEnrollmentRequestEntity> inactiveEnrollmentsTmp;
                if (user1.getId() > user2.getId()) {
                    prevailsAccount = user1;
                    inactiveAccount = user2;
                    prevailsEnrollmentsTmp = preEnrollmentRepository.findByUserId(prevailsAccount.getId());
                    inactiveEnrollmentsTmp = preEnrollmentRepository.findByUserId(inactiveAccount.getId());

                    if (prevailsEnrollmentsTmp.isEmpty() && !inactiveEnrollmentsTmp.isEmpty()) {
                        logger.info("rule 6 to merge -> account one and two is profile completed, but account two has enrollents and account one doesn't have any enrollments");
                        prevailsEnrollments = inactiveEnrollmentsTmp;
                        inactiveEnrollments = prevailsEnrollmentsTmp;
                        prevailsAccount = user2;
                        inactiveAccount = user1;
                    } else {
                        logger.info("rule 5 to merge -> account one and two is profile completed, we take the max id");
                        prevailsEnrollments = prevailsEnrollmentsTmp;
                        inactiveEnrollments = inactiveEnrollmentsTmp;
                    }

                } else {
                    prevailsAccount = user2;
                    inactiveAccount = user1;
                }
            }


            if (!inactiveEnrollments.isEmpty())
                portalUserDao.moveRequestsToUserId(inactiveAccount.getId(), prevailsAccount.getId());

            inactiveAccount.setRevisionDate(new Date());
            inactiveAccount.setActiveInd(false);
            saveComment(String.format("MERGED BY SYSTEM user id %s now is %s", inactiveAccount.getId(), prevailsAccount.getId()), prevailsAccount.getId(), adminUser);
            userRepository.save(inactiveAccount);

            users = Lists.newArrayList(prevailsAccount);
            //reglas para el merged:
            /*
             * 1) se inactiva la cuenta que no se posea matricula
             * 2) si ambas cuentas poseen matriculas se debe unir todas estas en una cuenta
             * 3) precede el id mayor
             *
             * */
        }

        List<UserSearchResult> result = FluentIterable
                .from(users)
                .transform(new Function<UserEntity, UserSearchResult>() {
                    @Override
                    public UserSearchResult apply(UserEntity userEntity) {
                        UserSearchResult r = new UserSearchResult();
                        r.setUserId(userEntity.getId());
                        r.setEmail(userEntity.getEmail());
                        r.setCreated(userEntity.getCreationDate());
                        r.setEnabled(userEntity.isEnabled());
                        return r;
                    }
                })
                .toList();

        return users.isEmpty() ? Collections.<UserSearchResult>emptyList() : result;
    }


    @Transactional
    public EditUserResult getUser(final Long userId) {
        UserEntity userEntity = userRepository.findOne(userId);

        Function<UserEntity, EditUserResult> toResult = new Function<UserEntity, EditUserResult>() {
            @Override
            public EditUserResult apply(UserEntity userEntity) {
                EditUserResult result = new EditUserResult();
                result.setUserId(userId);
                result.setEmail(userEntity.getEmail());
                result.setDateOfBirth(userEntity.getDateOfBirth());
                String name = Utils.toFullName(userEntity.getFirstName(), userEntity.getMiddleName(), userEntity.getLastName());
                result.setFatherName(name);
                result.setLastLogin(userEntity.getLastLogin());
                result.setCreationDate(userEntity.getCreationDate());
                result.setRevisionDate(userEntity.getRevisionDate());
                result.setEnabled(userEntity.isEnabled());
                result.setProfileCompleted(userEntity.isProfileCompleted());
                return result;
            }
        };
        return toResult.apply(userEntity);

    }

    @Transactional
    public boolean saveUser(ActivateUserRequest request, AdminUser adminUser) {
        UserEntity user = userRepository.findOne(request.getUserId());
        if (StringUtils.hasText(request.getPassword())) {
            String encryptedPwd = portalEncoder.encode(request.getPassword());
            user.setPassword(encryptedPwd);
        }
        user.setCredLostKey(null);
        user.setCredLostKeyExpireDate(null);
        user.setActivationKey(null);
        user.setActivationKeyExpireDate(null);
        user.setActivationDate(new Date());
        user.setEnabled(StringUtils.hasText(user.getPassword()) || user.isEnabled());
        user.setRevisionDate(new Date());
        user = userRepository.save(user);

        saveComment(request.getComment(), user.getId(), adminUser);
        return user != null;
    }


    private void saveComment(String comment, Long referenceId, AdminUser user) {
        if (StringUtils.hasText(comment)) {
            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setReferenceId(referenceId);
            commentEntity.setUserId(user.getUserId());
            commentEntity.setCommentText(comment);
            commentRepository.save(commentEntity);
        }
    }

    private boolean isEnable(UserEntity user) {
        return user.isEnabled();
    }

    private boolean isProfileCompleted(UserEntity user) {
        return user.isProfileCompleted();

    }

}