package com.majed.authentication.services.impl;

import com.majed.authentication.domain.dto.LoginDto;
import com.majed.authentication.domain.dto.RegisterDto;
import com.majed.authentication.domain.entities.Role;
import com.majed.authentication.domain.entities.UserEntity;
import com.majed.authentication.repositories.UserRepository;
import com.majed.authentication.responses.AuthenticationResponse;
import com.majed.authentication.services.AuthService;
import com.majed.authentication.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterDto registerDto) {
        if (userRepository.findByEmail(registerDto.getEmail().toLowerCase()).isPresent()) {
            // throw user exists
        }
        UserEntity user = UserEntity
                .builder()
                .name(registerDto.getName())
                .email(registerDto.getEmail().toLowerCase())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthenticationResponse login(LoginDto loginDto) {
        String email = loginDto.getEmail().toLowerCase();
        String password = loginDto.getPassword();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        UserEntity user = userRepository.findByEmail(email).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
