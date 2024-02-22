package com.majed.authentication.domain.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterDto {

    @NotEmpty(message = "name field is required")
    private String name;

    @Email
    @NotEmpty(message = "email field is required")
    private String email;


    @NotEmpty(message = "password field is required")
    private String password;
}
