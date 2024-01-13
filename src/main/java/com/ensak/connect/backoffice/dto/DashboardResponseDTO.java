package com.ensak.connect.backoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponseDTO {

    private String field;

    private Long count;

}
