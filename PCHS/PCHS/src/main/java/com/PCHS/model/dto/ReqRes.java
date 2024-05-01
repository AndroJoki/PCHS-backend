package com.PCHS.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.PCHS.model.entity.Admin;
import com.PCHS.model.entity.Student;

import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqRes {

    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String name;
    private String username;
	private String position;
	private String advisory;
    private String email;
    private String password;
    private List<Student> students;
    private Admin admins;
}
