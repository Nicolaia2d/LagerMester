package com.nicolai.lagermester.controller;

import com.nicolai.lagermester.dto.ProductDTO;
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
        return "products"; // Viser templates/product-list.html
    }

    /**
     * Oppdaterer lagerbeholdningen på et produkt.
     * POST /products/update
     */
    @GetMapping("/update")
    public String showUpdateProductForm() {
        return "product-update"; // viser templates/product-update.html
    }

    @PostMapping("/update")
    public String updateProduct(@RequestParam String sku, @RequestParam int quantity) {
        productService.updateQuantity(sku, quantity); // Oppdaterer antall i databasen
        return "redirect:/products"; // Etter oppdatering, gå tilbake til produktlisten
    }
    @GetMapping("/search")
    @ResponseBody
    public List<ProductDTO> searchProducts(@RequestParam("query") String query) {
        List<Product> matches = productService.searchByName(query);
        return matches.stream()
                .map(p -> new ProductDTO(p.getSku(), p.getName(), p.getThumbnailUrl(), p.getQuantity()))
                .toList();
    }


    // Viser skjema for å legge til nytt produkt
    @GetMapping("/add")
    public String showAddProductForm() {
        return "product-add"; // product-add.html
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
