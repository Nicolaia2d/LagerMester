package com.nicolai.lagermester.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicolai.lagermester.dto.MagentoOrderItem;
import com.nicolai.lagermester.model.Product;
import com.nicolai.lagermester.model.ReplacedProductLog;
import com.nicolai.lagermester.repository.ProductRepository;
import com.nicolai.lagermester.repository.ReplacedProductLogRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service-klasse som håndterer all kommunikasjon med Magento API.
 * Den er også ansvarlig for å lagre eller oppdatere produktinformasjon i den lokale databasen.
 */
@Service
public class MagentoClientService {

    // Verktøy for HTTP-kall
    private final RestTemplate restTemplate;
    // For lagring og henting av produkter i databasen
    private final ProductRepository productRepository;
    // For logging av erstatningsprodukter når noe mangler
    private final ReplacedProductLogRepository replacedProductLogRepository;
    // Brukes for å konvertere JSON-respons fra API
    private final ObjectMapper objectMapper;

    // URL til Magento API (oppdater denne med korrekt verdi)
    private final String magentoBaseUrl = "https://din-butikk.no/rest/V1/";
    // Autentisering via Bearer Token (sett riktig token her)
    private final String bearerToken = "Ditt-Token-Her";

    /**
     * Konstruktør for å injisere nødvendige komponenter
     */
    public MagentoClientService(ProductRepository productRepository,
                                ReplacedProductLogRepository replacedProductLogRepository) {
        this.restTemplate = new RestTemplate();
        this.productRepository = productRepository;
        this.replacedProductLogRepository = replacedProductLogRepository;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Henter alle produkter fra Magento sitt API og lagrer dem lokalt i databasen.
     * Produktene får standardverdier for lagerbeholdning og bilde-URL hvis ikke oppgitt.
     */
    public void syncProductsFromMagento() {
        String url = magentoBaseUrl + "products?searchCriteria=";

        // Legger til nødvendige headere som Bearer-token og Content-Type
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + bearerToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Utfører API-kall
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        // Hvis kallet lykkes
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode items = root.path("items");

                List<Product> products = new ArrayList<>();

                // Går gjennom alle produktene
                for (JsonNode item : items) {
                    String sku = item.path("sku").asText();
                    String name = item.path("name").asText();

                    Product product = new Product();
                    product.setSku(sku);
                    product.setName(name);
                    product.setQuantity(0); // Lageret starter på 0 lokalt
                    product.setThumbnailUrl(""); // Tom URL foreløpig

                    products.add(product);
                }

                // Lagrer alle i én operasjon
                productRepository.saveAll(products);

            } catch (Exception e) {
                throw new RuntimeException("Feil ved parsing av Magento-data", e);
            }
        } else {
            throw new RuntimeException("Feil ved henting av produkter: " + response.getStatusCode());
        }
    }

    /**
     * Går gjennom ordrelinjer fra Magento og logger produkter som mangler på lager.
     * Denne metoden brukes til å vise "mangler varer i ordre" til sjef/admin.
     *
     * @param ordreFraMagento Liste av produkter fra en ordre hentet fra Magento
     */
    public void logMissingItemsFromOrder(List<MagentoOrderItem> ordreFraMagento) {
        for (MagentoOrderItem item : ordreFraMagento) {
            // Søk etter produktet lokalt basert på SKU
            Optional<Product> produktOpt = productRepository.findById(item.getSku());

            if (produktOpt.isPresent()) {
                Product produkt = produktOpt.get();

                // Hvis produktet finnes, men er tomt på lager, logg det som en manglende vare
                if (produkt.getQuantity() == 0) {
                    ReplacedProductLog logg = new ReplacedProductLog();
                    logg.setOriginalSku(produkt.getSku());
                    logg.setReplacementSku(""); // Ikke valgt erstatning enda
                    logg.setReplacementComment("Produkt mangler på lager, trenger erstatning.");
                    logg.setOrderId(item.getOrder_id());
                    logg.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));

                    replacedProductLogRepository.save(logg); // Lagre i loggtabellen
                }
            }
        }
    }
}
