package com.ensak.connect.feed.dto;

import com.ensak.connect.blog_post.model.CommentPost;
import com.ensak.connect.profile.dto.ProfileResponseDTO;
import com.ensak.connect.question_post.model.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.List;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntercationResponseDTO {

    private Integer id;

    private String content;

    private ProfileResponseDTO author;

    private String timePassed;

    public static  IntercationResponseDTO map(CommentPost interaction) {
        PrettyTime prettyTime = new PrettyTime();
        return IntercationResponseDTO.builder()
                .id(interaction.getId())
                .content(interaction.getContent())
                .author(ProfileResponseDTO.mapToDTO(interaction.getAuthor().getProfile()))
                .timePassed(prettyTime.format(interaction.getUpdatedAt()))
                .build();
    }

    public static List<IntercationResponseDTO> mapComments(List<CommentPost> interactions) {
        if (interactions == null) {
            return null;
        }
        List<IntercationResponseDTO> list = new ArrayList<IntercationResponseDTO>(interactions.size());
        for (CommentPost interaction : interactions) {
            list.add( map(interaction));
        }
        return list;
    }

    public static  IntercationResponseDTO map(Answer interaction) {
        PrettyTime prettyTime = new PrettyTime();
        return IntercationResponseDTO.builder()
                .id(interaction.getId())
                .content(interaction.getContent())
                .author(ProfileResponseDTO.mapToDTO(interaction.getAuthor().getProfile()))
                .timePassed(prettyTime.format(interaction.getUpdatedAt()))
                .build();
    }

    public static List<IntercationResponseDTO> mapAnswers(List<Answer> interactions) {
        if (interactions == null) {
            return null;
        }
        List<IntercationResponseDTO> list = new ArrayList<IntercationResponseDTO>(interactions.size());
        for (Answer interaction : interactions) {
            list.add( map(interaction));
        }
        return list;
    }
}
