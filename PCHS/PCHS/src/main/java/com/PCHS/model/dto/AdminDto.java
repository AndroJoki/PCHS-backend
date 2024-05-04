package com.PCHS.model.dto;

import com.PCHS.model.entity.Admin;
import lombok.Builder;

import lombok.Data;

@Data
@Builder
public class AdminDto {
    private String name;
    private String username;
	private String position;
	private String advisory;
    private String email;
    private String password;

    public static AdminDto buildAdminInfo(Admin admin) {
        return AdminDto.builder()
                .name(admin.getName())
                .username(admin.getUsername())
                .position(admin.getPosition())
                .advisory(admin.getAdvisory())
                .email(admin.getEmail())
                .password(admin.getPassword())
                .build();
    }
}
