package com.saminassim.mvgjava.controller;

import com.saminassim.mvgjava.dto.SignupRequest;
import com.saminassim.mvgjava.entity.User;
import com.saminassim.mvgjava.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<User> signup(@RequestBody SignupRequest signupRequest) {
       return ResponseEntity.ok(authenticationService.signup(signupRequest));
    }
}
