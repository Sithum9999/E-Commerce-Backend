package org.icet.learn.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductDetail {

    private Product productDto;

    private List<Review> reviewDtoList;

    private List<Faq> faqDtoList;

}
