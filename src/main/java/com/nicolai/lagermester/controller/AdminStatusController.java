package com.nicolai.lagermester.controller;

import com.nicolai.lagermester.service.LoggerService;
import com.nicolai.lagermester.service.StatusService;
import com.nicolai.lagermester.model.ReplacedProductLog;
import com.nicolai.lagermester.repository.ReplacedProductLogRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminStatusController {

    private final StatusService statusService;
    private final LoggerService loggerService;
    private final ReplacedProductLogRepository replacedProductLogRepository;

    public AdminStatusController(StatusService statusService, LoggerService loggerService,
                                 ReplacedProductLogRepository replacedProductLogRepository) {
        this.statusService = statusService;
        this.loggerService = loggerService;
        this.replacedProductLogRepository = replacedProductLogRepository;
    }

    @GetMapping("/admin/status")
    public String showAdminStatus(Model model) {

        // Henter systemdata
        model.addAttribute("lastMagentoSync", statusService.getLastMagentoSyncFormatted());
        model.addAttribute("lastEmailProcessed", statusService.getLastEmailProcessedFormatted());
        model.addAttribute("nextDelivery", statusService.getNextExpectedDelivery());
        model.addAttribute("lastOrder", statusService.getLastOrderDescription());

        String dbSummary = statusService.getLowStockCount() + " varer med lav beholdning, " +
                statusService.getMissingDataCount() + " mangler bilde";
        model.addAttribute("databaseSummary", dbSummary);

        // Henter logger fra LoggerService
        model.addAttribute("apiLogs", loggerService.getApiLogs());
        model.addAttribute("emailLogs", loggerService.getEmailLogs());
        model.addAttribute("orderLogs", loggerService.getOrderLogs());
        model.addAttribute("userLogs", loggerService.getUserLogs());
        model.addAttribute("orderPlacementLogs", loggerService.getOrderPlacementLogs());

        // Manglende produkter i ordrer
        List<ReplacedProductLog> logs = replacedProductLogRepository.findAll();
        model.addAttribute("replacedLogs", logs);

        return "admin-status";
    }
}
