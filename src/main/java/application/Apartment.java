package application;

import lombok.Getter;

public class Apartment {
    @Getter String neighborhood;
    @Getter String title;
    @Getter String square_meters;
    @Getter String price;
    @Getter String rooms;

    @Getter String date;
    @Getter String date_added;


    public Apartment(String neighborhood, String title, String square_meters, String price, String date, String date_added, String rooms) {
        this.neighborhood = neighborhood;
        this.title = title;
        this.square_meters = square_meters;
        this.price = price;
        this.date = date;
        this.date_added = date_added;
        this.rooms = rooms;
    }


    @Override
    public String toString() {
        return "Apartment{" +
                "neighborhood='" + neighborhood + '\'' +
                ", title=" + title +
                ", square_meters=" + square_meters +
                ", price=" + price +
                ", date=" + date +
                ", date_added=" + date_added +
                ", rooms=" + rooms +
                '}';
    }
}
