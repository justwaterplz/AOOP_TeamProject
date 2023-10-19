package Main.Page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ListView extends PageBase {
    private JPanel mainPanel;
    private JPanel listPanel;
    private JButton backButton;
    private JScrollPane scrollPane;

    private ArrayList<JButton> buttonList = new ArrayList<>();

    public ListView(PageChangeListener _listener) {
        super(_listener);

        // 동적으로 버튼들을 담을 리스트 패널 생성
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        // 버튼들 생성
        for (int i = 1; i <= 20; i++) {
            JButton button = new JButton("버튼 " + i);
            buttonList.add(button);
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
            button.setPreferredSize(new Dimension(30, 100));
            listPanel.add(button);
        }

        scrollPane.setViewportView(listPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // 액션 리스너 등록
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.returnToPrevPage();
            }
        });
    }

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
