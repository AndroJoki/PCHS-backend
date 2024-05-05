package com.PCHS.controller;

//import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PCHS.exceptions.MissingException;
import com.PCHS.model.dto.AdminDto;
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

    @GetMapping
    public AdminDto getSelfInfo(Authentication authentication) throws Exception{
        Admin selfUser = ((Admin) authentication.getPrincipal());
        return AdminDto.buildAdminInfo(selfUser);
    }


	@GetMapping("get-all")
    public List<AdminDto> findAdminsRequest() throws Exception {

        List<AdminDto> results = new java.util.ArrayList<>(List.of());
        
        results = adminService.allAdmins()
                .stream()
                .map(AdminDto::buildAdminInfo)
                .toList();

        return results;
    }

    @GetMapping("page/{offset}")
    private List<AdminDto> getStudentsWithPage(@PathVariable int offset) throws Exception {
        
        List<AdminDto> results = new java.util.ArrayList<>(List.of());

        results = adminService.adminsWithPage(offset)
                .stream()
                .map(AdminDto::buildAdminInfo)
                .toList();

        return results;
    }

	@GetMapping("show/{id}")
    public AdminDto showAdminRequest(@PathVariable Long id) throws Exception {
       return AdminDto.buildAdminInfo(adminService.getAdmin(id));
    }

    @PutMapping("update")
    public AdminDto updateAdminRequest(Authentication authentication, @RequestBody Admin admin) throws Exception
    {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        Boolean passwordChanged=false;
        Admin optionalAdmin = adminService.getAdminByUsername(username);

        if (optionalAdmin == null) throw new MissingException("Admin");

        if (!admin.getName().equals("")) optionalAdmin.setName(admin.getName());
        if (!admin.getUsername().equals("")) optionalAdmin.setUsername(admin.getUsername());
        if (!admin.getAdvisory().equals("")) optionalAdmin.setAdvisory(admin.getAdvisory());
        if (!admin.getPosition().equals("")) optionalAdmin.setPosition(admin.getPosition());
        if (!admin.getEmail().equals("")) optionalAdmin.setEmail(admin.getEmail());
        if (!admin.getPassword().equals("")){
            optionalAdmin.setPassword(admin.getPassword());
            passwordChanged=true;
        }

        Admin updatedAdmin = adminService.updateAdmin(username, passwordChanged, optionalAdmin);
        return AdminDto.buildAdminInfo(updatedAdmin);
    }

}
