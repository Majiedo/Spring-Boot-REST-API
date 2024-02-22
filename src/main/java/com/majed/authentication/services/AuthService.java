package com.majed.authentication.services;

import com.majed.authentication.domain.dto.LoginDto;
import com.majed.authentication.domain.dto.RegisterDto;
import com.majed.authentication.responses.AuthenticationResponse;

public interface AuthService {
    AuthenticationResponse register(RegisterDto registerDto);

    AuthenticationResponse login(LoginDto loginDto);
}
