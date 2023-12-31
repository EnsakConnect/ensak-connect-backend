package com.ensak.connect.util.email.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailDTO {
    private String to;
    private String subject;
    private String content;
}
