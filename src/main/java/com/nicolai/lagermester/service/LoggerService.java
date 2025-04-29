package com.nicolai.lagermester.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * LoggerService holder oversikt over ulike hendelser i systemet som skal vises i admin-status.
 */
@Service
public class LoggerService {

    private final List<String> apiLogs = new ArrayList<>();
    private final List<String> emailLogs = new ArrayList<>();
    private final List<String> orderLogs = new ArrayList<>();
    private final List<String> userLogs = new ArrayList<>();
    private final List<String> orderPlacementLogs = new ArrayList<>();

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public void logApiSync(String message) {
        apiLogs.add(getTimestamp() + " - " + message);
    }

    public void logEmail(String message) {
        emailLogs.add(getTimestamp() + " - " + message);
    }

    public void logOrder(String message) {
        orderLogs.add(getTimestamp() + " - " + message);
    }

    public void logUserAction(String message) {
        userLogs.add(message); // Brukere logger gjerne med navn og handling
    }

    public void logOrderPlacement(String message) {
        orderPlacementLogs.add(message);
    }

    // Hjelpemetode for å generere tidsstempel
    private String getTimestamp() {
        return LocalDateTime.now().format(formatter);
    }

    // Gettere for Thymeleaf
    public List<String> getApiLogs() {
        return apiLogs;
    }

    public List<String> getEmailLogs() {
        return emailLogs;
    }

    public List<String> getOrderLogs() {
        return orderLogs;
    }

    public List<String> getUserLogs() {
        return userLogs;
    }

    public List<String> getOrderPlacementLogs() {
        return orderPlacementLogs;
    }
}