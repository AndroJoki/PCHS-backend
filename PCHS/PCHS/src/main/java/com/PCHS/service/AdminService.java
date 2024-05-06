package com.PCHS.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.PCHS.model.entity.Admin;
import com.PCHS.model.entity.Student;
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

    public Page<Admin> adminsWithPage(int offset){
        Page<Admin> admins = adminRepo.findAll(PageRequest.of(offset, 10));
        return admins;
    }

    public Admin getAdmin(Long id) {
        Optional<Admin> optionalAdmin = adminRepo.findById(id);
        return optionalAdmin.orElse(null);
    }

    public Admin getAdminByUsername(String username) {
        Optional<Admin> optionalAdmin = adminRepo.findByUsername(username);
        return optionalAdmin.orElse(null);
    }

    public Admin updateAdmin(Long id, Boolean passwordChanged, Admin admin) {
        Optional<Admin> optionalAdmin = adminRepo.findById(id);
        if (optionalAdmin.isEmpty()) return null;

        if(passwordChanged) admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setId(id);
        return adminRepo.save(admin);
    }

	

}
