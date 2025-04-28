package com.nicolai.lagermester.scheduler;

import com.nicolai.lagermester.service.MagentoClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduler-klasse som automatisk synkroniserer produkter fra Magento hver natt.
 */
@Component
public class ProductSyncScheduler {

    private static final Logger logger = LoggerFactory.getLogger(ProductSyncScheduler.class);
    // Logger som brukes til å skrive ryddige meldinger i konsollen

    private final MagentoClientService magentoClientService;

    public ProductSyncScheduler(MagentoClientService magentoClientService) {
        this.magentoClientService = magentoClientService;
    }

    /**
     * Kjører automatisk hver natt kl 02:00.
     * Henter produkter fra Magento og lagrer dem i lokal database.
     */
    @Scheduled(cron = "0 0 2 * * *")
    public void syncProducts() {
        logger.info("Starter automatisk produkt-synkronisering fra Magento...");
        try {
            magentoClientService.syncProductsFromMagento();
            logger.info("Produkt-synkronisering fullført uten feil.");
        } catch (Exception e) {
            logger.error("Feil under produkt-synkronisering: ", e);
        }
    }
}
