package com.PCHS.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PCHS.model.entity.Student;
import com.PCHS.model.dto.StudentDto;
import com.PCHS.repository.StudentRepository;

@Service
public class StudentService implements IStudentService{

    @Autowired
	private StudentRepository studentRepo;

    public StudentService(StudentRepository studentRepo) {
        this.studentRepo = studentRepo;
    }


	@Override
    public List<Student> allStudents() {
        return (List<Student>) studentRepo.findAll();
    }

	@Override
    public Student getStudent(Long id) {
        Optional<Student> optionalStudent = studentRepo.findById(id);
        return optionalStudent.orElse(null);
    }

	@Override
	public Student addStudent(StudentDto studentDto) {
		Student student = new Student();

        student.setAge(studentDto.getAge());
        student.setClassification(studentDto.getClassification());
        student.setContactNum(studentDto.getContactNum());
        student.setEmail(studentDto.getEmail());
        student.setEnrollStatus(studentDto.getEnrollStatus());
        student.setFatherName(studentDto.getFatherName());
        student.setGradeLevel(studentDto.getGradeLevel());
        student.setLearnerRefNo(studentDto.getLearnerRefNo());
        student.setMotherName(studentDto.getMotherName());
        student.setName(studentDto.getName());

		return studentRepo.save(student);
	}


	@Override
    public Student updateStudent(Long id, Student student) {
        Optional<Student> optionalStudent = studentRepo.findById(id);
        if (optionalStudent.isEmpty()) return null;

        student.setId(id);
        return studentRepo.save(student);
    }


	@Override
	public void deleteStudent(Long id) {
        Optional<Student> optionalStudent = studentRepo.findById(id);
        if (optionalStudent.isPresent()){
			studentRepo.delete(optionalStudent.get());
		}
			
    }
}
