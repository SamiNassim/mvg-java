package com.saminassim.mvgjava.controller;

import com.saminassim.mvgjava.dto.JwtAuthenticationResponse;
import com.saminassim.mvgjava.dto.LoginRequest;
import com.saminassim.mvgjava.dto.SignupRequest;
import com.saminassim.mvgjava.exception.UserAlreadyExistsException;
import com.saminassim.mvgjava.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
       try {
           return ResponseEntity.ok(authenticationService.signup(signupRequest));
       } catch (UserAlreadyExistsException e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                   .body(e.getMessage());
       }
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }
}
