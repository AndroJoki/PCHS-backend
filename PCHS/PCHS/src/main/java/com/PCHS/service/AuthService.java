package com.PCHS.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.PCHS.model.dto.ReqRes;
import com.PCHS.model.entity.Admin;
import com.PCHS.model.entity.AdminAuthToken;
import com.PCHS.repository.AdminRepository;

import java.util.Optional;

import com.PCHS.repository.SuperAdminRepository;

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
    private AuthenticationManager authManager;
    @Autowired
    private SuperAdminRepository superAdminRepo;


    public String logIn(ReqRes signinRequest, String adminType){
        ReqRes response = new ReqRes();
        String jwt = "";

        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getUsername(),signinRequest.getPassword()));
            
            if(adminType.equals("SuperAdmin")){
                System.out.println("SuperAdmin Success");
                var user = superAdminRepo.findByUsername(signinRequest.getUsername()).orElseThrow();
                jwt = jwtUtils.generateToken(user);
            } 
            else if(adminType.equals("Admin")){
                var user = adminRepo.findByUsername(signinRequest.getUsername()).orElseThrow();
                jwt = jwtUtils.generateToken(user);
            }      
    
        }catch (Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return jwt;
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


    /*var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
                response.setToken(jwt);
                response.setRefreshToken(refreshToken);*/
}
