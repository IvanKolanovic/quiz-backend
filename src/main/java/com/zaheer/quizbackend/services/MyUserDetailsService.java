package com.zaheer.quizbackend.services;

import com.zaheer.quizbackend.models.User;
import com.zaheer.quizbackend.models.security.MyUserDetails;
import com.zaheer.quizbackend.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByEmail(email);
    user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + email));
    return user.map(MyUserDetails::new).get();
  }
}
