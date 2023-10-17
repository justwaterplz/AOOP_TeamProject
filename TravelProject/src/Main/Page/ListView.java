package Main.Page;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListView {
    private PageChangeListener listener;

    public JPanel mainPanel;
    private JButton backButton;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;

    public ListView(PageChangeListener listener) {
        this.listener = listener;

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //listener.onPageChanged(new MainView(listener).mainPanel);
                listener.returnToPrevPage();
            }
        });
    }

}
