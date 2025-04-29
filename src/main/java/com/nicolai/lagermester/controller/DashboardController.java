package com.nicolai.lagermester.controller;

import com.nicolai.lagermester.model.Product;
import com.nicolai.lagermester.model.ProductBatch;
import com.nicolai.lagermester.repository.ProductBatchRepository;
import com.nicolai.lagermester.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class DashboardController {

    private final ProductService productService;
    private final ProductBatchRepository productBatchRepository;

    // Konstruktør: Spring injiserer avhengighetene
    public DashboardController(ProductService productService, ProductBatchRepository productBatchRepository) {
        this.productService = productService;
        this.productBatchRepository = productBatchRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // 1. Hent ALLE produkter
        List<Product> products = productService.getAllProducts();

        // 2. Filtrer produkter med lav beholdning (<5)
        List<Product> lowStock = products.stream()
                .filter(p -> p.getQuantity() < 5)
                .toList();

        // 3. Hent batcher som utløper innen 30 dager
        List<ProductBatch> expiringBatches = productBatchRepository
                .findByExpirationDateBefore(LocalDate.now().plusDays(30));

        // 4. Formater tid for "sist oppdatert"
        String formattedTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

        // 5. Legg til i modellen for Thymeleaf
        model.addAttribute("products", products);
        model.addAttribute("lowStock", lowStock);
        model.addAttribute("expiringBatches", expiringBatches);
        model.addAttribute("lastUpdated", formattedTime);

        return "dashboard"; // Viser dashboard.html
    }
}
