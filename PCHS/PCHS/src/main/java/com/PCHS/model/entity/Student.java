package com.PCHS.model.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "students")
public class Student {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Student() {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int nextYear = currentYear + 1;
        this.schoolYear = currentYear + "-" + nextYear;
    }
}
