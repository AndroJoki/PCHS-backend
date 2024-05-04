package com.PCHS.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactUsDto {
    private String name;
    private String email;
    private String subject;
    private String message;
}
