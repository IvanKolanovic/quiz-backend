package com.zaheer.quizbackend.services;

import com.zaheer.quizbackend.models.db.Email;
import com.zaheer.quizbackend.services.interfaces.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Autowired
    private final JavaMailSender javaMailSender;

    @Override
    public void sendHtmlEmail(Email email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        setEmailMessageParameters(email, message);
        javaMailSender.send(mimeMessage);
    }

    private void setEmailMessageParameters(Email email, MimeMessageHelper message) throws MessagingException {
        if (email.getSender() != null) {
            message.setFrom(email.getSender());
        }
        message.setTo(email.getReceivers());
        if (email.getBcc() != null) {
            message.setBcc(email.getBcc());
        }
        message.setSubject(email.getSubject());
        message.setText(email.getText(), true);
    }

}
