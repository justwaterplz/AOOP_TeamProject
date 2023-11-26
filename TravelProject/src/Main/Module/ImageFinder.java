package Main.Module;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageFinder {
    private static final String API_KEY = "AIzaSyDqL27KalItTnbwcjl7PiM24L_S0rnnAV4";
    private static final String CX = "842310f819f41461f";

    public static void searchAndDisplayImage(String query, JLabel imageLabel) {
        System.out.println("query : " + query);
        try {
            // API 일일 제한량 100 이니까 적당히
            String url = "https://www.googleapis.com/customsearch/v1?q=" + query.replace(" ", "%20") + "&searchType=image&key=" + API_KEY + "&cx=" + CX;
            System.out.println("url : " + url);
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                JsonElement jsonElement = JsonParser.parseString(content.toString());
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    if (jsonObject.has("items")) {
                        String imageUrl = jsonObject.getAsJsonArray("items").get(0).getAsJsonObject().get("link").getAsString();
                        displayImageFromUrl(imageUrl, imageLabel);
                    }
                }
            } else {
                System.out.println("GET request not worked");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void displayImageFromUrl(String imageUrl, JLabel imageLabel) {
        SwingUtilities.invokeLater(() -> {
            try {
                BufferedImage image = loadImage(imageUrl);
                ImageIcon icon = new ImageIcon(image.getScaledInstance(750, 600, Image.SCALE_SMOOTH));
                imageLabel.setIcon(icon);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to load image", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private static BufferedImage loadImage(String imageUrl) throws IOException {
        try (java.io.InputStream in = new URL(imageUrl).openStream()) {
            return ImageIO.read(in);
        }
    }
}
