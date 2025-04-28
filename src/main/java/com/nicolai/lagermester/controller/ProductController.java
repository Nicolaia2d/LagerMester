package com.nicolai.lagermester.controller;

import com.nicolai.lagermester.model.Product;
import com.nicolai.lagermester.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller-klasse som håndterer HTTP-forespørsler relatert til produkter.
 * Kobler frontend (HTML-sider) mot backend (service-laget).
 */
@Controller
@RequestMapping("/products") // Alle endepunkter under /products
public class ProductController {

    private final ProductService productService;

    // Konstruktør: Spring injiserer ProductService automatisk
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Viser liste over alle produkter.
     * GET /products → viser product-list.html
     */
    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productService.getAllProducts(); // Henter alle produkter fra databasen
        model.addAttribute("products", products); // Legger produkter til i modellen for Thymeleaf
        return "product-list"; // Viser templates/product-list.html
    }

    /**
     * Oppdaterer lagerbeholdningen på et produkt.
     * POST /products/update
     */
    @PostMapping("/update")
    public String updateProduct(@RequestParam String sku, @RequestParam int quantity) {
        productService.updateQuantity(sku, quantity); // Oppdaterer antall i databasen
        return "redirect:/products"; // Etter oppdatering, gå tilbake til produktlisten
    }

    /**
     * Lagrer et nytt produkt i databasen.
     * POST /products/save
     */
    @PostMapping("/save")
    public String saveProduct(@RequestParam String sku,
                              @RequestParam String name,
                              @RequestParam int quantity,
                              @RequestParam(required = false) String thumbnailUrl) {
        // Lager nytt produkt basert på skjema-data
        Product product = new Product();
        product.setSku(sku);
        product.setName(name);
        product.setQuantity(quantity);
        product.setThumbnailUrl(thumbnailUrl != null ? thumbnailUrl : ""); // Setter tom streng hvis thumbnail er null

        productService.saveProduct(product); // Lagrer produktet via service
        return "redirect:/products"; // Etter lagring, gå tilbake til produktlisten
    }
}
