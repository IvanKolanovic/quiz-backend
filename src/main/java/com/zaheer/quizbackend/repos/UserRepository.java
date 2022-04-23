package com.zaheer.quizbackend.repos;

import com.zaheer.quizbackend.models.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmailAndActiveTrue(String email);

  Optional<User> findByUsernameAndActiveTrue(String name);

  Optional<User> findByIdAndActiveTrue(Long id);

  List<User> findAllByActiveTrue();
}
