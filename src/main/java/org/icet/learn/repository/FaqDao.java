package org.icet.learn.repository;

import org.icet.learn.entity.FaqEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqDao extends JpaRepository<FaqEntity, Long> {

}
