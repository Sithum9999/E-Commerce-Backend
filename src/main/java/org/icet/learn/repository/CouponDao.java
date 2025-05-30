package org.icet.learn.repository;

import org.icet.learn.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponDao extends JpaRepository<CouponEntity, Long> {

    boolean existsByCode(String code);

    Optional<CouponEntity> findByCode(String code);

}
