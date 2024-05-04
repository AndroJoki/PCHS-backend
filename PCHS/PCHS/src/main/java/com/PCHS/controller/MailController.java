package com.PCHS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PCHS.model.dto.ContactUsDto;
import com.PCHS.service.MailService;

@RestController
@RequestMapping("/api/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("send/contactus")
    public String fromContactUs(@RequestBody ContactUsDto mailStructure) throws Exception {
        mailService.sendContactUsMail(mailStructure.getName(), mailStructure.getEmail(), 
                                    mailStructure.getSubject(), mailStructure.getMessage());
                                    
        return "Successfully sent the mail!";
    }
}
