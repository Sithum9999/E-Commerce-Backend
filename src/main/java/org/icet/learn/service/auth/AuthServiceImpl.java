package org.icet.learn.service.auth;

import lombok.RequiredArgsConstructor;
import org.icet.learn.dto.SignupRequest;
import org.icet.learn.dto.User;
import org.icet.learn.entity.UserEntity;
import org.icet.learn.enums.UserRole;
import org.icet.learn.repository.UserDao;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private UserDao userDao;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User createUser(SignupRequest signupRequest) {
        UserEntity user = new UserEntity();

        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setRole(UserRole.CUSTOMER);

        UserEntity createdUser = userDao.save(user);

        User userDto = new User();
        userDto.setId(createdUser.getId());
        userDto.setName(createdUser.getName());
        userDto.setEmail(createdUser.getEmail());
        userDto.setUserRole(createdUser.getRole());

        return userDto;
    }

    @Override
    public Boolean hasUserWithEmail(String email) {
        return userDao.findFirstByEmail(email).isPresent();
    }

}
