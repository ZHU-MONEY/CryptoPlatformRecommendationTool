package com.example.cryptoplatformrecommendationtool.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Rate {
    double buyPrice;
    double sellPrice;
    String platform;
    String base;
    String currency;
    String id;

    public Rate() {

    }

    public Rate(String id, String platform, String base, String currency, double buyPrice, double sellPrice) {
        this.id = id;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.platform = platform;
        this.base = base;
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "buyPrice=" + buyPrice +
                ", sellPrice=" + sellPrice +
                ", platform='" + platform + '\'' +
                ", base='" + base + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }



    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id
    public String getId() {
        return id;
    }
}
