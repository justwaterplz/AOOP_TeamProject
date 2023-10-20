package Main.Page;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class MainView extends PageBase {
    private JPanel mainPanel;
    private JPanel searchPanel;
    private JPanel themePanel;
    private JTextField searchTextField;
    private JButton th01Button;
    private JButton th02Button;
    private JButton th03Button;
    private JButton th04Button;
    private JButton th05Button;
    private JButton th06Button;
    private JButton searchButton;
    private JButton insideButton;
    private JButton outsideButton;
    private JButton[] actionButtons = {th01Button, th02Button, th03Button, th04Button, th05Button, th06Button, searchButton, insideButton, outsideButton};
    private JPanel prevPanel;

    public MainView(PageChangeListener _listener) {
        super(_listener);

        // 버튼 리스너 등록
        for (JButton button : actionButtons) {
            button.addActionListener(new ButtonActionListener());
        }
    }


    // 버튼 액션 리스너
    private class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            Map<String,String> filterData = new HashMap<>();

            if (clickedButton == searchButton) {  // "검색"
                filterData.put("FilterType","검색");
                filterData.put("FilterData",searchTextField.getText().toString());
            } else if (clickedButton == th01Button) {   // "문화예술"
                filterData.put("FilterType","테마");
                filterData.put("FilterData","TH01");
            } else if (clickedButton == th02Button) {   // "자연힐링"
                filterData.put("FilterType","테마");
                filterData.put("FilterData","TH02");
            } else if (clickedButton == th03Button) {   // "캠핑스포츠"
                filterData.put("FilterType","테마");
                filterData.put("FilterData","TH03");
            } else if (clickedButton == th04Button) {   // "종교역사전통"
                filterData.put("FilterType","테마");
                filterData.put("FilterData","TH04");
            } else if (clickedButton == th05Button) {   // "쇼핑놀이"
                filterData.put("FilterType","테마");
                filterData.put("FilterData","TH05");
            } else if (clickedButton == th06Button) {   // "체험학습산업"
                filterData.put("FilterType","테마");
                filterData.put("FilterData","TH06");
            } else if (clickedButton == insideButton) { // "실내"
                filterData.put("FilterType","실내외");
                filterData.put("FilterData","실내");
            } else if (clickedButton == outsideButton) {  // "실외"
                filterData.put("FilterType","실내외");
                filterData.put("FilterData","실외");
            }

            listener.onPageChanged(new ListView(listener,filterData).getMainPanel());
        }
    }

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
