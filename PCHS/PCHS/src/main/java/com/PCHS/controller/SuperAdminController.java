package com.PCHS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PCHS.exceptions.AlreadyExistException;
import com.PCHS.exceptions.MissingException;
import com.PCHS.model.dto.AdminDto;
import com.PCHS.model.dto.SuperAdminDto;
import com.PCHS.model.entity.Admin;
import com.PCHS.model.entity.SuperAdmin;
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

    @GetMapping
    public SuperAdminDto getSelfInfo(Authentication authentication) throws Exception{
        SuperAdmin selfUser = ((SuperAdmin) authentication.getPrincipal());
        return SuperAdminDto.builder()
            .username(selfUser.getUsername())
            .password(selfUser.getPassword())
            .build();
    }

    @PostMapping("add-admin")
    public AdminDto createAdminRequest(@RequestBody AdminDto addRequest) throws Exception {

        if (superAdminService.isAdminExistByEmail(addRequest.getEmail())) {
            throw new AlreadyExistException("Email");
        }else if(superAdminService.isAdminExistByUsername(addRequest.getUsername())){
            throw new AlreadyExistException("Username");
        }

        Admin admin = superAdminService.addAdmin(addRequest);
        return AdminDto.buildAdminInfo(admin);
    }

    @DeleteMapping("delete-admin/{id}")
    public AdminDto deleteAdminRequest(@PathVariable Long id) throws Exception
    {
        return AdminDto.buildAdminInfo(superAdminService.deleteAdmin(id));
    }

    @PutMapping("update")
    public SuperAdminDto updateSuperAdminRequest(Authentication authentication, @RequestBody SuperAdmin superAdmin) throws Exception
    {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        Boolean passwordChanged=false;
        SuperAdmin optionalSuperAdmin = superAdminService.getSuperAdminByUsername(username);

        if (optionalSuperAdmin == null) throw new MissingException("SuperAdmin");

        if (superAdmin.getUsername().equals("")) optionalSuperAdmin.setUsername(superAdmin.getUsername());
        if (superAdmin.getPassword().equals("")){
            optionalSuperAdmin.setPassword(superAdmin.getPassword());
            passwordChanged=true;
        } 

        SuperAdmin updatedSuperAdmin = superAdminService.updateSuperAdmin(username, passwordChanged, optionalSuperAdmin);
        return SuperAdminDto.builder()
            .username(updatedSuperAdmin.getUsername())
            .password(updatedSuperAdmin.getPassword())
            .build();
    }
}
