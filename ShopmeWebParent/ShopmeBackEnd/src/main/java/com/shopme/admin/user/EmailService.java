package com.shopme.admin.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service("emailService")
public class EmailService {
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	@Autowired
    private TemplateEngine templateEngine;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
    }
    
    public void sendConfirmationEmail(String to, String confirmationUrl) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Konfirmasi Pendaftaran");

        Context context = new Context();
        context.setVariable("confirmationUrl", confirmationUrl);

        String htmlContent = templateEngine.process("emails/confirmation-email-template", context);
        helper.setText(htmlContent, true);

        javaMailSender.send(message);
    }
}
