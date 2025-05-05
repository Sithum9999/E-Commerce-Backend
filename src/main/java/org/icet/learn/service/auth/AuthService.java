package org.icet.learn.service.auth;

import org.icet.learn.dto.SignupRequest;
import org.icet.learn.dto.User;

public interface AuthService {

    User createUser(SignupRequest signupRequest);

    Boolean hasUserWithEmail(String email);
}
