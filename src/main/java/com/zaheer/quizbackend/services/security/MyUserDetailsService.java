package com.zaheer.quizbackend.services.security;

import com.zaheer.quizbackend.models.db.User;
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
    Optional<User> user = userRepository.findByEmailAndActiveTrue(email);
    user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + email));
    return user.map(MyUserDetails::new).get();
  }
}
