package Main.Page;

import Main.Model.Model관광지;
import Main.Module.ImageFinder;
import Main.Module.SpotNameManager;
import Main.Service.MainService;
import Main.Service.NaverApiService;
import Main.Service.WeatherService;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class SpotDetailView extends JFrame {

    private JLabel nameLabel;
    private JLabel locationLabel;
    private JLabel themeLabel;
    private JLabel indoorOutdoorLabel;
    private JTabbedPane tabbedPane;
    private JButton favoriteButton;
    private JButton closeButton;

    private JFrame parentFrame;
    private final MainService mainService;
    private final NaverApiService naverApiService;
    private final WeatherService weatherService;
    private double longitude;
    private double latitude;
    private Model관광지 spot;
    private boolean isFavorited;

    public SpotDetailView(JFrame _parentFrame, Model관광지 spot) throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {
        this.naverApiService = new NaverApiService();
        this.weatherService = new WeatherService();
        this.mainService = new MainService();
        this.spot = spot;
        this.parentFrame = _parentFrame;

        initializeUI(spot);
        checkFavoritedStatus();

        // 프레임 표시
        setVisible(true);
    }

    private void initializeUI(Model관광지 spot) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {

        longitude = spot.get경도().doubleValue();
        latitude = spot.get위도().doubleValue();
        nameLabel = new JLabel(SpotNameManager.getSpotName(spot.get관광지명()));
        themeLabel = new JLabel("테마 : " + SpotNameManager.getThemeName(spot.get테마분류()));
        indoorOutdoorLabel = new JLabel("실내구분 : " + spot.get실내구분());

        // 탭 패널 생성 및 추가
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("관광지 사진", createGoogleImagePanel(spot)); // 병대 돈을 위해 잠시 주석
        tabbedPane.addTab("네이버 지도", createNaverImagePanel(spot));
        tabbedPane.addTab("날씨", createWeatherListPanel(spot));


        // 즐겨찾기 버튼
        favoriteButton = new JButton("");
        favoriteButton.addActionListener(new SpotDetailView.ButtonActionListener());

        // 닫기 버튼
        closeButton = new JButton("닫기");
        closeButton.addActionListener(new SpotDetailView.ButtonActionListener());

        // 레이아웃 설정
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(2, 2,0,0));
        infoPanel.add(nameLabel);
        infoPanel.add(themeLabel);
        infoPanel.add(indoorOutdoorLabel);
        infoPanel.add(locationLabel);
        infoPanel.setBackground(new Color(240, 240, 240)); // 연한 회색 배경
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        favoriteButton.setPreferredSize(new Dimension(150, 30)); // 예시 크기
        closeButton.setPreferredSize(new Dimension(150, 30)); // 예시 크기
        buttonPanel.add(favoriteButton);
        buttonPanel.add(closeButton);

        // 각 Label에 대해 폰트 크기 및 간격 조절
        nameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14)); // 예시 폰트 크기
        locationLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12)); // 예시 폰트 크기
        themeLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12)); // 예시 폰트 크기
        indoorOutdoorLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12)); // 예시 폰트 크기

        // 전체 레이아웃 설정
        setLayout(new BorderLayout());
        add(infoPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);


        // 프레임 설정
        setTitle("여행지 상세 정보");
        setSize(800, 800);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    // 구글 위치 사진 이미지 불러오기
    private JPanel createGoogleImagePanel(Model관광지 spot) throws IOException, ParserConfigurationException, SAXException {

        JPanel panel = new JPanel(new BorderLayout());

        JLabel imageLabel = new JLabel();
        ImageFinder.searchAndDisplayImage(SpotNameManager.getSpotName(nameLabel.getText()),imageLabel);
        panel.add(imageLabel, BorderLayout.CENTER);

        return panel;
    }

    // 위치 네이버 맵 이미지 및 좌표기준으로 지역위치 불러오기
    private JPanel createNaverImagePanel(Model관광지 spot) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {


        JPanel panel = new JPanel(new BorderLayout());

        JLabel imageLabel  = new JLabel();

        String imageApiURL = naverApiService.getImageApiURL(longitude,latitude);
        ImageIcon imageIcon = naverApiService.getImageIcon(imageApiURL);
        imageLabel.setIcon(imageIcon);

        panel.add(imageLabel, BorderLayout.CENTER);

        // 좌표로 위치 이름 불러오기
        String locationApiURL = naverApiService.getLocationApiURL(longitude,latitude);
        Document doc = naverApiService.getLocationDocument(locationApiURL);

        String locationName = naverApiService.getLocationName(doc);
        locationLabel = new JLabel(locationName.isEmpty() ? "위치 : " + SpotNameManager.getSpotLocate(spot.get관광지명()) : "위치 : " + locationName);

        return panel;
    }

    // 날씨 정보를 보여주는 Panel 생성기
    private JPanel createWeatherListPanel(Model관광지 spot) throws IOException, ParserConfigurationException, SAXException {
        JPanel weatherPanel = new JPanel(new BorderLayout());
        JPanel topPanel = getWeatherTopPanel(weatherPanel);
        weatherPanel.add(topPanel, BorderLayout.NORTH);

        return weatherPanel;
    }

    // 즐겨찾기 상태 체크
    public void checkFavoritedStatus() {
         isFavorited = mainService.checkSpotFavoritedStatus(spot.get관광지ID());
         favoriteButton.setText(isFavorited==true ?"즐겨찾기 삭제" : "즐겨찾기 등록");
    }


    // 날씨 정보 상단 패널
    private JPanel getWeatherTopPanel(JPanel weatherPanel) {
        JPanel topPanel = new JPanel();

        JLabel dayLabel1 = new JLabel("오늘부터 ");
        String[] dropdownOptions = {"1", "2", "3", "4", "5","6"};
        JLabel dayLabel2 = new JLabel("일 예보");
        JComboBox<String> dropdown = new JComboBox<>(dropdownOptions);
        topPanel.add(dayLabel1);
        topPanel.add(dropdown);
        topPanel.add(dayLabel2);

        // 요구사항을 위한 그래픽 버튼
        GraphicButton graphicButton = new GraphicButton();
        graphicButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                // 이미 날씨 그려져있으면 제거 후 추가
                Component centerComponent = ((BorderLayout) weatherPanel.getLayout()).getLayoutComponent(BorderLayout.CENTER);
                if(centerComponent!=null){
                    weatherPanel.remove(centerComponent);
                }

                // day와 spot을 넘기면서 BarchartPanel 초기화후 스크롤패인에 넣어준다.
                JScrollPane scrollPane = new JScrollPane(new BarChartPanel(Integer.parseInt((String)dropdown.getSelectedItem()),spot));
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                weatherPanel.add(scrollPane, BorderLayout.CENTER);
                repaint();
            }
        });
        topPanel.add(graphicButton);

        return topPanel;
    }

    // 요구 사항을 위한 그래픽버튼
    private static class GraphicButton extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.GRAY);
            g.drawRect(0,3,30,20);
            g.setColor(Color.BLACK);
            g.drawString("확인",4,19);
        }
        public Dimension getPreferredSize() {
            return new Dimension(35, 30);
        }
    }



    // 버튼 액션 리스너
    private class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();

            if (clickedButton == closeButton) {  // "닫기"
                dispose();
            }
            else if (clickedButton == favoriteButton) {  // "검색"
                if(isFavorited==false){
                    if(mainService.addFavoriteSpot(spot.get관광지ID())){
                        JOptionPane.showMessageDialog(null, "즐겨찾기 등록 되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "즐겨찾기 등록 오류", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    if(mainService.deleteFavoriteSpot(spot.get관광지ID())){
                        JOptionPane.showMessageDialog(null, "즐겨찾기 삭제 되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "즐겨찾기 삭제 오류", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
                checkFavoritedStatus();
                ViewControl vc = (ViewControl) parentFrame;
                if (vc != null && vc.isFavTab()) {
                    vc.createSpotTableWithFilteredData();
                }
            }
        }
    }
}
