package Main.Page;


import Main.Repository.MainRepository;
import Main.Service.MainService;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

abstract public class PageBase {
    protected PageChangeListener listener;
    protected final MainService mainService;

    public PageBase(PageChangeListener listener) {
        this.mainService = new MainService();
        this.listener = listener;
    }

    abstract public JPanel getMainPanel();

    // TODO : 현재는 image button으로 가정하고 메소드 작성, 추후에 필요하면 image button이 아닌 경우의 함수 추가 필요
    protected MouseListener getButtonMouseListener(JButton button, Color pressed, Color rollover) {
        return new MouseListener() {
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // 버튼 클릭 시 색상 변경을 위한 UIManager 설정
                UIManager.put("Button.select", new ColorUIResource(pressed));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setContentAreaFilled(false);
                button.setBackground(UIManager.getColor("control")); // 버튼에서 마우스가 나갔을 때 기본 배경색으로 변경
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setContentAreaFilled(true);
                button.setBackground(rollover); // 버튼에 마우스가 들어왔을 때 배경색 변경
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setContentAreaFilled(false);
                button.setBackground(UIManager.getColor("control")); // 버튼에서 마우스가 나갔을 때 기본 배경색으로 변경
            }
        };
    }

    protected void initializeImageButton(JButton button) {
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(Color.BLACK);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
    }
}
