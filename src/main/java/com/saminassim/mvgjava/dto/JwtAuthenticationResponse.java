package com.saminassim.mvgjava.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class JwtAuthenticationResponse {

    private String token;
    private String refreshToken;
    private UUID userId;
}
