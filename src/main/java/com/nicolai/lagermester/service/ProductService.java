package com.nicolai.lagermester.service;

import com.nicolai.lagermester.model.Product;
import com.nicolai.lagermester.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    // Constructor Injection (best practice i Spring)
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Henter alle produkter fra databasen
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Lagrer et nytt produkt eller oppdaterer et eksisterende
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // Oppdaterer lagerbeholdningen til et produkt basert på SKU
    public Product updateQuantity(String sku, int newQuantity) {
        Product product = productRepository.findById(sku)
                .orElseThrow(() -> new NoSuchElementException("Fant ikke produkt med SKU: " + sku));

        product.setQuantity(newQuantity);
        return productRepository.save(product);
    }
}
