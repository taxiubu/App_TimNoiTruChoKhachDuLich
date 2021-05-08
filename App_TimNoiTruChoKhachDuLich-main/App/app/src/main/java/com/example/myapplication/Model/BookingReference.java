package com.example.myapplication.Model;

public class BookingReference {
    private String dateIn, dateOut, email, nameResidence, countRoom,countPeople, status,phone;

    public BookingReference(String dateIn, String dateOut, String email,String phone, String nameResidence, String countRoom,String countPeople,String status) {
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.email = email;
        this.nameResidence = nameResidence;
        this.countRoom = countRoom;
        this.phone=phone;
        this.status=status;
        this.countPeople=countPeople;
    }

    public String getCountPeople() {
        return countPeople;
    }

    public void setCountPeople(String countPeople) {
        this.countPeople = countPeople;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDateIn() {
        return dateIn;
    }

    public void setDateIn(String dateIn) {
        this.dateIn = dateIn;
    }

    public String getDateOut() {
        return dateOut;
    }

    public void setDateOut(String dateOut) {
        this.dateOut = dateOut;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNameResidence() {
        return nameResidence;
    }

    public void setNameResidence(String nameResidence) {
        this.nameResidence = nameResidence;
    }

    public String getCountRoom() {
        return countRoom;
    }

    public void setCountRoom(String countRoom) {
        this.countRoom = countRoom;
    }
}
