package com.ensak.connect.util.email;

import com.ensak.connect.util.email.dto.EmailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


@Profile("test")
@Slf4j
@Service
public class TestEmailService implements EmailService {

    @Override
    public Boolean sendEmail(EmailDTO email) {
        return true;
    }
}
