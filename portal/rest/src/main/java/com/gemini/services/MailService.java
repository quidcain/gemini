package com.gemini.services;

import com.gemini.beans.forms.User;
import com.gemini.beans.internal.EmailSchool;
import com.gemini.commons.beans.internal.UserAction;
import com.gemini.beans.requests.enrollment.PreEnrollmentSubmitRequest;
import com.gemini.beans.requests.user.RegisterRequest;
import com.gemini.commons.beans.forms.PreEnrollmentBean;
import com.gemini.commons.beans.integration.SchoolResponse;
import com.gemini.commons.beans.types.EmailType;
import com.gemini.commons.beans.types.RequestStatus;
import com.gemini.commons.database.jpa.entities.EmailLogEntity;
import com.gemini.commons.database.jpa.respository.EmailLogRepository;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/9/18
 * Time: 1:51 PM
 */
@Service
public class MailService {
    static Logger logger = LoggerFactory.getLogger(MailService.class.getName());

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;
    @Autowired
    private PreEnrollmentService preEnrollmentService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailLogRepository emailLogRepository;
    @Value("${email.from}")
    private String fromEmail;
    @Value("${website.url}${website.context-path}")
    private String publicUrl;

    //public methods
    public boolean sendRegisterEmail(RegisterRequest request, String activationCode) {
        SimpleMailMessage message = accountRegisterMail(request, toUrl(UserAction.ACCOUNT_ACTIVATION, activationCode));
        User user = userService.findUserByUsername(request.getEmail());
        return send(message, EmailType.ACCOUNT_ACTIVATION, user.getId());
    }

    public boolean sendPreEnrollmentSubmitEmail(User user, PreEnrollmentSubmitRequest request) {
        SimpleMailMessage message = preEnrollmentSubmit(user, request);
        return send(message, EmailType.PRE_ENROLLMENT_SUBMITTED, user.getId(), request.getRequestId());
    }

    public boolean sendPreEnrollmentWithAlternateSchoolsSubmitEmail(User user, PreEnrollmentSubmitRequest request) {
        SimpleMailMessage message = preEnrollmentWithAlternateSchoolsSubmit(user, request);
        return send(message, EmailType.PRE_ENROLLMENT_SUBMITTED, user.getId(), request.getRequestId());
    }

    public boolean sendPreEnrollmentWithVocationalSchoolsSubmitEmail(User user, PreEnrollmentSubmitRequest request) {
        SimpleMailMessage message = preEnrollmentWithAlternateSchoolsSubmit(user, request);
        return send(message, EmailType.PRE_ENROLLMENT_SUBMITTED, user.getId(), request.getRequestId());
    }

    public boolean sendPasswordForgotEmail(String email, String key) {
        SimpleMailMessage message = forgetPasswordMail(email, toUrl(UserAction.RESET_PASSWORD, key), toUrl(UserAction.CANCEL_RESET_PASSWORD, key));
        User user = userService.findUserByUsername(email);
        return send(message, EmailType.RESET_PASSWORD, user.getId());
    }

    private String toUrl(UserAction link, String param) {
        return String.format("%s".concat(link.getPath()), publicUrl, param);
    }

    private <T extends Object> String composeBody(Map<String, T> params, String templateName) {
        Context ctx = new Context();
        for (Map.Entry<String, T> entry : params.entrySet()) {
            ctx.setVariable(entry.getKey(), entry.getValue());
        }
        Iterables.cycle(params.entrySet()).iterator().hasNext();
        return templateEngine.process(templateName, ctx);
    }

    private SimpleMailMessage accountRegisterMail(RegisterRequest request, String link) {
        SimpleMailMessage registerMail = new SimpleMailMessage();
        registerMail.setFrom(fromEmail);
        registerMail.setTo(request.getEmail());
        registerMail.setSubject("Registro en Linea - Activar Cuenta");
        Map<String, String> params = ImmutableMap.of("link", link);
        registerMail.setText(composeBody(params, "emails/registration"));
        return registerMail;
    }

