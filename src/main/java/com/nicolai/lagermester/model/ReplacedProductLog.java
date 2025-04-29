// Dette er en mulig implementasjon av «ErstattedeProdukter»-tabellen og logikk

package com.nicolai.lagermester.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ReplacedProductLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalSku;         // Varen som ikke finnes lenger
    private String replacementSku;      // Hva du sendte i stedet
    private String replacementComment;  // Kommentar eller forslag
    private String orderId;             // Referanse til bestilling
    private String timestamp;           // Når dette ble loggført

    // Gettere og settere
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOriginalSku() { return originalSku; }
    public void setOriginalSku(String originalSku) { this.originalSku = originalSku; }

    public String getReplacementSku() { return replacementSku; }
    public void setReplacementSku(String replacementSku) { this.replacementSku = replacementSku; }

    public String getReplacementComment() { return replacementComment; }
    public void setReplacementComment(String replacementComment) { this.replacementComment = replacementComment; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
