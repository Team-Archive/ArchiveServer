package site.archive.infra.mail;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Async("mailExecutor")
    public void sendTemporaryPassword(final String targetEmail, final String temporaryPassword) {
        var context = new Context(null, Map.of("password", temporaryPassword));
        var body = templateEngine.process(MailTemplate.TEMPORARY_PASSWORD_MAIL.templateName, context);
        sendMail(targetEmail, MailTemplate.TEMPORARY_PASSWORD_MAIL.subject, body);
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
        TEMPORARY_PASSWORD_MAIL("[Archive] ??????????????? ???????????? Archive ?????? ?????? ?????????????????????.", "temporary-password-mail-template");

        private final String subject;
        private final String templateName;

        MailTemplate(String subject, String templateName) {
            this.subject = subject;
            this.templateName = templateName;
        }

    }

}
