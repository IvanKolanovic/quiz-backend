package com.zaheer.quizbackend.services.interfaces;

import com.zaheer.quizbackend.models.db.User;

public interface NotificationEmailService {

    void sendConfirmationEmailOnPasswordChange(User user);

    void sendConfirmationEmailOnRegistration(User user);

}
