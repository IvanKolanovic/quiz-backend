package com.zaheer.quizbackend.services;

import com.zaheer.quizbackend.dto.UserDto;
import com.zaheer.quizbackend.exceptions.RequestFailedException;
import com.zaheer.quizbackend.exceptions.ResourceNotFoundException;
import com.zaheer.quizbackend.models.db.User;
import com.zaheer.quizbackend.repos.UserRepository;
import com.zaheer.quizbackend.services.interfaces.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CONFLICT;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BaseService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserStatisticsService userStatisticsService;
    private final CountryService countryService;
    private final CurrentUserService currentUserService;
    private final NotificationEmailService notificationEmailService;

    @Override
    @Transactional
    public User createUser(User user) {

        if (isEmailInUse(user.getEmail())) {
            throw new RequestFailedException(
                    CONFLICT, "User email:" + user.getEmail() + " is already taken!", "Email");
        }

        if (isUsernameInUse(user.getUsername())) {
            throw new RequestFailedException(
                    CONFLICT, "Username:" + user.getUsername() + " is already taken!", "Username");
        }

        user.setLearningIndex(1);
        user.setActive(true);
        user.setRoles("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserStatistics(userStatisticsService.createStatistic());

        notificationEmailService.sendConfirmationEmailOnRegistration(user);

        return userRepository.saveAndFlush(user);
    }

    @Override
    public User getUser(Long id) {
        return userRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id:" + id + " not found."));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public boolean deleteUser(Long userId) {
        User user =
                userRepository
                        .findByIdAndActiveTrue(userId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("User with id:" + userId + " not found."));

        userRepository.delete(user);
        return true;
    }

    @Override
    @Transactional
    public User banUser(Long userId, Long adminID) {

        User adminUser = userRepository.findUserById(adminID).orElseThrow(
                () -> new ResourceNotFoundException("Admin user with id:" + userId + " not found."));

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with id:" + userId + " not found."));

        if (user.getRoles().equals("ROLE_ADMIN") && adminUser.getRoles().equals("ROLE_ADMIN")) {
            throw new RequestFailedException(CONFLICT, "Admins cannot ban each other.", "adminsForbiddenBan");
        }

        if (user.getRoles().equals("ROLE_SUPER_ADMIN")) {
            throw new RequestFailedException(CONFLICT, "Super admin cannot be banned.", "superAdminForbiddenBan");
        }

        user.setActive(!user.getActive());

        return userRepository.saveAndFlush(user);
    }


    @Override
    @Transactional
    public User updateUser(Long id, User input) {

        User updatedUser =
                userRepository
                        .findById(id)
                        .map(
                                user -> {
                                    checkIfUsernameAndEmailTaken(input, user);
                                    user.setFirstName(input.getFirstName());
                                    user.setLastName(input.getLastName());
                                    return user;
                                })
                        .orElseThrow(resourceNotFound("User with id " + id + " does not exist."));

        return userRepository.saveAndFlush(updatedUser);
    }

    @Override
    @Transactional
    public User adminUpdateUser(Long id, User input) {

        User updatedUser =
                userRepository
                        .findById(id)
                        .map(
                                user -> {
                                    if (user.getRoles().equals("ROLE_SUPER_ADMIN")) {
                                        throw new RequestFailedException(CONFLICT, "Super Admin information cannot be edited by admin.",
                                            "superAdminEditByAdmin");
                                    }
                                    checkIfUsernameAndEmailTaken(input, user);
                                    user.setRoles(input.getRoles());
                                    user.setLearningIndex(input.getLearningIndex());
                                    user.setFirstName(input.getFirstName());
                                    user.setLastName(input.getLastName());
                                    return user;
                                })
                        .orElseThrow(resourceNotFound("User with id " + id + " does not exist."));

        return userRepository.saveAndFlush(updatedUser);
    }

    private void checkIfUsernameAndEmailTaken(User input, User user) {
        if (!user.getEmail().equals(input.getEmail())) {
            if (isEmailInUse(input.getEmail())) {
                throw new RequestFailedException(
                        CONFLICT, "User email:" + input.getEmail() + " is already taken!", "Email");
            }
            user.setEmail(input.getEmail());
        }

        if (!user.getUsername().equals(input.getUsername())) {
            if (isUsernameInUse(input.getUsername())) {
                throw new RequestFailedException(
                        CONFLICT, "Username:" + input.getUsername() + " is already taken!", "Username");
            }
            user.setUsername(input.getUsername());
        }
    }

    @Override
    public User updateUserPassword(UserDto userDto) {

        if (userDto.getOldPassword() == null && !currentUserService.isLoggedInUserAdmin()) {
            throw new RequestFailedException(CONFLICT, "You must provide an old password.");
        }

        User updatedUser =
                userRepository
                        .findUserById(userDto.getId())
                        .orElseThrow(resourceNotFound("User with id " + userDto.getId() + " does not exist."));

        if (!currentUserService.isLoggedInUserAdmin()) {
            if (!passwordEncoder.matches(userDto.getOldPassword(), updatedUser.getPassword())) {
                throw new RequestFailedException(CONFLICT, "Old password is incorrect.");
            }
        }
        updatedUser.setPassword(passwordEncoder.encode(userDto.getNewPassword()));

        notificationEmailService.sendConfirmationEmailOnPasswordChange(updatedUser);

        return userRepository.saveAndFlush(updatedUser);
    }

    @Override
    @Transactional
    public User updateUserLearningIndex(Long id, int learningIndex) {
        User updatedUser =
                userRepository
                        .findByIdAndActiveTrue(id)
                        .orElseThrow(resourceNotFound("User with id " + id + " does not exist."));

        updatedUser.setLearningIndex(updatedUser.getLearningIndex() + learningIndex);
        return getUserForUpdatingIndex(updatedUser);
    }

    @Override
    @Transactional
    public User updateUserSetLearningIndex(Long id, int learningIndex) {
        User updatedUser =
                userRepository
                        .findByIdAndActiveTrue(id)
                        .orElseThrow(resourceNotFound("User with id " + id + " does not exist."));

        updatedUser.setLearningIndex(learningIndex);
        return getUserForUpdatingIndex(updatedUser);
    }

    private User getUserForUpdatingIndex(User updatedUser) {
        int numOfCountries = countryService.getNumOfCountries();
        if (updatedUser.getLearningIndex() == 0) {
            updatedUser.setLearningIndex(numOfCountries);
        } else if (updatedUser.getLearningIndex() == numOfCountries + 1) {
            updatedUser.setLearningIndex(1);
        }

        return userRepository.saveAndFlush(updatedUser);
    }

    @Override
    public boolean isEmailInUse(String email) {
        Optional<User> dbUser = userRepository.findByEmailAndActiveTrue(email);
        return dbUser.isPresent();
    }

    @Override
    public boolean isUsernameInUse(String name) {
        Optional<User> dbUser = userRepository.findByUsernameAndActiveTrue(name);
        return dbUser.isPresent();
    }
}
