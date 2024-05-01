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

import com.PCHS.model.dto.ReqRes;
import com.PCHS.service.AuthService;
import com.PCHS.service.VerificationService;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private VerificationService verifyService;

    @PostMapping("/register")
    public ResponseEntity<ReqRes> signUp(@RequestBody ReqRes signUpRequest){
        return ResponseEntity.ok(authService.registerUp(signUpRequest));
    }
    @PostMapping("/login")
    public ResponseEntity<ReqRes> signIn(@RequestBody ReqRes signInRequest){
        return ResponseEntity.ok(authService.logIn(signInRequest));
    }
    @PostMapping("/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes refreshTokenRequest){
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

    @PostMapping("/register-code/send")
    public void sendRegisterCode(){
        verifyService.sendRegisterCode();
    }

    @PostMapping("/register-code/verify")
    public boolean verifyRegisterCode(@RequestBody String code){
        return verifyService.verifyRegisterCode(code);
    }


    @GetMapping("/admin-code/verify")
    public boolean verifyAdminCode(@RequestBody String code){
        return verifyService.verifyAdminCode(code);
    }



    /*private final AdminRepository adminRepo;

    private AuthenticationManager authManager;

    @Autowired
	private IAdminService adminService;

    public AuthController(AdminRepository adminRepo, AuthenticationManager authManager) {
        this.adminRepo = adminRepo;
        this.authManager = authManager;
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return new ResponseEntity<>("Admin Login Success!", HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if(adminRepo.existsByUsername(registerDto.getEmail())){
            return new ResponseEntity<>("Email is taken!", HttpStatus.BAD_REQUEST);
        }

        adminService.addAdmin(registerDto);
        return new ResponseEntity<>("Admin Registered Success!", HttpStatus.OK);
    }
    */
}
