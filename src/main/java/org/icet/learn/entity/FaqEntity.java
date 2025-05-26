package org.icet.learn.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.icet.learn.dto.Faq;

@Data
@Entity(name = "Faq")
public class FaqEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String question;

        private String answer;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "product_id", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        private ProductEntity product;

        public Faq getFaqDto(){

           Faq faqDto = new Faq();
           faqDto.setId(id);
           faqDto.setQuestion(question);
           faqDto.setAnswer(question);
           faqDto.setProductId(product.getId());

           return faqDto;
        }

}
