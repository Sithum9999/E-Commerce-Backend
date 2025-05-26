package org.icet.learn.dto;

import lombok.Data;

@Data
public class Faq {

    private Long id;

    private String question;

    private String answer;

    private Long productId;

}
