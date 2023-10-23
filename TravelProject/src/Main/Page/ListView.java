package Main.Page;

import Main.Model.Model관광지;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class ListView extends PageBase {
    private JPanel mainPanel;
    private JPanel listPanel;
    private JButton backButton;
    private JScrollPane scrollPane;

    private ArrayList<JButton> buttonList = new ArrayList<>();
    private ArrayList<Model관광지> touristSpotList = new ArrayList<>();

    public ListView(PageChangeListener _listener, Map<String,String> filterData) {
        super(_listener);

        // 동적으로 버튼들을 담을 리스트 패널 생성
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        // 필터정보를 기준으로 TouristSpotList를 가져온다.
        touristSpotList = mainService.findTouristSpots(filterData.get("FilterType"), filterData.get("FilterData"));

        // 가져온 리스트들로 출력
        for (Model관광지 touristSpot : touristSpotList) {
            JButton button = new JButton(touristSpot.관광지명()+ "  " + touristSpot.실내구분());
            buttonList.add(button);
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
            button.setPreferredSize(new Dimension(30, 100));
            button.setMargin(new Insets(0, 8, 0, 8));
            listPanel.add(button);
        }

        // 가져온 정보가 없을 때 표시
        if(touristSpotList.size()==0){
            listPanel.add(new JLabel("결과가 없습니다."));
        }

        scrollPane.setViewportView(listPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // 액션 리스너 등록
        backButton.addActionListener(e -> listener.returnToPrevPage());
        // image button 설정        
        backButton.setFocusPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setForeground(Color.BLACK);
        backButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        backButton.setHorizontalTextPosition(SwingConstants.CENTER);
    }

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
