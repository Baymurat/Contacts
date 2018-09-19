package com.itechart.contacts.core.entities;

/**
 * Created by Admin on 12.09.2018.
 */
public class Address {
    private String country;
    private String city;
    private String streetHouseApart;
    private int index;

    public Address() {

    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetHouseApart() {
        return streetHouseApart;
    }

    public void setStreetHouseApart(String streetHouseApart) {
        this.streetHouseApart = streetHouseApart;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
