package org.icet.learn.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderedProductsResponse {

    private List<Product> productDtoList;

    private Long orderAmount;

}
