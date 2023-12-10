package com.ensak.connect.util.email;

import com.ensak.connect.util.email.dto.EmailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Profile("dev")
@Slf4j
@Service
public class DevEmailService implements EmailService {

    @Autowired
    private JavaMailSender mail;

    @Override
    public Boolean sendEmail(EmailDTO email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email.getTo());
            message.setSubject(email.getSubject());
            message.setText(email.getContent());
            mail.send(message);
            return true;
        } catch (Exception ex) {
            log.error("sendEmail: Error sending email, ex: " + ex.getMessage());
            return false;
        }
    }
}
