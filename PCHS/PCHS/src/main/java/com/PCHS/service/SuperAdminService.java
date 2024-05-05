package com.PCHS.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.PCHS.model.dto.AdminDto;
import com.PCHS.model.dto.ReqRes;
import com.PCHS.model.entity.Admin;
import com.PCHS.model.entity.SuperAdmin;
import com.PCHS.repository.AdminRepository;
import com.PCHS.repository.SuperAdminRepository;

@Service
public class SuperAdminService {

    @Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AdminRepository adminRepo;

    @Autowired
    private SuperAdminRepository superAdminRepo;

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

    public SuperAdmin getSuperAdminByUsername(String username) {
        Optional<SuperAdmin> optionalSuperAdmin = superAdminRepo.findByUsername(username);
        return optionalSuperAdmin.orElse(null);
    }

    public SuperAdmin updateSuperAdmin(String username, Boolean passwordChanged, SuperAdmin superAdmin) {
        Optional<SuperAdmin> optionalSuperAdmin = superAdminRepo.findByUsername(username);
        if (optionalSuperAdmin.isEmpty()) return null;

        if(passwordChanged) superAdmin.setPassword(passwordEncoder.encode(superAdmin.getPassword()));
        superAdmin.setId(optionalSuperAdmin.get().getId());
        return superAdminRepo.save(superAdmin);
    }
}
