package org.icet.learn.dto;

import lombok.Data;

@Data
public class CartItem {
    private Long id;
    private Long productId;
    private String productName;
    private Long price;
    private Long quantity;
}
