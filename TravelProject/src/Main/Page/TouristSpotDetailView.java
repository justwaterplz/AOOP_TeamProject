package Main.Page;

import Main.Model.Model관광지;
import Main.Module.ImageFinder;
import org.w3c.dom.Document;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class TouristSpotDetailView extends PageBase {

    BufferedReader io = new BufferedReader(new InputStreamReader(System.in));
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
            ImageFinder.searchAndDisplayImage(removeParentheses(touristSpot.관광지명()),locationImageLabel);

            // 위치 네이버 맵 이미지 불러오기
            String imageApiURL = mainService.getImageApiURL(longitude,latitude);
            ImageIcon imageIcon = mainService.getImageIcon(imageApiURL);
            mapImageLabel.setIcon(imageIcon);

            // 좌표로 위치 이름 불러오기
            String locationApiURL = mainService.getLocationApiURL(longitude,latitude);
            Document doc = mainService.getLocationDocument(locationApiURL);

            // doc로부터 XPath로 값을 추출하여 위치 설정
            String locationName = mainService.getLocationName(doc);
            locationLabel.setText(locationName.isEmpty() ? removeParentheses(touristSpot.관광지명()) : locationName);

        } catch (Exception e){
            System.out.println("TouristSpotDetailView.TouristSpotDetailView:  " + e);
        }

        // 액션 리스너 등록
        backButton.addActionListener(e -> listener.returnToPrevPage());
        backButton.addMouseListener(getButtonMouseListener(backButton, backButtonPressedColor, backButtonRolloverColor));
        // image button 초기화
        initializeImageButton(backButton);
    }

    // 모델로부터 이름 경도 위도 값 등을 받고 View에 적용한다.
    private void renderTouristSpotDetails(Model관광지 touristSpot) {
        longitude = touristSpot.경도().doubleValue();
        latitude = touristSpot.위도().doubleValue();
        touristSpotNameLabel.setText(touristSpot.관광지명().toString());
        indoorOutdoorLabel.setText(touristSpot.실내구분());
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

