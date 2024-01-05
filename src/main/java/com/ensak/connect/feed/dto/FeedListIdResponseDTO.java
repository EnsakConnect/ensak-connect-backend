package com.ensak.connect.feed.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class FeedListIdResponseDTO {

    private List<Integer> jobPostIds;

    private List<Integer> questionPostIds;

    private List<Integer> blogPostIds;

}
