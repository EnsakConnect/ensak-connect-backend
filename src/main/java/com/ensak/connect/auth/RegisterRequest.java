package com.ensak.connect.auth;

import com.ensak.connect.enumeration.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Firstname shouldn't be blank")
    @Size(min = 3, max = 30, message = "Please enter a valid firstname")
    private String firstname;

    @NotBlank(message = "Lastname shouldn't be blank")
    @Size(min = 3, max = 30, message = "Please enter a valid lastname")
    private String lastname;

    @Email(message = "Please enter a valid email")
    private String email;

    @NotBlank(message = "Password shouldn't be blank")
    @Size(min = 6, max = 18, message = "Password should be 6 to 18 characters")
    private String password;

    @NotBlank(message = "Role shouldn't be blank")
    private Role role;

}
