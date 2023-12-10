package com.ensak.connect.util.email;


import com.ensak.connect.util.email.dto.EmailDTO;

public interface EmailService {
    Boolean sendEmail(EmailDTO email);
}