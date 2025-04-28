package com.nicolai.lagermester.controller;

import com.nicolai.lagermester.service.MagentoClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/magento")
public class MagentoController {

    private final MagentoClientService magentoClientService;

    public MagentoController(MagentoClientService magentoClientService) {
        this.magentoClientService = magentoClientService;
    }

    @GetMapping("/sync")
    public String syncProducts() {
        magentoClientService.syncProductsFromMagento();
        return "Produkter hentet og lagret!";
    }
}
