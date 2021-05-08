package com.example.myapplication.Model;

import java.io.Serializable;

public class Residence implements Serializable {
    private String name, type, address, price,rate, rateStar, imageLink, webLink;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRateStar() {
        return rateStar;
    }

    public void setRateStar(String rateStar) {
        this.rateStar = rateStar;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public Residence(String name, String type, String address, String price, String rate, String rateStar, String linkanh, String linkweb) {
        this.name = name;
        this.type = type;
        this.address = address;
        this.price = price;
        this.rate = rate;
        this.rateStar = rateStar;
        this.imageLink = linkanh;
        this.webLink = linkweb;
    }

    public Residence() {
    }
}
