package com.PCHS.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.PCHS.model.entity.SuperAdmin;
import com.PCHS.repository.SuperAdminRepository;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private SuperAdminRepository superAdminRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... strings) throws Exception {

        if(superAdminRepo.existsById(1l) == false) {
            SuperAdmin superAdmin = new SuperAdmin();
            superAdmin.setUsername("PCHSsuperadmin");
            superAdmin.setPassword(passwordEncoder.encode("PCHS123"));
            superAdminRepo.save(superAdmin);
        }
    }
}

