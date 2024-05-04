package com.PCHS.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.PCHS.model.dto.StudentDto;
import com.PCHS.model.entity.Student;
import com.PCHS.repository.StudentRepository;

/**
 *
 * @author andre
 */
@Service
public class StudentService{

    @Autowired
	private StudentRepository studentRepo;

    public StudentService(StudentRepository studentRepo) {
        this.studentRepo = studentRepo;
    }

    public boolean isStudentExistByEmail(String email) {
        return studentRepo.existsByEmail(email);
    }

    public Page<Student> studentsWithPage(int offset){
        Page<Student> students = studentRepo.findAll(PageRequest.of(offset, 10));
        return students;
    }


    public Page<Student> findEnrolledStudents(int offset, String field, String direction, String enrollStatus) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction.toUpperCase());
        return studentRepo.findAllByEnrollStatus(enrollStatus, PageRequest.of(offset, 10, sortDirection, field));
    }
    
    

    public List<Student> allStudents() {
        return (List<Student>) studentRepo.findAll();
    }

    public Student getStudent(Long id) {
        Optional<Student> optionalStudent = studentRepo.findById(id);
        return optionalStudent.orElse(null);
    }

	public Student addStudent(StudentDto addRequest) {
		Student student = new Student();

        student.setAge(addRequest.getAge());
        student.setClassification(addRequest.getClassification());
        student.setContactNum(addRequest.getContactNum());
        student.setEmail(addRequest.getEmail());
        student.setAddress(addRequest.getAddress());
        student.setEnrollStatus(addRequest.getEnrollStatus());
        student.setFatherName(addRequest.getFatherName());
        student.setGradeLevel(addRequest.getGradeLevel());
        student.setLearnerRefNo(addRequest.getLearnerRefNo());
        student.setMotherName(addRequest.getMotherName());
        student.setName(addRequest.getName());

		return studentRepo.save(student);
	}


    public Student updateStudent(Long id, Student student) {
        Optional<Student> optionalStudent = studentRepo.findById(id);
        if (optionalStudent.isEmpty()) return null;

        student.setId(id);
        studentRepo.save(student);
        
        return student;
    }


	public Student deleteStudent(Long id) {
        Optional<Student> optionalStudent = studentRepo.findById(id);
        if (optionalStudent.isPresent()){
			studentRepo.delete(optionalStudent.get());
		}
		return optionalStudent.get();	
    }
}
