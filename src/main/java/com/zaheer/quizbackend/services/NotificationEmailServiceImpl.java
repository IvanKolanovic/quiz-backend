package com.zaheer.quizbackend.services;

import com.zaheer.quizbackend.models.db.Email;
import com.zaheer.quizbackend.models.db.PasswordToken;
import com.zaheer.quizbackend.models.db.User;
import com.zaheer.quizbackend.services.interfaces.EmailService;
import com.zaheer.quizbackend.services.interfaces.NotificationEmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationEmailServiceImpl implements NotificationEmailService {

    private final EmailService emailService;

    @Override
    public void sendConfirmationEmailOnPasswordChange(User user) {
        String emailText = "<p>Dear " + user.getFirstName() + ", your password was successfully changed on " + LocalDate.now() + " : " + LocalDateTime.now() +
                "<br" + "</p>" +
                "<p>You can now login with your new password.</p>" +
                "<a href='https://quiz.marko-juric.from.hr/auth/login'>Flaginator Login</a>" +
                "<p><b>~Your Flaginator Team</b></p>";
        Email email = Email.builder()
                .sender("Flaginator")
                .receivers(new String[]{user.getEmail()})
                .bcc(new String[]{"flaginator.test@oreple.com"})
                .subject("Password change confirmation email")
                .text(emailText)
                .build();
        try {
            emailService.sendHtmlEmail(email);
        } catch (MessagingException e) {
            log.error("Error while trying to send confirmation email on password change");
        }
    }

    @Override
    public void sendConfirmationEmailOnRegistration(User user) {
        String emailText = "<p>Dear " + user.getFirstName() + ", you have successfully created your account on Flaginator.</p>" +
                "<p>You can now login with your email and the password you provided.</p>" +
                "<p>Your email: " + user.getEmail() + "<br>" +
                "<a href='https://quiz.marko-juric.from.hr/auth/login'>Flaginator Login</a>" +
                "<p><b>~Your Flaginator Team</b></p>";
        Email email = Email.builder()
                .sender("Flaginator")
                .receivers(new String[]{user.getEmail()})
                .bcc(new String[]{"flaginator.test@oreple.com"})
                .subject("Welcome to Flaginator")
                .text(emailText)
                .build();
        try {
            emailService.sendHtmlEmail(email);
        } catch (MessagingException e) {
            log.error("Error while trying to send confirmation email on registration.");
        }
    }

    private String getUserEmailFromPasswordToken(final PasswordToken passwordToken) {
        return passwordToken.getUser().getEmail();
    }

    @Override
    public void sendPasswordResetLinkToUser(final PasswordToken passwordToken) {
        final String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/set-password").build().toUriString();
        final String link = "<a href='" + url + passwordToken.getToken() + "'>" + url + passwordToken.getToken() + "</a>";
        final String emailText = "<p>Click on this link to reset your password.</p>" + link + "<p> Link is valid for the next 24 hours.</p>";

        Email email = Email.builder()
                .sender("Flaginator")
                .receivers(new String[]{getUserEmailFromPasswordToken(passwordToken)})
                .bcc(new String[]{"flaginator.test@oreple.com"})
                .subject("Welcome to Flaginator")
                .text(emailText)
                .build();
        try {
            emailService.sendHtmlEmail(email);
        } catch (MessagingException e) {
            log.error("Error while trying to send password reset link to user email.");
        }
    }
}
