package org.icet.learn.repository;


import org.icet.learn.entity.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistDao extends JpaRepository<WishlistEntity, Long> {

    List<WishlistEntity> findAllByUserId(Long userId);

}
