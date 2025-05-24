package org.icet.learn.dto;

import lombok.Data;

@Data
public class PlaceOrder {

    private Long userId;

    private String address;

    private String orderDescription;

}
