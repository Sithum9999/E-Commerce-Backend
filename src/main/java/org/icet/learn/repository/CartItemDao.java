package org.icet.learn.repository;

import org.icet.learn.entity.CartItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemDao extends JpaRepository<CartItemsEntity, Long> {
    Optional<CartItemsEntity> findByProductEntityIdAndOrderEntityIdAndUserEntityId(Long productId, Long orderId, Long userId);
}
