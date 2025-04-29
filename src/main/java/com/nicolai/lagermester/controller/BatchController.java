package com.nicolai.lagermester.controller;

import com.nicolai.lagermester.model.Product;
import com.nicolai.lagermester.model.ProductBatch;
import com.nicolai.lagermester.repository.ProductBatchRepository;
import com.nicolai.lagermester.repository.ProductRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/batches")
public class BatchController {

    private final ProductRepository productRepo;
    private final ProductBatchRepository batchRepo;

    public BatchController(ProductRepository productRepo, ProductBatchRepository batchRepo) {
        this.productRepo = productRepo;
        this.batchRepo = batchRepo;
    }

    @PostMapping("/save")
    public String saveBatch(@RequestParam String sku,
                            @RequestParam int quantity,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expirationDate) {

        Product product = productRepo.findById(sku).orElseThrow(); // Nå matcher datatypene!
        ProductBatch batch = new ProductBatch(sku, quantity, expirationDate, product);
        batchRepo.save(batch);

        return "redirect:/products";
    }

}
