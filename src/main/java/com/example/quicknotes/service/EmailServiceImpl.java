package com.example.quicknotes.service;


import com.example.quicknotes.domain.entity.User;
import com.example.quicknotes.service.interfaces.ConfirmationService;
import com.example.quicknotes.service.interfaces.EmailService;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender sender;
    private Configuration mailConfig;
    private ConfirmationService confirmationService;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public EmailServiceImpl(JavaMailSender sender, Configuration mailConfig, ConfirmationService confirmationService) {
        this.sender = sender;
        this.mailConfig = mailConfig;
        this.confirmationService = confirmationService;

        mailConfig.setDefaultEncoding("UTF-8");
        mailConfig.setTemplateLoader(new ClassTemplateLoader(EmailServiceImpl.class, "/mail/"));
    }

    @Override
    public void sendConfirmationEmail(User user) {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        String text = generateConfirmationEmail(user);

        try{
            helper.setFrom(senderEmail);
            helper.setTo(user.getEmail());
            helper.setSubject("Registration");
            helper.setText(text, true);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        sender.send(message);
    }

    private String generateConfirmationEmail(User user){
        try {
            Template template = mailConfig.getTemplate("confirm_registration_mail.ftlh");
            String code = confirmationService.generateConfirmationCode(user);

            Map<String, Object> model = new HashMap<>();
            model.put("name", user.getUsername());

            //http://localhost:8080/register?code=vedfsdfds-fdsfd2ds-gbfs84dgd-sd885dfsf
            String url = "http://localhost:8080/register?code=" + code;
            model.put("link", url);

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}