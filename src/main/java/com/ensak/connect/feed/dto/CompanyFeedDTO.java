package com.ensak.connect.feed.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CompanyFeedDTO {

    private String companyName;

    private String location;

    private String logo;
}
