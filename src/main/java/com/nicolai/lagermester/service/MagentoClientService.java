package com.nicolai.lagermester.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicolai.lagermester.model.Product;
import com.nicolai.lagermester.repository.ProductRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Service-klasse som håndterer kommunikasjon med Magento sitt REST API
 * og lagrer produkter i lokal database.
 */
@Service
public class MagentoClientService {

    private final RestTemplate restTemplate;            // Brukes for å sende HTTP-forespørsler
    private final ProductRepository productRepository;  // For å lagre produkter i databasen
    private final ObjectMapper objectMapper;             // For å parse JSON-responsen

    // Grunn-URL til Magento API. Må oppdateres til din egen butikk.
    private final String magentoBaseUrl = "https://din-butikk.no/rest/V1/";

    // API-nøkkel (token) for autentisering mot Magento. Må settes riktig.
    private final String bearerToken = "Ditt-Token-Her";

    /**
     * Konstruktør som setter opp nødvendige verktøy.
     * RestTemplate for HTTP-kall, ObjectMapper for JSON-parsing,
     * og Repository for lagring.
     */
    public MagentoClientService(ProductRepository productRepository) {
        this.restTemplate = new RestTemplate();
        this.productRepository = productRepository;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Henter alle produkter fra Magento sitt API, parser resultatet,
     * og lagrer produktene i lokal database.
     */
    public void syncProductsFromMagento() {
        // Setter opp riktig URL for å hente alle produkter
        String url = magentoBaseUrl + "products?searchCriteria=";

        // Setter opp HTTP-headere inkludert autentisering med Bearer Token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + bearerToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Lager en HTTP-request som bare inneholder headere (ingen body for GET)
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Utfører et GET-kall til Magento API
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        // Hvis vi får en OK-respons (HTTP 200), behandler vi dataen
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                // Leser JSON-responsen
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode items = root.path("items"); // Finner "items" i JSON-objektet

                List<Product> products = new ArrayList<>();

                // Går gjennom alle produktene i responsen
                for (JsonNode item : items) {
                    String sku = item.path("sku").asText();    // Leser SKU (unik produktkode)
                    String name = item.path("name").asText();  // Leser produktnavn

                    // Lager nytt Product-objekt basert på data fra API
                    Product product = new Product();
                    product.setSku(sku);
                    product.setName(name);
                    product.setQuantity(0);         // Starter på 0 på lager i lokal database
                    product.setThumbnailUrl("");    // Kan senere oppdateres med bilde-URL

                    products.add(product);          // Legger produktet til i listen
                }

                // Lagre alle produkter i databasen i én operasjon
                productRepository.saveAll(products);

            } catch (Exception e) {
                // Feil ved parsing av JSON-data
                throw new RuntimeException("Feil ved parsing av Magento-data", e);
            }
        } else {
            // Hvis API-kallet feiler, kaster vi en feilmelding
            throw new RuntimeException("Feil ved henting av produkter: " + response.getStatusCode());
        }
    }
}
