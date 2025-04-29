package com.nicolai.lagermester.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class ProductBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku; // SKU til produktet
    private int quantity; // Antall i batchen
    private LocalDate expirationDate; // Best før-dato

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // Konstruktører
    public ProductBatch() {}

    public ProductBatch(String sku, int quantity, LocalDate expirationDate, Product product) {
        this.sku = sku;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
        this.product = product;
    }

    // Getters og setters
    public Long getId() { return id; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public LocalDate getExpirationDate() { return expirationDate; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}
