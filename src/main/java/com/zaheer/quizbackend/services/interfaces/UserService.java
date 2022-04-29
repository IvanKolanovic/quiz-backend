package com.zaheer.quizbackend.services.interfaces;

import com.zaheer.quizbackend.dto.UserDto;
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
    User banUser(Long userId, Long adminID);

    User updateUser(Long id, User input);

    User adminUpdateUser(Long id, User input);
    User updateUserLearningIndex(Long id, int learningIndex);

    User updateUserSetLearningIndex(Long id, int learningIndex);

    User updateUserPassword(UserDto userDto);

    boolean isUsernameInUse(String name);

    void sendPasswordResetLinkToUser(Long userID);
}
