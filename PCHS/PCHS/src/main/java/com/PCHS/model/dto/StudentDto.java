package com.PCHS.model.dto;


import java.time.LocalDate;

import com.PCHS.model.entity.Student;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentDto {
    private Long id;
    private String name;
    private String schoolYear;
    private String learnerRefNo;
    private String email;
	private String gradeLevel;
	private String classification;
    private String age;
    private String address;
    private String fatherName;
    private String motherName;
    private String contactNum;
    private String enrollStatus;

    public static StudentDto buildStudentInfo(Student student) {
        return StudentDto.builder()
                .id(student.getId())
                .name(student.getName())
                .schoolYear(student.getSchoolYear())
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

    public void generateSchoolYear() {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int nextYear = currentYear + 1;
        this.schoolYear = currentYear + "-" + nextYear;
    }

}
