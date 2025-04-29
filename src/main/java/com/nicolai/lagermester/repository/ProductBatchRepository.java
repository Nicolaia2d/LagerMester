package com.nicolai.lagermester.repository;

import com.nicolai.lagermester.model.ProductBatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ProductBatchRepository extends JpaRepository<ProductBatch, Long> {
    List<ProductBatch> findByExpirationDateBefore(LocalDate date); // F.eks. for varer som går ut snart
}
