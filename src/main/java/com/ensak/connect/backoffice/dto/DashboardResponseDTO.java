package com.ensak.connect.backoffice.dto;

import com.ensak.connect.profile.model.util.ProfileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponseDTO {

    private List<String> field;

    private List<Long> count;

    public static DashboardResponseDTO map(List<DashboardSingleObjectDTO> objects){
        List<String> fields = new ArrayList<>();
        List<Long> counts = new ArrayList<>();

        for(DashboardSingleObjectDTO object : objects){
            fields.add(object.getField());
            counts.add(object.getCount());
        }

        return DashboardResponseDTO.builder()
                .field(fields)
                .count(counts)
                .build();
    }

    public static DashboardResponseDTO mapO(List<Object[]> objects){
        List<String> fields = new ArrayList<>();
        List<Long> counts = new ArrayList<>();

        for(Object[] object : objects){
            fields.add(String.valueOf(object[0]));
            counts.add((Long) object[1]);
        }

        return DashboardResponseDTO.builder()
                .field(fields)
                .count(counts)
                .build();
    }


}
