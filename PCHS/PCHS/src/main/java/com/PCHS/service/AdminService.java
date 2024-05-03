package com.PCHS.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.PCHS.model.entity.Admin;
import com.PCHS.repository.AdminRepository;

/**
 *
 * @author andre
 */
@Service
public class AdminService{

    @Autowired
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	private AdminRepository adminRepo;

    public AdminService(AdminRepository adminRepo, PasswordEncoder passwordEncoder) {
        this.adminRepo = adminRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Admin> allAdmins() {
        return (List<Admin>) adminRepo.findAll();
    }

    public Admin getAdmin(Long id) {
        Optional<Admin> optionalAdmin = adminRepo.findById(id);
        return optionalAdmin.orElse(null);
    }

    public Admin updateAdmin(Long id, Admin admin) {
        Optional<Admin> optionalAdmin = adminRepo.findById(id);
        if (optionalAdmin.isEmpty()) return null;

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setId(id);
        return adminRepo.save(admin);
    }

	

}
