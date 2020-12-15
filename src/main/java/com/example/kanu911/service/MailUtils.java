package com.example.kanu911.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class MailUtils {

    @Value("${spring.mail.username}")
    private String userName;

    @Value("${spring.mail.password}")
    private String password;

    public void mailSend(MailDto mailDto) throws MessagingException {

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", 465);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session defaultInstance = Session.getDefaultInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

        MimeMessage mimeMessage = new MimeMessage(defaultInstance);
        mimeMessage.setFrom(new InternetAddress(userName));
        mimeMessage.addRecipients(Message.RecipientType.TO, mailDto.getAddress());

        mimeMessage.setSubject(mailDto.getTitle());
        mimeMessage.setText(mailDto.getMessage());
        Transport.send(mimeMessage);
    }
}
