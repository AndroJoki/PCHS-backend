package com.PCHS.service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PCHS.model.entity.Verification;
import com.PCHS.repository.VerificationRepository;

@Service
public class VerificationService {

    @Autowired
    private MailService mailService;

    @Autowired
    private VerificationRepository verificationRepo;


    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            result.append(characters.charAt(index));
        }
        return result.toString();
    }

    public void sendRegisterCode() {

        Verification verification = verificationRepo.findById(1l).orElseThrow();

        // Generate code
        String code = generateRandomString(5);

        // Update code in database
        verification.setOtp_code(code);
        verification.setId(1L);
        verification.setOtpGeneratedTime(LocalDateTime.now());
        verificationRepo.save(verification);

        // Send email
        mailService.sendCodeMail(code);
    }

    public boolean verifyRegisterCode(String code) {

        Verification verifyRegister = verificationRepo.findById(1l).orElseThrow();

        System.out.println(code);
        System.out.println(verifyRegister.getOtp_code());
        System.out.println(verifyRegister.getOtp_code().equals(code));
        // Remove verification entry if OTP time expired
        if(Duration.between(verifyRegister.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds() > (5 * 60)){
            verifyRegister.setId(1l);
            verifyRegister.setOtp_code(null);
            verifyRegister.setOtpGeneratedTime(null);
            verificationRepo.save(verifyRegister);
            return false;       
        }

        // Remove verification entry if successful
        if (verifyRegister.getOtp_code().equals(code)){
            verifyRegister.setId(1l);
            verifyRegister.setOtp_code(null);
            verifyRegister.setOtpGeneratedTime(null);
            verificationRepo.save(verifyRegister);
            return true;   
        }
    
        return false;
    }


    public boolean verifyAdminCode(String code) {
        return true;
    }

}
