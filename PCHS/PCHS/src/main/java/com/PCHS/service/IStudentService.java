package com.PCHS.service;

import java.util.List;

import com.PCHS.model.dto.StudentDto;
import com.PCHS.model.entity.Student;

public interface IStudentService {

    List<Student> allStudents();
    Student getStudent(Long id);
    Student updateStudent(Long id, Student student);
    Student addStudent(StudentDto studentDto);
    void deleteStudent(Long id);
}
