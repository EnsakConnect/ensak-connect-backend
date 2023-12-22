package com.ensak.connect.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedPageResponseDTO {

    private FeedListIdResponseDTO listIds;

    private PageRequest page;

    private Long totals;

}
