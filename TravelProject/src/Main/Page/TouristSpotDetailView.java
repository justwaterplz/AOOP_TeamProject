package Main.Page;

import Main.Model.Model관광지;
import Main.Module.ImageFinder;
import Main.Service.LocateService;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class TouristSpotDetailView extends PageBase {

    BufferedReader io = new BufferedReader(new InputStreamReader(System.in));
    private final LocateService locateService = new LocateService();
    private String clientId = "62xhbr8jjh";
    private String clientSecret = "mYxnTmAJz1psgRwuC3lqiO8C3v7QkH3MSItXtGRp";
    private double longitude;
    private double latitude;
    private JPanel mainPanel;
    private JLabel mapImageLabel;
    private JLabel locationLabel;
    private JButton backButton;
    private JLabel touristSpotNameLabel;
    private JLabel indoorOutdoorLabel;
    private JLabel locationImageLabel;
    private JPanel topBarPanel;


    public TouristSpotDetailView(PageChangeListener _listener, Model관광지 touristSpot) {
        super(_listener);

        renderTouristSpotDetails(touristSpot);

        try{

            // 위치 사진 이미지 불러오기
            ImageFinder.searchAndDisplayImage(removeParentheses(touristSpot.get관광지명()),locationImageLabel);

            // 위치 네이버 맵 이미지 불러오기
            String imageApiURL = locateService.getImageApiURL(longitude,latitude);
            ImageIcon imageIcon = locateService.getImageIcon(imageApiURL);
            mapImageLabel.setIcon(imageIcon);

            // 좌표로 위치 이름 불러오기
            String locationApiURL = locateService.getLocationApiURL(longitude,latitude);
            Document doc = locateService.getLocationDocument(locationApiURL);

            // doc로부터 XPath로 값을 추출하여 위치 설정
            String locationName = locateService.getLocationName(doc);
            locationLabel.setText(locationName.isEmpty() ? removeParentheses(touristSpot.get관광지명()) : locationName);

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
        longitude = touristSpot.get경도().doubleValue();
        latitude = touristSpot.get위도().doubleValue();
        touristSpotNameLabel.setText(touristSpot.get관광지명().toString());
        indoorOutdoorLabel.setText(touristSpot.get실내구분());
    }

    // 정규 표현식을 사용하여 괄호와 괄호 안의 내용을 제거
    public static String removeParentheses(String input) {
        return input.replaceAll("\\([^\\)]*\\)", "").trim();
    }

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

