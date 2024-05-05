package com.PCHS.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PCHS.model.entity.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
    Boolean existsByEmailAndSchoolYear(String email, String schoolYear);

    /*Page<Student> findAllStudents(Pageable pageable);*/

    List<Student> findAllByEnrollStatusAndSchoolYear(String enrollStatus, String schoolYear);
}
