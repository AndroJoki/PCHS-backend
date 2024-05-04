package com.PCHS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    public void sendMail(String mail, String subject, String body){
        SimpleMailMessage mail_msg = new SimpleMailMessage();
        mail_msg.setFrom(fromMail);
        mail_msg.setSubject(subject);
        mail_msg.setText(body);
        mail_msg.setTo(mail);

        mailSender.send(mail_msg);
    }

    @Async
    public void sendContactUsMail(String name, String email, String subject, String msg) {

        String targetEmail = "andrewjoquino@gmail.com";

        String msgBody = String.format(
                """
                From %s,
                %s

                %s
                """,
                name, email, msg
        );

        sendMail(
                targetEmail,
                subject,
                msgBody
        );
    }
}
