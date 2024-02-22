package com.majed.authentication.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @Email
    @NotEmpty(message = "email field is required")
    private String email;
    @NotEmpty(message = "password field is required")
    private String password;
}
