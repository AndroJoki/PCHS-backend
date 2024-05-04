package com.PCHS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PCHS.exceptions.MissingException;
import com.PCHS.model.dto.LoginDto;
import com.PCHS.model.dto.ReqRes;
import com.PCHS.repository.AdminRepository;
import com.PCHS.repository.SuperAdminRepository;
import com.PCHS.service.AuthService;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private SuperAdminRepository superAdminRepo;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public LoginDto signIn(@RequestBody ReqRes signInRequest) throws Exception {

        if (!superAdminRepo.existsByUsername(signInRequest.getUsername()) && 
        !adminRepo.existsByUsername(signInRequest.getUsername())) {
            throw new MissingException("Username");
        }

        String adminType = "";

        if(superAdminRepo.existsByUsername(signInRequest.getUsername())){
            adminType = "SuperAdmin";
        }else if(adminRepo.existsByUsername(signInRequest.getUsername())){
            adminType = "Admin";
        }

        String token = authService.logIn(signInRequest, adminType);

        return LoginDto.builder()
                .adminType(adminType)
                .token(token)
                .build();
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes refreshTokenRequest) throws Exception{
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/logout")
    public void logoutUser(Authentication authentication) {

        // Logout User
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        String token = details.getSessionId();

        authService.logoutUser(username, token);
    }
}
