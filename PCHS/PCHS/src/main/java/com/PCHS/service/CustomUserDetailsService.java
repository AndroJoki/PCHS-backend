package com.PCHS.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.PCHS.repository.AdminRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
	 private AdminRepository adminRepo;

	/*@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin admin = adminRepo.findAdminByEmail(username);
		if (admin == null) {
			throw new UsernameNotFoundException("User not found");
		}
		
		return User
        .withUsername(admin.getUsername())
        .password(admin.getPassword())
        .authorities(new SimpleGrantedAuthority("admin"))
        .build();
	}*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepo.findByUsername(username).orElseThrow();
    }
}
