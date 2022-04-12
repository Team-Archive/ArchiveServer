package com.depromeet.archive.infra.mail;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

import static com.depromeet.archive.infra.mail.MailService.MailTemplate.TEMPORARY_PASSWORD_MAIL;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendTemporaryPassword(final String targetEmail, final String temporaryPassword) {
        var context = new Context(null, Map.of("password", temporaryPassword));
        var body = templateEngine.process(TEMPORARY_PASSWORD_MAIL.templateName, context);
        sendMail(targetEmail, TEMPORARY_PASSWORD_MAIL.subject, body);
    }

    private void sendMail(final String targetEmail, final String subject, final String body) {
        MimeMessagePreparator messagePreparator = mimeMessage -> messageHelper(targetEmail, subject, body, mimeMessage);
        mailSender.send(messagePreparator);
    }

    private void messageHelper(final String targetEmail,
                               final String subject,
                               final String body,
                               MimeMessage mimeMessage) throws MessagingException {
        final var helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom(fromEmail);
        helper.setTo(targetEmail);
        helper.setSubject(subject);
        helper.setText(body, true);
    }

    @Getter
    public enum MailTemplate {
        TEMPORARY_PASSWORD_MAIL("[Archive] 임시 비밀번호 발급 안내", "temporary-password-mail-template");

        private final String subject;
        private final String templateName;

        MailTemplate(String subject, String templateName) {
            this.subject = subject;
            this.templateName = templateName;
        }

    }

}
