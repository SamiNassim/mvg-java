package com.saminassim.mvgjava.service;

import com.saminassim.mvgjava.dto.JwtAuthenticationResponse;
import com.saminassim.mvgjava.dto.LoginRequest;
import com.saminassim.mvgjava.dto.RefreshTokenRequest;
import com.saminassim.mvgjava.dto.SignupRequest;
import com.saminassim.mvgjava.entity.User;

public interface AuthenticationService {

    User signup(SignupRequest signupRequest);
    JwtAuthenticationResponse login(LoginRequest loginRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
