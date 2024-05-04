package com.PCHS.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuperAdminDto {
    private String username;
    private String password;
}
