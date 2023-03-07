package application;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    private static EmailSender emailSender = null;
    private static Set<String> notifiedApartments = new HashSet<>();

    private EmailSender() {

    }

    public static EmailSender newInstance() {
        if (emailSender == null)
            emailSender = new EmailSender();
        return emailSender;
    }

    public void sendEmail(List<String> apartmentsUrl)  {


        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        apartmentsUrl.forEach(apartmentUrl -> {
            if (!notifiedApartments.contains(apartmentUrl)) {
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, "{\"project\":\"notifications\",\"channel\":\"notifications\",\"event\":\"" + apartmentUrl + "\",\"notify\":true}");
                Request request = new Request.Builder()
                        .url("https://api.logsnag.com/v1/log")
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer c7ad0ff2d353d1c482dd1e22ff1e4efe")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    notifiedApartments.add(apartmentUrl);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }


}
