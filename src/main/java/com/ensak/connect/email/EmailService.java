package com.ensak.connect.email;


import com.ensak.connect.email.dto.EmailDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mail;

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