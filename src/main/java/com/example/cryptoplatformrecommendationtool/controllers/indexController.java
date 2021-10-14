package com.example.cryptoplatformrecommendationtool.controllers;

import com.example.cryptoplatformrecommendationtool.domain.Rate;
import com.example.cryptoplatformrecommendationtool.services.RatesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController {

    private final RatesService ratesService;

    public indexController(RatesService ratesService) {
        this.ratesService = ratesService;
    }

    @RequestMapping({""})
    public String getIndexPage(Model model){
        Rate rate_Kraken_BTC_USD = ratesService.getRate("Kraken_BTC_USD");
        Rate rate_Kraken_ETH_USD = ratesService.getRate("Kraken_ETH_USD");
        Rate rate_Coinbase_BTC_USD =ratesService.getRate("Coinbase_BTC_USD");
        Rate rate_Coinbase_ETH_USD=ratesService.getRate("Coinbase_ETH_USD");
        model.addAttribute("Kraken_BTC_USD", rate_Kraken_BTC_USD);
        model.addAttribute("Kraken_ETH_USD", rate_Kraken_ETH_USD);
        model.addAttribute("Coinbase_BTC_USD", rate_Coinbase_BTC_USD);
        model.addAttribute("Coinbase_ETH_USD", rate_Coinbase_ETH_USD);

        String recommendation = ratesService.getRecommendation();
        model.addAttribute("recommendation", recommendation);
        return "index";
    }
}
