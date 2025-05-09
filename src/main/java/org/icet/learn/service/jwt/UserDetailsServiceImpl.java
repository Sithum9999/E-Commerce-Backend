package org.icet.learn.service.jwt;


import lombok.RequiredArgsConstructor;
import org.icet.learn.entity.UserEntity;
import org.icet.learn.repository.UserDao;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userDao.findFirstByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        return new User(
                userEntity.getEmail(),
                userEntity.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().name()))
        );
    }

}
