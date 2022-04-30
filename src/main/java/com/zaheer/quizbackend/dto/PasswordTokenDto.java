package com.zaheer.quizbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PasswordTokenDto {
    private String token;
    private Long userID;
    private LocalDate expiryDate;
    private boolean active;
}
