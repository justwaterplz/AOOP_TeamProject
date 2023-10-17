package Main.Page;

import javax.swing.*;

public class SwingController implements PageChangeListener {
    private final JFrame frame;
    private JPanel currentPage;
    private JPanel prevPage;

    public SwingController() {
        frame = new JFrame("Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(300, 200);
        frame.pack();
        currentPage = new MainView(this).mainPanel;
        frame.add(currentPage);
        frame.setVisible(true);
    }

    @Override
    public void onPageChanged(JPanel newPage) {
        prevPage = currentPage;
        currentPage = newPage;
        frame.getContentPane().removeAll();
        frame.getContentPane().add(currentPage);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }

    @Override
    public void returnToPrevPage() {
        currentPage = prevPage;
        frame.getContentPane().removeAll();
        frame.getContentPane().add(currentPage);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }
}
