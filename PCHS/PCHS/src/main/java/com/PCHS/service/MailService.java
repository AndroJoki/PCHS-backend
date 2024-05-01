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
    public void sendCodeMail(String code) {

        String targetEmail = "andrewjoquino@gmail.com";

        String verificationMessage = String.format(
                """
                Here's your OTP
                Hi there,
                Your OTP (One-time PIN) is %s
                
                Please enter this code within 5 minutes. 
                Remember, DO NOT share the code with anyone.
                """,
                code
        );

        sendMail(
                targetEmail,
                "Admin Register Verification",
                verificationMessage
        );
    }
}
