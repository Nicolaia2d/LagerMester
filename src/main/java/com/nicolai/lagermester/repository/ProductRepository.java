package com.nicolai.lagermester.repository;
import com.nicolai.lagermester.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, String> {
    // Ingenting å skrive her nå – JpaRepository gir oss alt vi trenger!
    List<Product> findByNameContainingIgnoreCase(String name);

}