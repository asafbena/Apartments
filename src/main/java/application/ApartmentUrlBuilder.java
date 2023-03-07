package application;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ApartmentUrlBuilder {

    private static ApartmentUrlBuilder apartmentUrlBuilder = null;

    private ApartmentUrlBuilder() {

    }

    public static ApartmentUrlBuilder newInstance() {
        if (apartmentUrlBuilder == null)
            apartmentUrlBuilder = new ApartmentUrlBuilder();
        return apartmentUrlBuilder;
    }

    public List<String> buildApartmentsUrl(List<Apartment> apartments){
        List<String> apartmentsUrl;
        apartmentsUrl = apartments.stream().map(apartment -> {
            String apartmentUrl = buildApartmentUrl(apartment);
            return apartmentUrl;
        }).collect(Collectors.toList());

        return apartmentsUrl;
    }

    public String buildApartmentUrl(Apartment apartment){
        String apartmentUrl = String.format("https://www.yad2.co.il/realestate/rent/map" +
                "?rooms=2-3" +
                "&price=%s-%s" +
                "&squaremeter=%s--1" +
                "&comment=%s&z=12"
                , apartment.price
                , apartment.price
                , apartment.square_meters
                , apartment.title);
        return apartmentUrl;
    }




    }
