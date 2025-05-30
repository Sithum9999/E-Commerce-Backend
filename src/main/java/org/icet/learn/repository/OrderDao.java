package org.icet.learn.repository;

import org.icet.learn.entity.OrderEntity;
import org.icet.learn.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderDao extends JpaRepository<OrderEntity, Long> {

    OrderEntity findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);

    List<OrderEntity> findAllByOrderStatusIn(List<OrderStatus> orderStatusList);

    List<OrderEntity> findByUserIdAndOrderStatusIn(Long userId, List<OrderStatus> orderStatus);

    Optional<OrderEntity> findByTrackingId(UUID trackingId);

    List<OrderEntity> findByDateBetweenAndOrderStatus(Date startOfMonth, Date endOfMonth, OrderStatus status);

    Long countByOrderStatus(OrderStatus status);
}
