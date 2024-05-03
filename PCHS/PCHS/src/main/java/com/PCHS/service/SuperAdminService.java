package com.PCHS.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.PCHS.model.dto.ReqRes;
import com.PCHS.model.entity.Admin;
import com.PCHS.repository.AdminRepository;

@Service
public class SuperAdminService {

    @Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AdminRepository adminRepo;

    public ReqRes addAdmin(ReqRes addRequest){
        ReqRes resp = new ReqRes();
        try {
            Admin admin = new Admin();
            admin.setName(addRequest.getName());
            admin.setUsername(addRequest.getUsername());
            admin.setPosition(addRequest.getPosition());
            admin.setAdvisory(addRequest.getAdvisory());
            admin.setEmail(addRequest.getEmail());
            admin.setPassword(passwordEncoder.encode(addRequest.getPassword()));
            Admin adminResult = adminRepo.save(admin);
            if (adminResult != null && adminResult.getId()>0) {
                resp.setAdmins(adminResult);
                resp.setMessage("Admin Registered Successfully");
                resp.setStatusCode(200);
            }
        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public void deleteAdmin(Long id) {
        Optional<Admin> optionalAdmin = adminRepo.findById(id);
        if (optionalAdmin.isPresent()){
			adminRepo.delete(optionalAdmin.get());
		}
			
    }
}
