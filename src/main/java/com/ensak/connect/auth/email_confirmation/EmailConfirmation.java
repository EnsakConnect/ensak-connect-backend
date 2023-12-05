package com.ensak.connect.auth.email_confirmation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "email_confirmations")
public class EmailConfirmation {

    @Id
    @GeneratedValue
    private Integer id;

    private String email;

    private String code;
}
