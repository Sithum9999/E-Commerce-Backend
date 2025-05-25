package org.icet.learn.repository;

import org.icet.learn.entity.OrderEntity;
import org.icet.learn.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<OrderEntity, Long> {
    OrderEntity findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);
    List<OrderEntity> findAllByOrderStatusIn(List<OrderStatus> orderStatusList);
}
