package com.zaheer.quizbackend.services.interfaces;

import com.zaheer.quizbackend.models.db.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

  User createUser(User user);

  User getUser(Long id);

  List<User> getAllUsers();

  boolean deleteUser(Long userId);

  boolean isEmailInUse(String email);

    @Transactional
    User banUser(Long userId);

    User updateUser(Long id, User input);

    User updateUserLearningIndex(Long id, int learningIndex);

    User updateUserJumpToIndex(Long id, int learningIndex);

    boolean isUsernameInUse(String name);
}
