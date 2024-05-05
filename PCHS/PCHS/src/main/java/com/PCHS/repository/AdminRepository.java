package com.PCHS.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PCHS.model.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>{
    /*
    Boolean existsByEmail(String email);

    @Query("SELECT u FROM Admin u WHERE u.email = ?1")
    Admin findAdminByEmail(String email);
    */

    Optional<Admin> findByUsername(String username);
    Boolean existsByUsername(String username);

    Optional<Admin> findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByUsernameAndPassword(String username, String password);
}
