package org.icet.learn.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.icet.learn.dto.Review;

@Entity
@Data
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long rating;

    @Lob
    private String description;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductEntity product;

    public Review getDto() {
        Review reviewDto = new Review();

        reviewDto.setId(id);
        reviewDto.setRating(rating);
        reviewDto.setDescription(description);
        reviewDto.setReturnedImg(img);
        reviewDto.setProductId(product.getId());
        reviewDto.setUserId(user.getId());
        reviewDto.setUsername(user.getName());

        return reviewDto;
    }

}
