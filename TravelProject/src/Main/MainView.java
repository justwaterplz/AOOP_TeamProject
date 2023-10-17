package Main;

import javax.swing.*;

public class MainView {
    private JPanel Main;
    private JPanel Panel1;
    private JPanel Panel2;
    private JTextField searchTextField;
    private JButton th01;
    private JButton th03;
    private JButton th04;
    private JButton th05;
    private JButton th06;
    private JButton th02;
    private JButton searchButton;

    public MainView() {
        JFrame frame = new JFrame("Demo");
        frame.setContentPane(this.Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
