package com.zaheer.quizbackend.services;

import com.zaheer.quizbackend.models.db.User;
import com.zaheer.quizbackend.repos.UserRepository;
import com.zaheer.quizbackend.services.interfaces.CurrentUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CurrentUserServiceImpl extends BaseService implements CurrentUserService {

    private final UserRepository userRepository;
    @Override
    public String getLoggedInUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @Override
    public boolean isLoggedInUserAdmin() {
        User loggedInUser = userRepository.findByEmailAndActiveTrue(getLoggedInUserEmail())
                .orElseThrow(resourceNotFound("User does not exist."));
        return loggedInUser.getRoles().equals("ROLE_ADMIN") || loggedInUser.getRoles().equals("ROLE_SUPER_ADMIN");
    }


}
