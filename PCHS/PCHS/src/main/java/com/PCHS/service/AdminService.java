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
public class AdminService implements IAdminService {

    @Autowired
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	private AdminRepository adminRepo;

    public AdminService(AdminRepository adminRepo, PasswordEncoder passwordEncoder) {
        this.adminRepo = adminRepo;
        this.passwordEncoder = passwordEncoder;
    }


	@Override
    public List<Admin> allAdmins() {
        return (List<Admin>) adminRepo.findAll();
    }

	@Override
    public Admin getAdmin(Long id) {
        Optional<Admin> optionalAdmin = adminRepo.findById(id);
        return optionalAdmin.orElse(null);
    }

	/*@Override
	public Admin addAdmin(RegisterDto registerDto) {
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());

		Admin admin = new Admin(registerDto.getEmail(), registerDto.getName(), registerDto.getPassword());

        admin.setPassword(encodedPassword);

		return adminRepo.save(admin);
	}*/


	@Override
    public Admin updateAdmin(Long id, Admin admin) {
        Optional<Admin> optionalAdmin = adminRepo.findById(id);
        if (optionalAdmin.isEmpty()) return null;

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setId(id);
        return adminRepo.save(admin);
    }


	@Override
	public void deleteAdmin(Long id) {
        Optional<Admin> optionalAdmin = adminRepo.findById(id);
        if (optionalAdmin.isPresent()){
			adminRepo.delete(optionalAdmin.get());
		}
			
    }

}
