package com.PCHS.model.dto;

import lombok.Data;

@Data
public class StudentDto {
    private String name;
    private String learnerRefNo;
    private String email;
	private String gradeLevel;
	private String classification;
    private int age;
    private String fatherName;
    private String motherName;
    private String contactNum;
    private String enrollStatus;
}
