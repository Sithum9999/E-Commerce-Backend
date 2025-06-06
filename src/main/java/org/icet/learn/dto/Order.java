package org.icet.learn.dto;


import lombok.Data;
import org.icet.learn.enums.OrderStatus;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class Order {

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

    private String username;

    private List<CartItem> cartItems;

    private String couponName;
}
