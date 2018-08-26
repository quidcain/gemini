package com.gemini.codes;

import com.gemini.codes.beans.StudentResult;
import com.gemini.codes.services.SIERegistroService;
import com.gemini.commons.utils.GradeLevelUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 5/22/18
 * Time: 9:28 AM
 */
@SpringBootApplication
public class SendSIEResultMail implements CommandLineRunner {
    static Logger logger = LoggerFactory.getLogger(SendSIEResultMail.class.getName());

    @Autowired
    JavaMailSender mailSender;

    final String fromEmail = "DE-SIE@de.pr.gov";

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private SIERegistroService sieRegistroService;

    public static void main(String[] args) {
        SpringApplication.run(SendSIEResultMail.class, args);
    }

    @Override
    public void run(String... strings) {
        sentErrorConfirmationMail();
    }

    private void sentConfirmationMail() {
        List<StudentResult> results = sieRegistroService.retrieve();
        int counter = 1;
        int total = results.size();
        for (StudentResult studentResult : results) {
            System.out.print(String.format("\rEmails sent %d out %s", counter++, total));
            SimpleMailMessage resultMail = new SimpleMailMessage();
            resultMail.setFrom(fromEmail);
            resultMail.setTo(studentResult.getEmail());
            String gradeLevelDescription = GradeLevelUtils.getDescriptionByGradeLevel(studentResult.getGradeLevel());
            resultMail.setSubject("SIE-Registro - Solicitud Evaluada");
            Map<String, String> params = ImmutableMap.of("studentName", studentResult.getStudentName(),
                    "schoolName", studentResult.getSchoolName(),
                    "extSchoolNumber", studentResult.getExtSchoolNumber(),
                    "schoolAddress", studentResult.getSchoolAddress(),
                    "gradeLevelDescription", gradeLevelDescription);
            resultMail.setText(composeBody(params, "de-response"));
            send(resultMail, studentResult);
        }
    }

    private void sentErrorConfirmationMail() {
        List<StudentResult> results = sieRegistroService.retrieveSentByError();
        int counter = 1;
        int total = results.size();
        for (StudentResult studentResult : results) {
            System.out.print(String.format("\rEmails sent %d out %s", counter++, total));
            SimpleMailMessage resultMail = new SimpleMailMessage();
            resultMail.setFrom(fromEmail);
            resultMail.setTo(studentResult.getEmail());
            resultMail.setSubject("SIE-Registro - Solicitud Evaluada");
//            String gradeLevelDescription = GradeLevelUtils.getDescriptionByGradeLevel(studentResult.getGradeLevel());
            Map<String, String> params = ImmutableMap.of("studentName", studentResult.getStudentName());
//                    "schoolName", studentResult.getSchoolName(),
//                    "extSchoolNumber", studentResult.getExtSchoolNumber(),
//                    "schoolAddress", studentResult.getSchoolAddress(),
//                    "gradeLevelDescription", String.format("%s - %s", studentResult.getGradeLevel(), gradeLevelDescription));
            resultMail.setText(composeBody(params, "de-approve-by-error"));
            sendError(resultMail, studentResult);
        }
    }

    private boolean send(SimpleMailMessage message, StudentResult studentResult) {
        boolean sent = false;
        try {
            final MimeMessageHelper helper =
                    new MimeMessageHelper(mailSender.createMimeMessage(), true, "UTF-8"); // true = multipart
            helper.setSubject(message.getSubject());
            helper.setFrom(message.getFrom());
            helper.setTo(message.getTo());
            helper.setText(message.getText(), true);
            mailSender.send(helper.getMimeMessage());
            sent = true;
            sieRegistroService.maskAsSent(studentResult.getId());
        } catch (Exception e) {
            sieRegistroService.maskTry(studentResult.getId());
            logger.error("Error while sending email", e);
        }
        return sent;
    }

    private boolean sendError(SimpleMailMessage message, StudentResult studentResult) {
        boolean sent = false;
        try {
            final MimeMessageHelper helper =
                    new MimeMessageHelper(mailSender.createMimeMessage(), true, "UTF-8"); // true = multipart
            helper.setSubject(message.getSubject());
            helper.setFrom(message.getFrom());
            helper.setTo(message.getTo());
            helper.setText(message.getText(), true);
            mailSender.send(helper.getMimeMessage());
            sent = true;
            sieRegistroService.maskErrorAsSent(studentResult.getId());
        } catch (Exception e) {
            logger.error("Error while sending email", e);
        }
        return sent;
    }

    private <T extends Object> String composeBody(Map<String, T> params, String templateName) {
        Context ctx = new Context();
        for (Map.Entry<String, T> entry : params.entrySet()) {
            ctx.setVariable(entry.getKey(), entry.getValue());
        }
        Iterables.cycle(params.entrySet()).iterator().hasNext();
        return templateEngine.process(templateName, ctx);
    }
}