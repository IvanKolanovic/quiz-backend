package com.zaheer.quizbackend.repos;

import com.zaheer.quizbackend.models.db.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Long> {

    Optional<PasswordToken> findByToken(String token);

    List<PasswordToken> findByUser_UsernameAndActiveIsTrueAndExpiryDateAfter(String username, LocalDateTime today);
}
