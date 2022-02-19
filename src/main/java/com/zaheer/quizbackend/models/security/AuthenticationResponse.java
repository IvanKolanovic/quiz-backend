package com.zaheer.quizbackend.models.security;

import com.zaheer.quizbackend.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
  private User user;
  private String jwt;
  private Date validTo;
}
