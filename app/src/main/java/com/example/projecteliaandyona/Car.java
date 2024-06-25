package com.example.projecteliaandyona;

public class Car {
    String owner;
    String carName;
    String year;
    String price;
    String phoneNumber;


    public Car(String owner, String carName, String year, String price, String phoneNumber) {
        this.owner = owner;
        this.carName = carName;
        this.year = year;
        this.price = price;
        this.phoneNumber = phoneNumber;
    }

    public Car(){

    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String img) {
        this.owner = img;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
