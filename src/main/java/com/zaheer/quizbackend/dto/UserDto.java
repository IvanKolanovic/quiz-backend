package com.zaheer.quizbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private String oldPassword;
    private String newPassword;

    @Override
    public String toString() {
        return "username = " + username + ", password = *";
    }
}
