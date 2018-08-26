package com.gemini.services;

import com.gemini.beans.forms.User;
import com.gemini.beans.internal.FailureLogin;
import com.gemini.commons.beans.internal.UserAction;
import com.gemini.beans.requests.FamilyInfoRequest;
import com.gemini.beans.requests.ParentProfileInfoRequest;
import com.gemini.beans.requests.user.ForgotPasswordRequest;
import com.gemini.beans.requests.user.RegisterRequest;
import com.gemini.beans.requests.user.ResetPasswordRequest;
import com.gemini.beans.requests.user.UserActivationRequest;
import com.gemini.commons.beans.types.RequestStatus;
import com.gemini.commons.database.jpa.entities.FailureLoginLogEntity;
import com.gemini.commons.database.jpa.entities.PreEnrollmentRequestEntity;
import com.gemini.commons.database.jpa.entities.UserActionsLogEntity;
import com.gemini.commons.database.jpa.entities.UserEntity;
import com.gemini.commons.database.jpa.respository.FailureLoginLogRepository;
import com.gemini.commons.database.jpa.respository.PreEnrollmentRepository;
import com.gemini.commons.database.jpa.respository.UserActionsLogRepository;
import com.gemini.commons.database.jpa.respository.UserRepository;
import com.gemini.commons.utils.CopyUtils;
import com.gemini.commons.utils.DateUtils;
import com.gemini.commons.utils.Utils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/12/18
 * Time: 6:21 PM
 */
