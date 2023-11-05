package Main.Page;

import javax.swing.*;
import java.util.Stack;

public class SwingController implements PageChangeListener {
    private final JFrame frame;
    private JPanel currentPage;
    private static Stack<JPanel> pageStack = new Stack<>();

    public SwingController() {
        frame = new JFrame("여행의 민족");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setSize(700, 600);
        currentPage = new MainView(this).getMainPanel();
        pageStack.push(currentPage);
        frame.add(currentPage);
        frame.setVisible(true);
    }

    @Override
    public void onPageChanged(JPanel newPage) {
        pageStack.push(newPage);
        currentPage = newPage;
        frame.getContentPane().removeAll();
        frame.getContentPane().add(currentPage);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }

    @Override
    public void returnToPrevPage() {
        pageStack.pop();
        currentPage = pageStack.peek();
        frame.getContentPane().removeAll();
        frame.getContentPane().add(currentPage);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }
}
