package org.icet.learn.service.jwt;


import lombok.RequiredArgsConstructor;
import org.icet.learn.entity.UserEntity;
import org.icet.learn.repository.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUser = userDao.findFirstByEmail(username);
        if (optionalUser.isEmpty())
            throw new UsernameNotFoundException("Username not found", null);

        return new org.springframework.security.core.userdetails.User(
                optionalUser.get().getEmail(),
                optionalUser.get().getPassword(),
                new ArrayList<>()
        );
    }
}
