package com.example.beerfinder;

public class Beer {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBeerUrl() {
        return beerUrl;
    }

    public void setBeerUrl(String beerUrl) {
        this.beerUrl = beerUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFoodPairing() {
        return foodPairing;
    }

    public void setFoodPairing(String foodPairing) {
        this.foodPairing = foodPairing;
    }

    public String getBrewerTips() {
        return brewerTips;
    }

    public void setBrewerTips(String brewerTips) {
        this.brewerTips = brewerTips;
    }

    private String name;
    private String abv;
    private String date;
    private String beerUrl;
    private String description;
    private String foodPairing;
    private String brewerTips;

    public Beer(String name, String abv, String date, String beerUrl, String description,
                String foodPairing, String brewerTips) {
        this.name = name;
        this.abv = abv;
        this.date = date;
        this.beerUrl = beerUrl;
        this.description = description;
        this.foodPairing = foodPairing;
        this.brewerTips = brewerTips;
    }

}
