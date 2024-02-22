package com.majed.authentication.domain.dto;

import com.majed.authentication.domain.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Integer id;
    private String name;
    private String email;
    private Timestamp createdAt;
    private Role role;

}
