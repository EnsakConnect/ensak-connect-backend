package com.ensak.connect.email;


import com.ensak.connect.email.dto.EmailDTO;

public interface EmailService {
    Boolean sendEmail(EmailDTO email);
}