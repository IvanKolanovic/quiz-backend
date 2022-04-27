package com.zaheer.quizbackend.services.interfaces;

import com.zaheer.quizbackend.models.db.Email;

import javax.mail.MessagingException;

public interface EmailService {
    void sendHtmlEmail(Email email) throws MessagingException;
}
