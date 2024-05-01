package com.PCHS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PCHS.model.dto.StudentDto;
import com.PCHS.model.entity.Student;
import com.PCHS.service.IStudentService;

@RestController
@RequestMapping("/api/admin/student")
public class StudentController {

    @Autowired
	private IStudentService studentService;
	
	@GetMapping("get-all")
    public List<Student> findStudents(){
       return studentService.allStudents();
    }

	@GetMapping("show/{id}")
    public Student showStudent(@PathVariable Long id) {
       return studentService.getStudent(id);
    }

    @PostMapping("add")
    public Student addStudent(@RequestBody StudentDto student)
    {
        return studentService.addStudent(student);
    }

    @PutMapping("update/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student)
    {
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("delete/{id}")
    public void deleteDog(@PathVariable Long id)
    {
        studentService.deleteStudent(id);
    }
}
