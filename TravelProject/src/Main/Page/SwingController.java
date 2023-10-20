package Main.Page;

import javax.swing.*;

public class SwingController implements PageChangeListener {
    private final JFrame frame;
    private JPanel currentPage;
    private JPanel prevPage;

    public SwingController() {
        frame = new JFrame("여행의 민족");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setSize(500, 600);
        currentPage = new MainView(this).getMainPanel();
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
