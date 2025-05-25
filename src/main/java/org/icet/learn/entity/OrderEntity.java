package org.icet.learn.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.icet.learn.dto.Order;
import org.icet.learn.enums.OrderStatus;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderDescription;

    private Date date;

    private Long amount;

    private String address;

    private String payment;

    private OrderStatus orderStatus;

    private Long totalAmount;

    private Long discount;

    private UUID trackingId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "coupon_id", referencedColumnName = "id")
    private CouponEntity coupon;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<CartItemsEntity> cartItems;

    public Order getOrderDto() {
        Order order = new Order();

        order.setId(id); //
        order.setOrderDescription(orderDescription);
        order.setAddress(address);
        order.setTrackingId(trackingId);
        order.setAmount(amount);
        order.setDate(date);
        order.setOrderStatus(orderStatus);
        order.setUsername(user.getName());

        if (coupon != null) {
            order.setCouponName(coupon.getName());
        }

        return order;
    }

}