    private SimpleMailMessage preEnrollmentSubmit(User user, PreEnrollmentSubmitRequest enrollmentBean) {
        PreEnrollmentBean preEnrollmentBean = preEnrollmentService.findById(enrollmentBean.getRequestId());
        SimpleMailMessage preEnrollmentMail = new SimpleMailMessage();
        preEnrollmentMail.setFrom(fromEmail);
        preEnrollmentMail.setTo(user.getEmail());
        preEnrollmentMail.setSubject("Matrícula Recibida");

        String conditionalText = RequestStatus.APPROVED.equals(preEnrollmentBean.getRequestStatus())
                ? " confirmando el espacio "
                : " sometiendo la solicitud ";


        SchoolResponse school = preEnrollmentService.findSchoolByPreEnrollmentIdConfirmed(enrollmentBean.getRequestId());
        Map<String, String> params = ImmutableMap.of(
                "studentName", preEnrollmentBean.getStudent().getFullName(),
                "schoolName", school.getSchoolName(),
                "schoolAddress", school.getAddress().getAddressFormatted(),
                "conditionalText", conditionalText

        );
        preEnrollmentMail.setText(composeBody(params, "emails/pre-enrollment-submit"));
        return preEnrollmentMail;
    }

    private SimpleMailMessage preEnrollmentWithAlternateSchoolsSubmit(User user, PreEnrollmentSubmitRequest enrollmentBean) {
        PreEnrollmentBean preEnrollmentBean = preEnrollmentService.findById(enrollmentBean.getRequestId());
        SimpleMailMessage preEnrollmentMail = new SimpleMailMessage();
        preEnrollmentMail.setFrom(fromEmail);
        preEnrollmentMail.setTo(user.getEmail());
        preEnrollmentMail.setSubject("Matrícula Recibida");

        List<EmailSchool> schools = preEnrollmentService.findEmailsByPreEnrollmentId(enrollmentBean.getRequestId());
        Map<String, Object> params = ImmutableMap.of(
                "studentName", preEnrollmentBean.getStudent().getFullName(),
                "schools", schools
        );
        preEnrollmentMail.setText(composeBody(params, "emails/pre-enrollment-alternate-schools-submit"));
        return preEnrollmentMail;
    }

    private SimpleMailMessage forgetPasswordMail(String to, String resetLink, String cancelResetLink) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(fromEmail);
        mail.setTo(to);
        mail.setSubject("Registro en Linea - Olvido Contraseña");
        Map<String, String> params = ImmutableMap.of(
                "resetLink", resetLink
                , "cancelResetLink", cancelResetLink
                , "credentialKeyExpireInMinutes", String.valueOf(commonService.getCredentialKeyExpireInMinutes())
        );

        mail.setText(composeBody(params, "emails/forgot-password"));
        return mail;
    }

    boolean send(SimpleMailMessage message, EmailType type, Long userId) {
        return send(message, type, userId, null);
    }

    private boolean send(SimpleMailMessage message, EmailType type, Long userId, Long preEnrollmentId) {
        boolean sent = true;
        EmailLogEntity log = null;
        try {
            final MimeMessageHelper helper =
                    new MimeMessageHelper(mailSender.createMimeMessage(), true, "UTF-8"); // true = multipart
            helper.setSubject(message.getSubject());
            helper.setFrom(message.getFrom());
            helper.setTo(message.getTo());
            helper.setText(message.getText(), true);
            log = saveLog(type, userId, preEnrollmentId);
            mailSender.send(helper.getMimeMessage());
            sent = true;
            saveSentDate(log);
        } catch (Exception e) {
//            //todo: fran create failover process to manage this
            saveTries(log);
            logger.error("Error while sending email", e);
        }
        return sent;

    }

    private EmailLogEntity saveLog(EmailType type, Long userId, Long preEnrollmentId) {
        EmailLogEntity emailLog = new EmailLogEntity();
        emailLog.setType(type);
        emailLog.setUserId(userId);
        emailLog.setPreEnrollmentId(preEnrollmentId);
        emailLog = emailLogRepository.save(emailLog);
        return emailLog;
    }

    private void saveSentDate(EmailLogEntity entity) {
        entity.setSentDate(commonService.getCurrentDate());
        emailLogRepository.save(entity);
    }

    private void saveTries(EmailLogEntity entity) {
        entity.setTries(entity.getTries() + 1);
        emailLogRepository.save(entity);
    }


}