    package application;

    import com.google.gson.Gson;
    import com.google.gson.JsonArray;
    import com.google.gson.JsonElement;
    import com.google.gson.JsonObject;
    import com.google.gson.stream.JsonReader;
    import lombok.Getter;

    import java.io.File;
    import java.io.FileNotFoundException;
    import java.io.FileReader;
    import java.io.Reader;
    import java.net.URISyntaxException;
    import java.net.URL;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.stream.Collectors;

    public class ApartmentParser {

        private static ApartmentParser apartmentParser = null;
        private final Gson gson = new Gson();


        private ApartmentParser() {

        }

        public static ApartmentParser newInstance() {
            if (apartmentParser == null)
                apartmentParser = new ApartmentParser();
            return apartmentParser;
        }

        public List<Apartment> parseApartments(String startDate){
            List<Apartment> apartments;
            apartments = Constants.areas.stream().map(area -> {
                URL resource = getClass().getClassLoader().getResource("areas/" + area + ".json");
                if (resource == null) {
                    throw new IllegalArgumentException("file not found!");
                } else {
                    try {
                        File areaJson = new File(resource.toURI());
                        Reader areaJsonReader = new FileReader(areaJson);
                        JsonElement json = gson.fromJson(areaJsonReader, JsonElement.class);
                        JsonObject jsonObject = json.getAsJsonObject();
                        JsonArray jsonArray = jsonObject.get("feed")
                                .getAsJsonObject().get("feed_items").getAsJsonArray();
                        List<Apartment> parsedApartments = parseFromJson(jsonArray, startDate);
                        return parsedApartments;
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }

            }).flatMap(List::stream).collect(Collectors.toList());

            return apartments;
        }

        private List<Apartment> parseFromJson(JsonArray apartmentsJsonArray, String startDate){
            List<Apartment> apartments = new ArrayList<>();
            apartmentsJsonArray.forEach(apartment -> {
                Apartment parsedApartment = parseApartment(apartment);
                if (parsedApartment != null && isNewApartment(parsedApartment.date_added, startDate))
                    apartments.add(parsedApartment);
            });
            return apartments;
        }

        private boolean isNewApartment(String apartmentDate, String startDate) {
            String[] apartmentSplit = apartmentDate.split("-");
            String[] startDateSplit = startDate.split("-");
            String apartmentYear = apartmentSplit[0];
            String apartmentMonth = apartmentSplit[1];
            String apartmentDay = apartmentSplit[2];
            String startDateYear = startDateSplit[0];
            String startDateMonth = startDateSplit[1];
            String startDateDay = startDateSplit[2];

            if(apartmentYear.compareTo(startDateYear) > 0)
                return true;
            else if (apartmentYear.equals(startDateYear) && apartmentMonth.compareTo(startDateMonth) > 0) {
                return true;
            }
            else if (apartmentYear.equals(startDateYear)
                    && apartmentMonth.equals(startDateMonth)
                    && apartmentDay.compareTo(startDateDay) >= 0) {
                return true;
            }
            else{
                return false;
            }
        }

        private Apartment parseApartment(JsonElement apartmentJsonElement){
            if(apartmentJsonElement.getAsJsonObject().size() <= 2){
                return null;
            }

            String neighborhood = apartmentJsonElement.getAsJsonObject().get("neighborhood").toString();
            neighborhood = neighborhood.substring(1, neighborhood.length() - 1);
            String title = apartmentJsonElement.getAsJsonObject().get("title_1").toString();
            title = title.substring(1, title.length() - 1);
            String square_meters = apartmentJsonElement.getAsJsonObject().get("square_meters").toString();
            String price = apartmentJsonElement.getAsJsonObject().get("price").toString().split(" ")[0];
            price = price.substring(1);
            price = price.replace(",", "");
            String rooms = apartmentJsonElement.getAsJsonObject().get("Rooms").toString();
            String date = apartmentJsonElement.getAsJsonObject().get("date").toString().split(" ")[0];
            date = date.substring(1);
            String date_added = apartmentJsonElement.getAsJsonObject().get("date_added").toString().split(" ")[0];
            date_added = date_added.substring(1);
            Apartment apartment = new Apartment(neighborhood, title, square_meters, price, date, date_added, rooms);
            return apartment;
        }

    }
