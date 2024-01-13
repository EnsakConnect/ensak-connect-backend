package com.ensak.connect.auth.dto;

import com.ensak.connect.auth.model.User;
import com.ensak.connect.profile.model.util.ProfileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    private Integer id;

    private String email;

    private Boolean isActive;

    private Boolean isNotLocked;

    private Date activatedAt;

    private String fullName;

    private ProfileType profileType;

    public static UserResponseDTO map(User user){
        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .isActive(user.getIsActive())
                .isNotLocked(user.getIsNotLocked())
                .activatedAt(user.getActivatedAt())
                .fullName(user.getProfile().getFullName())
                .profileType(user.getProfile().getProfileType())
                .build();
    }

    public static List<UserResponseDTO> map(List<User> users){

        if (users == null) {
            return null;
        }

        List<UserResponseDTO> list = new ArrayList<UserResponseDTO>(users.size());
        for (User user : users) {
            list.add( map(user));
        }

        return list;
    }

    public static Page<UserResponseDTO> map(Page<User> userPage){
        return new PageImpl<>(map(userPage.getContent()), userPage.getPageable(), userPage.getTotalElements());
    }
}
