package com.PCHS.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.PCHS.model.entity.Admin;
import com.PCHS.model.entity.AdminAuthToken;
import com.PCHS.repository.AdminRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JWTUtils {

    @Autowired
    private AdminRepository adminRepo;

    private SecretKey Key;

    private  static  final long EXPIRATION_TIME = 21600000; //6hours or 21600000 milisecs
    public JWTUtils(){
        String secreteString = "32423SJDKHGFSLKDJGHF98473502749B4378965fdgg9834656914523145239056785UJADHFASLIUGDFAIU34643634CVDFGD";
        byte[] keyBytes = Base64.getDecoder().decode(secreteString.getBytes(StandardCharsets.UTF_8));
        this.Key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Key)
                .compact();
    }
    public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails){
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Key)
                .compact();
    }

    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }
    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        return claimsTFunction.apply(Jwts.parser().verifyWith(Key).build().parseSignedClaims(token).getPayload());
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token){
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

    public void isTokenAllowedByUser(String username, String token) {
        Optional<Admin> optionalAdmin = adminRepo.findByUsername(username);
        if (optionalAdmin.isPresent()){

            // Perform Token Validation Checks
            List<AdminAuthToken> updatedAllowedTokens = new ArrayList<>();
            List<AdminAuthToken> allowedTokens = optionalAdmin.get().getAdminAuthTokens();
            allowedTokens.forEach(allowedToken -> {
                // Check if token is still valid
                if (isTokenExpired(allowedToken.getAuthToken())) {
                    updatedAllowedTokens.add(allowedToken);
                }
            });

            // Update Allowed Tokens
            Admin admin = optionalAdmin.get();
            admin.setAdminAuthTokens(updatedAllowedTokens);
            adminRepo.save(admin);
        }
    }
}

