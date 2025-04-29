package com.nicolai.lagermester.dto;

/**
 * Representerer én ordrelinje hentet fra Magento.
 */
public class MagentoOrderItem {

    private String sku;         // Produktets SKU
    private String order_id;    // Ordre-ID fra Magento

    public MagentoOrderItem() {}

    public MagentoOrderItem(String sku, String order_id) {
        this.sku = sku;
        this.order_id = order_id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
