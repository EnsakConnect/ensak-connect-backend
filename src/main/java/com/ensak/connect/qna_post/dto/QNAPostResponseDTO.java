package com.ensak.connect.qna_post.dto;

import com.ensak.connect.qna_post.QNAPost;
import com.ensak.connect.user.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class QNAPostResponseDTO {
    private Integer id;
    private String question;
    private UserResponseDTO author;
    private Date createdAt;
    private Date updatedAt;

    public static QNAPostResponseDTO map(QNAPost qnaPost) {
        return QNAPostResponseDTO.builder()
                .id(qnaPost.getId())
                .question(qnaPost.getQuestion())
                .author(UserResponseDTO.map(qnaPost.getAuthor()))
                .createdAt(qnaPost.getCreatedAt())
                .updatedAt(qnaPost.getUpdatedAt())
                .build();
    }
}
