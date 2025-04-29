package com.nicolai.lagermester.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class StatusService {

    private LocalDateTime lastMagentoSync;
    private LocalDateTime lastEmailProcessed;
    private String nextExpectedDelivery;
    private String lastOrderDescription;
    private int lowStockCount;
    private int missingDataCount;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    // Dummydata for test og utvikling
    @PostConstruct
    public void initTestData() {
        this.lastMagentoSync = LocalDateTime.now().minusHours(2);
        this.lastEmailProcessed = LocalDateTime.now().minusHours(5);
        this.nextExpectedDelivery = "03.05.2025 (tolket fra e-post)";
        this.lastOrderDescription = "Inza Espresso 1kg - 01.05.2025 09:15";
        this.lowStockCount = 4;
        this.missingDataCount = 2;
    }

    public void updateMagentoSyncTime() {
        this.lastMagentoSync = LocalDateTime.now();
    }

    public void updateEmailProcessedTime() {
        this.lastEmailProcessed = LocalDateTime.now();
    }

    public void setNextExpectedDelivery(String delivery) {
        this.nextExpectedDelivery = delivery;
    }

    public void setLastOrderDescription(String description) {
        this.lastOrderDescription = description;
    }

    public void setLowStockCount(int count) {
        this.lowStockCount = count;
    }

    public void setMissingDataCount(int count) {
        this.missingDataCount = count;
    }

    public String getLastMagentoSyncFormatted() {
        return Optional.ofNullable(lastMagentoSync).map(formatter::format).orElse("Ikke synket ennå");
    }

    public String getLastEmailProcessedFormatted() {
        return Optional.ofNullable(lastEmailProcessed).map(formatter::format).orElse("Ikke behandlet ennå");
    }

    public String getNextExpectedDelivery() {
        return nextExpectedDelivery != null ? nextExpectedDelivery : "Ingen info";
    }

    public String getLastOrderDescription() {
        return lastOrderDescription != null ? lastOrderDescription : "Ingen bestilling logget";
    }

    public int getLowStockCount() {
        return lowStockCount;
    }

    public int getMissingDataCount() {
        return missingDataCount;
    }
}