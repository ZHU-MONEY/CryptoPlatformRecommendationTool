package com.example.cryptoplatformrecommendationtool.bootstrap;

import com.example.cryptoplatformrecommendationtool.domain.Rate;
import com.example.cryptoplatformrecommendationtool.repositories.RateRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RateBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final RateRepository rateRepository;

    //----------Kraken------------
    final String url_Kraken_BTC_USD = "https://api.kraken.com/0/public/Ticker?pair=XBTUSD";
    final String url_Kraken_ETH_USD = "https://api.kraken.com/0/public/Ticker?pair=ETHUSD";

    //----------Coinbase------------
    final String url_Coinbase_Buy_BTC_USD = "https://api.coinbase.com/v2/prices/BTC-USD/buy";
    final String url_Coinbase_Sell_BTC_USD = "https://api.coinbase.com/v2/prices/BTC-USD/sell";
    final String url_Coinbase_Buy_ETH_USD = "https://api.coinbase.com/v2/prices/ETH-USD/buy";
    final String url_Coinbase_Sell_ETH_USD = "https://api.coinbase.com/v2/prices/ETH-USD/sell";

    public RateBootstrap(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            getRates();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void getRates() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        //----------Kraken------------
        ResponseEntity<String> response = restTemplate.getForEntity(url_Kraken_BTC_USD, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode sellP = root.findPath("a").get(0);
        JsonNode buyP = root.findPath("b").get(0);
        Rate rate_Kraken_BTC_USD = new Rate("Kraken_BTC_USD","Kraken","BTC","USD", buyP.asDouble(), sellP.asDouble());
        rateRepository.save(rate_Kraken_BTC_USD);

        response = restTemplate.getForEntity(url_Kraken_ETH_USD, String.class);
        root = mapper.readTree(response.getBody());
        sellP = root.findPath("a").get(0);
        buyP = root.findPath("b").get(0);
        Rate rate_Kraken_ETH_USD = new Rate("Kraken_ETH_USD","Kraken","ETH","USD", buyP.asDouble(), sellP.asDouble());
        rateRepository.save(rate_Kraken_ETH_USD);

        //----------Coinbase------------
        response = restTemplate.getForEntity(url_Coinbase_Buy_BTC_USD, String.class);
        root = mapper.readTree(response.getBody());
        buyP = root.findPath("amount");
        response = restTemplate.getForEntity(url_Coinbase_Sell_BTC_USD, String.class);
        root = mapper.readTree(response.getBody());
        sellP = root.findPath("amount");
        Rate rate_Coinbase_BTC_USD = new Rate("Coinbase_BTC_USD","Coinbase","BTC","USD", buyP.asDouble(), sellP.asDouble());
        rateRepository.save(rate_Coinbase_BTC_USD);

        response = restTemplate.getForEntity(url_Coinbase_Buy_ETH_USD, String.class);
        root = mapper.readTree(response.getBody());
        buyP = root.findPath("amount");
        response = restTemplate.getForEntity(url_Coinbase_Sell_ETH_USD, String.class);
        root = mapper.readTree(response.getBody());
        sellP = root.findPath("amount");
        Rate rate_Coinbase_ETH_USD = new Rate("Coinbase_ETH_USD","Coinbase","ETH","USD", buyP.asDouble(), sellP.asDouble());
        rateRepository.save(rate_Coinbase_ETH_USD);
    }


}
