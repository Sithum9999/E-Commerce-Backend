package org.icet.learn.repository;

import org.icet.learn.dto.Review;
import org.icet.learn.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewDao extends JpaRepository<ReviewEntity, Long> {



}
