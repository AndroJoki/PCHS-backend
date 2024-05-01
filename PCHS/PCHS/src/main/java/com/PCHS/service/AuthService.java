package com.PCHS.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.PCHS.model.dto.ReqRes;
import com.PCHS.model.entity.Admin;
import com.PCHS.model.entity.AdminAuthToken;
import com.PCHS.repository.AdminRepository;
import java.util.Optional;

/**
 *
 * @author andre
 */
@Service
public class AuthService {
    @Autowired
    private AdminRepository adminRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authManager;

    public ReqRes registerUp(ReqRes registrationRequest){
        ReqRes resp = new ReqRes();
        try {
            Admin admin = new Admin();
            admin.setName(registrationRequest.getName());
            admin.setUsername(registrationRequest.getUsername());
            admin.setPosition(registrationRequest.getPosition());
            admin.setAdvisory(registrationRequest.getAdvisory());
            admin.setEmail(registrationRequest.getEmail());
            admin.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
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

    public ReqRes logIn(ReqRes signinRequest){
        ReqRes response = new ReqRes();

        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getUsername(),signinRequest.getPassword()));
            var admin = adminRepo.findByUsername(signinRequest.getUsername()).orElseThrow();
            System.out.println("ADMIN IS: "+ admin);
            var jwt = jwtUtils.generateToken(admin);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), admin);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("6Hr");
            response.setMessage("Successfully Logged In");
        }catch (Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public ReqRes refreshToken(ReqRes refreshTokenReqiest){
        ReqRes response = new ReqRes();
        String adminUsername = jwtUtils.extractUsername(refreshTokenReqiest.getToken());
        Admin admins = adminRepo.findByUsername(adminUsername).orElseThrow();
        if (jwtUtils.isTokenValid(refreshTokenReqiest.getToken(), admins)) {
            var jwt = jwtUtils.generateToken(admins);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenReqiest.getToken());
            response.setExpirationTime("6Hr");
            response.setMessage("Successfully Refreshed Token");
        }
        response.setStatusCode(500);
        return response;
    }


    public boolean logoutUser(String username, String token) {
        // Remove token from allowedTokens
        Optional<Admin> optionalAdmin = adminRepo.findByUsername(username);
        if (optionalAdmin.isEmpty()) return false;

        optionalAdmin.get().getAdminAuthTokens().remove(new AdminAuthToken(token));
        adminRepo.save(optionalAdmin.get());
        return true;
    }
}
