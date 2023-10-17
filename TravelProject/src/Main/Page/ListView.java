package Main.Page;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListView extends PageBase {
    private JPanel mainPanel;
    private JButton backButton;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button1;
    private JButton button6;
    private JButton button7;

    public ListView(PageChangeListener _listener) {
        super(_listener);

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
