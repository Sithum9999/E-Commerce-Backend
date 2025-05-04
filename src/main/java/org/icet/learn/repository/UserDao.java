package org.icet.learn.repository;

import org.icet.learn.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findFirstByEmail(String username);
}
