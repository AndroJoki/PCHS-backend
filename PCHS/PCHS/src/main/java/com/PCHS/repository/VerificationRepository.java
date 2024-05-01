package com.PCHS.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.PCHS.model.entity.Verification;

@Repository
public interface VerificationRepository extends CrudRepository<Verification, Long> {

}
