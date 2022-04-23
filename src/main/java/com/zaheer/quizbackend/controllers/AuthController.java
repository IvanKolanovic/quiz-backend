package com.zaheer.quizbackend.controllers;

import com.zaheer.quizbackend.exceptions.RequestFailedException;
import com.zaheer.quizbackend.models.db.User;
import com.zaheer.quizbackend.models.security.AuthenticationRequest;
import com.zaheer.quizbackend.models.security.AuthenticationResponse;
import com.zaheer.quizbackend.models.security.MyUserDetails;
import com.zaheer.quizbackend.repos.UserRepository;
import com.zaheer.quizbackend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;

  @RequestMapping(value = "/auth", method = RequestMethod.POST)
  public ResponseEntity<?> createAuthToken(
      @RequestBody AuthenticationRequest authenticationRequest) {

    User user =
        userRepository.findByEmailAndActiveTrue(authenticationRequest.getEmail()).orElse(null);

    if (user != null && !user.getActive()) {
      log.info("User is removed!");
      throw new RequestFailedException(HttpStatus.CONFLICT, "User is removed!");
    }

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authenticationRequest.getEmail(), authenticationRequest.getPassword()));

    MyUserDetails userDetails =
        (MyUserDetails) userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
    String jwt = jwtUtil.generateToken(userDetails);

    return ResponseEntity.ok(
        new AuthenticationResponse(userDetails.getUser(), jwt, jwtUtil.extractExpiration(jwt)));
  }
}
