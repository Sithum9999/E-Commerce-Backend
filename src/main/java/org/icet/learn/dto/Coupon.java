package org.icet.learn.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Coupon {

    private Long id;

    private String name;

    private String code;

    private Long discount;

    private Date expirationDate;

}
