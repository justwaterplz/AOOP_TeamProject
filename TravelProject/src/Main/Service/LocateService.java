package Main.Service;

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
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LocateService {
    private String clientId = "62xhbr8jjh";
    private String clientSecret = "mYxnTmAJz1psgRwuC3lqiO8C3v7QkH3MSItXtGRp";

    // Doc을 xpath를 이용해서 지역 위치를 추출한다.
    public String getLocationName(Document doc) throws XPathExpressionException {
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

    // Naver의 ReverseGeocoding으로 xml로부터 Doc을 뽑아낸다.
    public Document getLocationDocument(String locationApiURL) throws IOException, ParserConfigurationException, SAXException {
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

    // Naver의 StaticMap로부터 이미지을 뽑아낸다.
    public ImageIcon getImageIcon(String imageApiURL) throws IOException {
        URL imageURL = new URL(imageApiURL);
        HttpURLConnection imageCon = (HttpURLConnection) imageURL.openConnection();

        imageCon.setRequestMethod("GET");
        imageCon.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
        imageCon.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
        InputStream imageInputStream = imageCon.getInputStream();

        BufferedImage image = ImageIO.read(imageInputStream);
        ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(300, 400, Image.SCALE_SMOOTH));

        return imageIcon;
    }

    // 경도와 위도를 참고해서 관광지 위치를 얻 ApiURL를 뽑아낸다.
    public String getLocationApiURL(double longitude, double latitude) {
        String locationApiURL = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?request=coordsToaddr&coords="
                + longitude + "," + latitude + "&sourcecrs=epsg:4326&orders=roadaddr&output=xml";
        return locationApiURL;
    }

    // 경도와 위도를 참고해서 지도 사진을 얻는 ApiURL를 생성한다.
    public String getImageApiURL(double longitude, double latitude) {
        String imageApiURL = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?w=400&h=400&level=15&" +
                "center=" + longitude + "," + latitude +
                "&markers=type:d|size:mid|pos:" + longitude + "%20" + latitude;
        return imageApiURL;
    }

}