package com.nicolai.lagermester.dto;

/**
 * DTO (Data Transfer Object) for å lese produktdata fra Magento sitt API.
 */
public class ProductDTO {

    private String sku;      // Unik produktkode
    private String name; //Navn på produktet
    private String thumbnailUrl; //Bilde av produktet
    private int quantity;

    public ProductDTO() {
        // Tom konstruktør nødvendig for JSON-deserialisering
    }

    public ProductDTO(String sku, String name, String thumbnailUrl, int quantity) {
        this.sku = sku;
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.quantity = quantity;
    }

    // Gettere og settere
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
