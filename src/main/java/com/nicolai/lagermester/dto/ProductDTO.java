package com.nicolai.lagermester.dto;

/**
 * DTO (Data Transfer Object) for å lese produktdata fra Magento sitt API.
 */
public class ProductDTO {

    private String sku;      // Unik produktkode
    private String name;     // Navn på produktet

    public ProductDTO() {
        // Tom konstruktør nødvendig for JSON-deserialisering
    }

    public ProductDTO(String sku, String name) {
        this.sku = sku;
        this.name = name;
    }

    // Gettere og settere
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
