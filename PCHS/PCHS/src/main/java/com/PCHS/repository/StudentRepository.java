package com.PCHS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PCHS.model.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
