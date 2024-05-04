package com.PCHS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PCHS.exceptions.AlreadyExistException;
import com.PCHS.model.dto.AdminDto;
import com.PCHS.model.entity.Admin;
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
}
