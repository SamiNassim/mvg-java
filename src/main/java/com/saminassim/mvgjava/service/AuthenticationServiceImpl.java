package com.saminassim.mvgjava.service;

import com.saminassim.mvgjava.dto.SignupRequest;
import com.saminassim.mvgjava.entity.Role;
import com.saminassim.mvgjava.entity.User;
import com.saminassim.mvgjava.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signup(SignupRequest signupRequest) {
        User user = new User();

        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setRole(Role.USER);

        return userRepository.save(user);
    }
}
