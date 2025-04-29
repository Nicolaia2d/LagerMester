package com.nicolai.lagermester.model;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    @Id
    private String sku; // Unik nøkkel, bruker SKU fra Magento

    private String name;
    private int quantity; // Intern beholdning
    private String thumbnailUrl; // Bilde av produktet

    // Konstruktører
    public Product() {}

    public Product(String sku, String name, int quantity, String thumbnailUrl) {
        this.sku = sku;
        this.name = name;
        this.quantity = quantity;
        this.thumbnailUrl = thumbnailUrl;
    }

    // Gettere og settere
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
}
