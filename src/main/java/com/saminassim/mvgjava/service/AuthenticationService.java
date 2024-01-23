package com.saminassim.mvgjava.service;

import com.saminassim.mvgjava.dto.SignupRequest;
import com.saminassim.mvgjava.entity.User;

public interface AuthenticationService {

    User signup(SignupRequest signupRequest);
}
