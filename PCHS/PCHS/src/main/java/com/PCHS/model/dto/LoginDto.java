package com.PCHS.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDto {
    private String adminType;
    private String token;
}
