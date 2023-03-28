package application;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

//import static application.SimilarNames.getSimilarNames;

public class Main {

    public static void main(String[] args) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String startDate = dtf.format(now);

        Timer timer = new Timer();
        TimerTask hourlyTask = new TimerTask() {
            @Override
            public void run () {
                System.out.println("Start a run");

                Constants.areas.forEach(area -> {
                    String areaJsonPath = "/Users/asafbenavraham/Downloads/" + area + ".json";
                    File areaJson = new File(areaJsonPath);
                    areaJson.delete();
                });


                UrlDataFetcher urlDataFetcher = UrlDataFetcher.newInstance();
                ApartmentParser apartmentParser = ApartmentParser.newInstance();
                ApartmentUrlBuilder apartmentUrlBuilder = ApartmentUrlBuilder.newInstance();
                EmailSender emailSender = EmailSender.newInstance();

                urlDataFetcher.downloadApartments();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                List<Apartment> apartments = apartmentParser.parseApartments(startDate);
                List<String> apartmentsUrl = apartmentUrlBuilder.buildApartmentsUrl(apartments);
                emailSender.sendEmail(apartmentsUrl);
                System.out.println("Finished a run");
            }
        };

        timer.schedule (hourlyTask, 0l, 1000*60*60);
//        timer.schedule (hourlyTask, 0l, 5000);


//        System.out.println(apartmentsUrl);
//        areasFolder.delete();


        //        List<SimilarNames.NameSimilarity> similarNames = getSimilarNames(input, randomNames);
//        System.out.println("input_str:" + input +  "->" + similarNames);

    }


}
