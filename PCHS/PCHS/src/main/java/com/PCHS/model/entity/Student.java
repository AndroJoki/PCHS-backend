package com.PCHS.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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
