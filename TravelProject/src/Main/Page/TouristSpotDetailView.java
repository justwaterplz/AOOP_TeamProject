package Main.Page;

import Main.Model.Model관광지;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class TouristSpotDetailView extends PageBase {

    BufferedReader io = new BufferedReader(new InputStreamReader(System.in));
    private String clientId = "62xhbr8jjh";
    private String clientSecret = "mYxnTmAJz1psgRwuC3lqiO8C3v7QkH3MSItXtGRp";
    private double longitude;
    private double latitude;
    private JPanel mainPanel;
    private JLabel imageLabel;
    private JLabel locationLabel;
    private JButton backButton;
    private JLabel touristSpotNameLabel;
    private JLabel indoorOutdoorLabel;
    private JPanel topBarPanel;

    public TouristSpotDetailView(PageChangeListener _listener, Model관광지 touristSpot) {
        super(_listener);

        renderTouristSpotDetails(touristSpot);

        try{
            // 위치 이미지 불러오기
            String imageApiURL = getImageApiURL();
            ImageIcon imageIcon = getImageIcon(imageApiURL);
            imageLabel.setIcon(imageIcon);

            // 좌표로 위치 이름 불러오기
            String locationApiURL = getLocationApiURL();
            Document doc = getLocationDocument(locationApiURL);

            // doc로부터 XPath로 값을 추출하여 위치 설정
            String locationName = getLocationName(doc);
            locationLabel.setText(locationName);

        } catch (Exception e){
            System.out.println("TouristSpotDetailView.TouristSpotDetailView:  " + e);
        }

        // 액션 리스너 등록
        backButton.addActionListener(e -> listener.returnToPrevPage());
        backButton.addMouseListener(getButtonMouseListener(backButton, backButtonPressedColor, backButtonRolloverColor));
        // image button 초기화
        initializeImageButton(backButton);
    }

    private void renderTouristSpotDetails(Model관광지 touristSpot) {
        // 위치의 이름 경도 위도 등을 받아온다.
        longitude = touristSpot.경도().doubleValue();
        latitude = touristSpot.위도().doubleValue();
        touristSpotNameLabel.setText(touristSpot.관광지명().toString());
        indoorOutdoorLabel.setText(touristSpot.실내구분());
    }


    private String getLocationName(Document doc) throws XPathExpressionException {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();

        String[] areaNames = {
                "/geocoding/results/order/region/area1/name",
                "/geocoding/results/order/region/area2/name",
                "/geocoding/results/order/region/area3/name",
                "/geocoding/results/order/land/name",
                "/geocoding/results/order/land/number1"
        };

        StringBuilder result = new StringBuilder();

        for (String xpathExpression : areaNames) {
            String name = xpath.evaluate(xpathExpression, doc);
            result.append(name).append("  ");
        }

        return result.toString().trim();
    }

    private Document getLocationDocument(String locationApiURL) throws IOException, ParserConfigurationException, SAXException {
        URL locationURL = new URL(locationApiURL);
        HttpURLConnection locationCon = (HttpURLConnection) locationURL.openConnection();
        locationCon.setRequestMethod("GET");
        locationCon.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
        locationCon.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

        InputStream locationInputStream = locationCon.getInputStream();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(locationInputStream);
        return doc;
    }

    private ImageIcon getImageIcon(String imageApiURL) throws IOException {
        URL imageURL = new URL(imageApiURL);
        HttpURLConnection imageCon = (HttpURLConnection) imageURL.openConnection();

        imageCon.setRequestMethod("GET");
        imageCon.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
        imageCon.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
        InputStream imageInputStream = imageCon.getInputStream();
        ImageIcon imageIcon = new ImageIcon(ImageIO.read(imageInputStream));
        return imageIcon;
    }

    private String getLocationApiURL() {
        String locationApiURL = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?request=coordsToaddr&coords="
                + longitude + "," + latitude + "&sourcecrs=epsg:4326&orders=roadaddr&output=xml";
        return locationApiURL;
    }

    private String getImageApiURL() {
        String imageApiURL = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?w=400&h=400&level=15&" +
                "center=" + longitude + "," + latitude +
                "&markers=type:d|size:mid|pos:" + longitude + "%20" + latitude;
        return imageApiURL;
    }

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }
}

