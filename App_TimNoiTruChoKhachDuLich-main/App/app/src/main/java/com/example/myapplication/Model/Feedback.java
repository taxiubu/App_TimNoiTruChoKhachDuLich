package com.example.myapplication.Model;

public class Feedback {
    private String idUser,name, nameResidence, rateStar, content;
    private String day;

    public Feedback(String idUser,String name, String nameResidence, String rateStar, String content, String day) {
        this.idUser = idUser;
        this.nameResidence = nameResidence;
        this.rateStar = rateStar;
        this.content = content;
        this.day = day;
        this.name = name;
    }

    public Feedback() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNameResidence() {
        return nameResidence;
    }

    public void setNameResidence(String nameResidence) {
        this.nameResidence = nameResidence;
    }

    public String getRateStar() {
        return rateStar;
    }

    public void setRateStar(String rateStar) {
        this.rateStar = rateStar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
