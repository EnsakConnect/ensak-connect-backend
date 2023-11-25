package com.ensak.connect.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Test {

    @Id
    @SequenceGenerator(
            name = "test_id_sequence",
            sequenceName = "test_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "test_id_sequence"
    )
    private Integer id;
    private String test;
}
