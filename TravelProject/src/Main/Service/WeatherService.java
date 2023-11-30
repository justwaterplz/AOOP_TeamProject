package Main.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * https://openweathermap.org/로부터 위도와 경도를 통해 날씨데이터 json받아오는 Service
 */
public class WeatherService {

    private final String secretKey = "8b8c852ce0cebeec369bf7f91333a28e";
    public JsonArray getWeatherJsonArray(double longitude, double latitude, int day){
        String url = "http://api.openweathermap.org/data/2.5/forecast"
                + "?lat="   + latitude
                + "&lon="   + longitude
                + "&appid=" + secretKey
                + "&units=" + "metric"
                + "&cnt="   + (5 + (day-1)*8)
                + "&lang="  + "kr";
        try {
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);
                JsonArray listArray = jsonObject.getAsJsonArray("list");
                return listArray;
            } else {
                System.out.println("연결 오류: " + responseCode);
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
