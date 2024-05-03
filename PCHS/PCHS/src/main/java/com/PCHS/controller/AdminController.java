package com.PCHS.controller;

//import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PCHS.model.entity.Admin;
import com.PCHS.service.AdminService;

/**
 *
 * @author andre
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@GetMapping("get-all")
    public List<Admin> findAdmins(){
       return adminService.allAdmins();
    }

	@GetMapping("show/{id}")
    public Admin showAdmin(@PathVariable Long id) {
       return adminService.getAdmin(id);
    }

    @PutMapping("update/{id}")
    public Admin updateAdmin(@PathVariable Long id, @RequestBody Admin admin)
    {
        return adminService.updateAdmin(id, admin);
    }

}
