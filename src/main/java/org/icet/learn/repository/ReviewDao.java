package org.icet.learn.repository;

import org.icet.learn.dto.Review;
import org.icet.learn.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewDao extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findAllByProductId(Long productsId);

}
