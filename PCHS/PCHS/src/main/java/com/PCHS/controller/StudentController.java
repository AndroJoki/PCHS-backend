package com.PCHS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PCHS.exceptions.AlreadyExistException;
import com.PCHS.exceptions.MissingException;
import com.PCHS.model.dto.AdminDto;
import com.PCHS.model.dto.StudentDto;
import com.PCHS.model.entity.Student;
import com.PCHS.service.StudentService;

@RestController
@RequestMapping("/api/admin/student")
public class StudentController {

    @Autowired
	private StudentService studentService;
	
	@GetMapping("get-all")
    public List<StudentDto> findStudentsRequest() throws Exception {

        List<StudentDto> results = new java.util.ArrayList<>(List.of());
        
        results = studentService.allStudents()
                .stream()
                .map(StudentDto::buildStudentInfo)
                .toList();

        return results;
    }

    @GetMapping("page/{offset}")
    private List<StudentDto> getStudentsWithPage(@PathVariable int offset) throws Exception {
        
        List<StudentDto> results = new java.util.ArrayList<>(List.of());

        results = studentService.studentsWithPage(offset)
                .stream()
                .map(StudentDto::buildStudentInfo)
                .toList();

        return results;
    }

	@GetMapping("show/{id}")
    public StudentDto showStudentRequest(@PathVariable Long id) throws Exception {
       return StudentDto.buildStudentInfo(studentService.getStudent(id));
    }

    @PostMapping("add")
    public StudentDto addStudentRequest(@RequestBody StudentDto addRequest) throws Exception
    {   
        if (studentService.isStudentExistByEmail(addRequest.getEmail())) {
            throw new AlreadyExistException("Email");
        }
        Student student = studentService.addStudent(addRequest);
        return StudentDto.buildStudentInfo(student);
    }

    @PutMapping("update/{id}")
    public StudentDto updateStudentRequest(@PathVariable Long id, @RequestBody Student updateRequest) throws Exception
    {
        return StudentDto.buildStudentInfo(studentService.updateStudent(id, updateRequest));
    }

    @DeleteMapping("delete/{id}")
    public StudentDto deleteStudentRequest(@PathVariable Long id) throws Exception
    {
        return StudentDto.buildStudentInfo(studentService.deleteStudent(id));
    }
}
