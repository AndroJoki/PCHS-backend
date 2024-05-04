package com.PCHS.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.PCHS.model.dto.AdminDto;
import com.PCHS.model.dto.ReqRes;
import com.PCHS.model.entity.Admin;
import com.PCHS.repository.AdminRepository;

@Service
public class SuperAdminService {

    @Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AdminRepository adminRepo;

    public Admin addAdmin(AdminDto addRequest){
        ReqRes resp = new ReqRes();
        Admin admin = new Admin();
        try {
            
            admin.setName(addRequest.getName());
            admin.setUsername(addRequest.getUsername());
            admin.setPosition(addRequest.getPosition());
            admin.setAdvisory(addRequest.getAdvisory());
            admin.setEmail(addRequest.getEmail());
            admin.setPassword(passwordEncoder.encode(addRequest.getPassword()));
        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return adminRepo.save(admin);
    }

    public Admin deleteAdmin(Long id) {
        Optional<Admin> optionalAdmin = adminRepo.findById(id);
        if (optionalAdmin.isPresent()){
			adminRepo.delete(optionalAdmin.get());
		}
        return optionalAdmin.get();
			
    }

    public boolean isAdminExistByEmail(String email) {
        return adminRepo.existsByEmail(email);
    }

    public boolean isAdminExistByUsername(String username) {
        return adminRepo.existsByUsername(username);
    }
}
