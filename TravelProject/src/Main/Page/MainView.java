package Main.Page;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

            if (clickedButton == searchButton) {  // "검색"
                listener.onPageChanged(new ListView(listener).getMainPanel());
            } else if (clickedButton == th01Button) {   // "문화예술"

            } else if (clickedButton == th02Button) {   // "자연힐링"

            } else if (clickedButton == th03Button) {   // "캠핑스포츠"

            } else if (clickedButton == th04Button) {   // "종교역사전통"

            } else if (clickedButton == th05Button) {   // "쇼핑놀이"

            } else if (clickedButton == th06Button) {   // "체험학습산업"

            } else if (clickedButton == insideButton) { // "실내"

            } else if (clickedButton == outsideButton) {  // "실외"

            }
        }
    }

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
