package com.example.cryptoplatformrecommendationtool.services;

import com.example.cryptoplatformrecommendationtool.domain.Rate;
import com.example.cryptoplatformrecommendationtool.repositories.RateRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class RatesService {
    private final RateRepository rateRepository;

    public RatesService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    public HashSet<Rate> getRates() {
        HashSet<Rate> set = new HashSet<>();
        rateRepository.findAll().iterator().forEachRemaining(set::add);
        return set;
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
