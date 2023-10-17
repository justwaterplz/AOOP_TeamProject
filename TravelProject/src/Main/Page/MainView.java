package Main.Page;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends PageBase {
    private JPanel mainPanel;
    private JPanel searchPanel;
    private JPanel themePanel;
    private JTextField searchTextField;
    private JButton th01;
    private JButton th02;
    private JButton th03;
    private JButton th04;
    private JButton th05;
    private JButton th06;
    private JButton searchButton;
    private JPanel prevPanel;

    public MainView(PageChangeListener _listener) {
        super(_listener);

        th01.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.onPageChanged(new ListView(listener).getMainPanel());
            }
        });
    }

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
