package com.PCHS.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author andre
 */
@NoArgsConstructor
@Data
@Entity
@Table(name = "verification")
public class Verification {

    @Id
    private Long id;
    private String otp_code;
    private String admin_passcode;
    private LocalDateTime otpGeneratedTime;
}
