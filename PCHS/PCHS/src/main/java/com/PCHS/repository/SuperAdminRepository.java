package com.PCHS.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PCHS.model.entity.SuperAdmin;

@Repository
public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long>{
    
    Optional<SuperAdmin> findByUsername(String username);
    Boolean existsByUsername(String username);

}
