package com.PCHS.model.dto;


import com.PCHS.model.entity.Student;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentDto {
    private Long id;
    private String name;
    private String learnerRefNo;
    private String email;
	private String gradeLevel;
	private String classification;
    private int age;
    private String address;
    private String fatherName;
    private String motherName;
    private String contactNum;
    private String enrollStatus;


    public static StudentDto buildStudentInfo(Student student) {
        return StudentDto.builder()
                .name(student.getName())
                .learnerRefNo(student.getLearnerRefNo())
                .email(student.getEmail())
                .gradeLevel(student.getGradeLevel())
                .classification(student.getClassification())
                .age(student.getAge())
                .address(student.getAddress())
                .fatherName(student.getFatherName())
                .motherName(student.getMotherName())
                .contactNum(student.getContactNum())
                .enrollStatus(student.getEnrollStatus())
                .build();
    }

    public static StudentDto getStudentInfo(Student student) {
        return StudentDto.builder()
                .id(student.getId())
                .name(student.getName())
                .learnerRefNo(student.getLearnerRefNo())
                .email(student.getEmail())
                .gradeLevel(student.getGradeLevel())
                .classification(student.getClassification())
                .age(student.getAge())
                .address(student.getAddress())
                .fatherName(student.getFatherName())
                .motherName(student.getMotherName())
                .contactNum(student.getContactNum())
                .enrollStatus(student.getEnrollStatus())
                .build();
    }
}
