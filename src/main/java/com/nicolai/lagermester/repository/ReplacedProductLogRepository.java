package com.nicolai.lagermester.repository;

import com.nicolai.lagermester.model.ReplacedProductLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplacedProductLogRepository extends JpaRepository<ReplacedProductLog, Long> {
    List<ReplacedProductLog> findByOrderId(String orderId);
}
