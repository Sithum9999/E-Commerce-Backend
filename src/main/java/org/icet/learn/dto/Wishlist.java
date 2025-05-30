package org.icet.learn.dto;

import lombok.Data;

@Data
public class Wishlist {

    private Long id;

    private Long productId;

    private Long userId;

    private String productName;

    private String productDescription;

    private byte[] returnedImg;

    private Long price;

}
