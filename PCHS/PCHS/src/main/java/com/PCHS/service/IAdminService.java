package com.PCHS.service;

import java.util.List;

import com.PCHS.model.entity.Admin;

public interface IAdminService {

    
    List<Admin> allAdmins();
    Admin getAdmin(Long id);
    Admin updateAdmin(Long id, Admin admin);
    void deleteAdmin(Long id);
}
