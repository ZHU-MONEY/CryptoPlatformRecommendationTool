package com.example.cryptoplatformrecommendationtool.services;

import com.example.cryptoplatformrecommendationtool.domain.Rate;
import com.example.cryptoplatformrecommendationtool.repositories.RateRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Optional;

@Service
public class RatesService {

    //----------Kraken------------
    final String url_Kraken_BTC_USD = "https://api.kraken.com/0/public/Ticker?pair=XBTUSD";
    final String url_Kraken_ETH_USD = "https://api.kraken.com/0/public/Ticker?pair=ETHUSD";

    //----------Coinbase------------
    final String url_Coinbase_Buy_BTC_USD = "https://api.coinbase.com/v2/prices/BTC-USD/buy";
    final String url_Coinbase_Sell_BTC_USD = "https://api.coinbase.com/v2/prices/BTC-USD/sell";
    final String url_Coinbase_Buy_ETH_USD = "https://api.coinbase.com/v2/prices/ETH-USD/buy";
    final String url_Coinbase_Sell_ETH_USD = "https://api.coinbase.com/v2/prices/ETH-USD/sell";
    private final RateRepository rateRepository;

    public RatesService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    public HashSet<Rate> getRates() {
        HashSet<Rate> set = new HashSet<>();
        rateRepository.findAll().iterator().forEachRemaining(set::add);
        return set;
    }

    public void PullRatesFromSource() throws JsonProcessingException {
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

    public Rate getRate(String id){

        Optional<Rate> rateOptional = rateRepository.findById(id);
        if(rateOptional.isEmpty()){
            throw new RuntimeException("Expected Rate "+ id +" Not Found");
        }

        return rateOptional.get();
    }

    public String getRecommendation(){
        Rate rate_Kraken_BTC_USD = rateRepository.findById("Kraken_BTC_USD").get();
        Rate rate_Kraken_ETH_USD = rateRepository.findById("Kraken_ETH_USD").get();
        Rate rate_Coinbase_BTC_USD =rateRepository.findById("Coinbase_BTC_USD").get();
        Rate rate_Coinbase_ETH_USD=rateRepository.findById("Coinbase_ETH_USD").get();

        StringBuilder sb = new StringBuilder("Recommend to buy BTC at: ");

        if(rate_Kraken_BTC_USD.getBuyPrice()<rate_Coinbase_BTC_USD.getBuyPrice())
            sb.append("Kraken");
        else
            sb.append("Coinbase");


        sb.append("\n");
        sb.append("Recommend to sell BTC at: ");
        if(rate_Kraken_BTC_USD.getSellPrice()>rate_Coinbase_BTC_USD.getSellPrice())
            sb.append("Kraken");
        else
            sb.append("Coinbase");

        sb.append("\n");
        sb.append("Recommend to buy ETH at: ");
        if(rate_Kraken_ETH_USD.getBuyPrice()<rate_Coinbase_ETH_USD.getBuyPrice())
            sb.append("Kraken");
        else
            sb.append("Coinbase");

        sb.append("\n");
        sb.append("Recommend to sell ETH at: ");
        if(rate_Kraken_ETH_USD.getSellPrice()>rate_Coinbase_ETH_USD.getSellPrice())
            sb.append("Kraken");
        else
            sb.append("Coinbase");


        return sb.toString();
    }
}
