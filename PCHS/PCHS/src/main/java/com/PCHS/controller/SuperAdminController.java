package com.PCHS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PCHS.model.dto.ReqRes;
import com.PCHS.service.SuperAdminService;

/**
 *
 * @author andre
 */
@RestController
@RequestMapping("/api/superadmin")
public class SuperAdminController {
    @Autowired
    private SuperAdminService superAdminService;

    @PostMapping("/add-admin")
    public ResponseEntity<ReqRes> signUp(@RequestBody ReqRes signUpRequest){
        return ResponseEntity.ok(superAdminService.addAdmin(signUpRequest));
    }

    @DeleteMapping("delete-admin/{id}")
    public void deleteDog(@PathVariable Long id)
    {
        superAdminService.deleteAdmin(id);
    }
}