@Service
@Order(SecurityProperties.IGNORED_ORDER)
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserActionsLogRepository userActionsLogRepository;
    @Autowired
    private PreEnrollmentRepository preEnrollmentRepository;
    @Autowired
    private FailureLoginLogRepository failureLoginLogRepository;
    @Autowired
    private CommonService commonService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailService mailService;

    @Transactional
    public boolean existsUserOnRegister(String username) {
        return userRepository.findByEmailAndEnabledTrue(username) != null;
    }

    @Transactional
    public User findUserByUsername(String username) {
        UserEntity entity = userRepository.findByEmail(username);
        User userBean = CopyUtils.convert(entity, User.class);
        Utils.copyLastNames(entity, userBean);
        return userBean;
    }

    @Transactional
    public User findUserByUsernameForAuth(String username) {
        UserEntity entity = userRepository.findByEmailAndEnabledTrue(username);
        if (entity == null)
            return null;
        User userBean = CopyUtils.convert(entity, User.class);
        Utils.copyLastNames(entity, userBean);
        List<PreEnrollmentRequestEntity> preEnrollments = Lists.newArrayList(entity.getRequests());//preEnrollmentRepository.findByUserIdOrderBySubmitDateDesc(entity.getId());
        userBean.setTotalPreEnrollments(preEnrollments.size());
        userBean.setWorkingPreEnrollmentId(null);
        if (preEnrollments.isEmpty())
            userBean.setWorkingPreEnrollmentId(-1L);
        else if (preEnrollments.size() == 1) {
            PreEnrollmentRequestEntity preEnrollment = preEnrollments.get(0);
            if (RequestStatus.ACTIVE.equals(preEnrollment.getRequestStatus()))
                userBean.setWorkingPreEnrollmentId(preEnrollment.getId());
        }

        return userBean;
    }

    @Transactional
    public boolean activationCodeExists(String activationCode) {
        UserEntity entity = userRepository.findByActivationKeyAndActivationKeyExpireDateIsAfterAndIsActiveIndTrue(activationCode, commonService.getCurrentDate());
        return entity != null;
    }

    @Transactional
    public boolean credentialKeyExists(String activationCode) {
        UserEntity entity = userRepository.findByCredLostKeyAndCredLostKeyExpireDateIsAfterAndIsActiveIndTrue(activationCode, commonService.getCurrentDate());
        return entity != null;
    }

    @Transactional
    public boolean activateUser(UserActivationRequest request) {
        boolean activate = false;
        UserEntity entity = userRepository.findByActivationKeyAndActivationKeyExpireDateIsAfterAndIsActiveIndTrue(request.getActivationCode(), commonService.getCurrentDate());
        if (entity != null) {
            String pwd = passwordEncoder.encode(request.getPassword());
            entity.setPassword(pwd);
            entity.setEnabled(true);
            entity.setActivationKey(null);
            entity.setActivationKeyExpireDate(null);
            entity.setActivationDate(commonService.getCurrentDate());
            entity = userRepository.save(entity);
            activate = entity != null;
        }

        return activate;
    }

    @Transactional
    public boolean updateUser(ParentProfileInfoRequest request) {
        UserEntity entity = userRepository.findOne(request.getUserId());
        entity.setRelationType(request.getRelationType());
        entity.setDateOfBirth(request.getDateOfBirth());
        entity.setFirstName(request.getFirstName());
        entity.setMiddleName(request.getMiddleName());
        entity.setLastName(request.getLastName());
        entity.setProfileCompleted(true);
        entity = userRepository.save(entity);
        return entity != null;
    }

    @Deprecated
    public boolean completeProfile(FamilyInfoRequest request) {
        UserEntity entity = userRepository.findOne(request.getUserId());
        entity.setIncome(request.getIncome());
        entity.setEducationLevel(request.getEducationLevel());
        entity.setTotalFamilyMembers(request.getTotalFamilyMembers());
        entity.setProfileCompleted(true);
        entity = userRepository.save(entity);
        return entity != null;
    }

    @Transactional
    public boolean resetPassword(ResetPasswordRequest request) {
        boolean reset = false;
        UserEntity entity = userRepository.findByCredLostKeyAndCredLostKeyExpireDateIsAfterAndIsActiveIndTrue(request.getCredentialLostKey(), commonService.getCurrentDate());
        if (entity != null) {
            String pwd = passwordEncoder.encode(request.getPassword());
            entity.setCredLostKey(null);
            entity.setCredLostKeyExpireDate(null);
            entity.setPassword(pwd);
            entity = userRepository.save(entity);
            reset = entity != null;
        }

        return reset;
    }

    @Transactional
    public boolean cancelResetPassword(String key, HttpServletRequest servletRequest) {
        boolean cancel = false;
        UserEntity entity = userRepository.findByCredLostKeyAndCredLostKeyExpireDateIsAfterAndIsActiveIndTrue(key, commonService.getCurrentDate());
        if (entity != null) {
            entity.setCredLostKey(null);
            entity.setCredLostKeyExpireDate(null);
            entity = userRepository.save(entity);
            cancel = entity != null;
            logUserAction(key, UserAction.CANCEL_RESET_PASSWORD, entity.getId(), servletRequest);
        }
        return cancel;
    }

    @Transactional
    public String createUser(RegisterRequest request, HttpServletRequest servletRequest) {
        String activationKey = Utils.generateKey();
        UserEntity entity = userRepository.findByEmail(request.getEmail());
        if (entity == null)
            entity = new UserEntity();
        entity.setEmail(request.getEmail());
        entity.setActivationKey(activationKey);
        entity = userRepository.save(entity);
        logUserAction(activationKey, UserAction.ACCOUNT_ACTIVATION, entity.getId(), servletRequest);
        return entity != null ? activationKey : null;
    }

    @Transactional
    public boolean saveUserPreEnrollment(Long userId, PreEnrollmentRequestEntity preEnrollment) {
        UserEntity entity = userRepository.findOne(userId);
        entity.getRequests().add(preEnrollment);
        return userRepository.save(entity) != null;
    }

    @Transactional
    public void saveLastLogin(User user) {
        UserEntity entity = userRepository.findOne(user.getId());
        entity.setLastLogin(commonService.getCurrentDate());
        userRepository.save(entity);
    }

    @Transactional
    public void saveFailureLogin(FailureLogin failureLogin) {
        FailureLoginLogEntity loginLogEntity = CopyUtils.convert(failureLogin, FailureLoginLogEntity.class);
        failureLoginLogRepository.save(loginLogEntity);
    }

    @Transactional
    public void saveSentActivationResult(String email, boolean result) {
        UserEntity entity = userRepository.findByEmail(email);
        Date expireActivation = DateUtils.addHours(commonService.getCurrentDate(), commonService.getActivationKeyInHours());
        entity.setActivationCodeSent(result ? commonService.getCurrentDate() : null);
        entity.setActivationKeyExpireDate(result ? expireActivation : null);
        userRepository.save(entity);
    }

    @Transactional
    public boolean processForgetEmailRequest(ForgotPasswordRequest request, HttpServletRequest servletRequest) {
        UserEntity userEntity = userRepository.findByEmailAndEnabledTrue(request.getEmail());
        if (userEntity != null) {
            String key = Utils.generateKey();
            userEntity.setCredLostKey(key);
            userEntity.setCredLostKeyExpireDate(DateUtils.addMinutes(commonService.getCurrentDate(), commonService.getCredentialKeyExpireInMinutes()));
            userRepository.save(userEntity);
            mailService.sendPasswordForgotEmail(request.getEmail(), key);
            logUserAction(key, UserAction.RESET_PASSWORD, userEntity.getId(), servletRequest);
        }
        return userEntity != null;
    }

    public void logUserAction(String key, UserAction action, HttpServletRequest servletRequest) {
        logUserAction(key, action, null, servletRequest);
    }

    @Transactional
    public void logUserAction(String key, UserAction action, Long userId, HttpServletRequest servletRequest) {
        UserActionsLogEntity userActionsLog = new UserActionsLogEntity();
        userActionsLog.setUserId(userId);
        userActionsLog.setKeyString(key);
        userActionsLog.setAction(action);
        userActionsLog.setRemoteIp(servletRequest.getRemoteAddr());
        userActionsLogRepository.save(userActionsLog);
    }
}