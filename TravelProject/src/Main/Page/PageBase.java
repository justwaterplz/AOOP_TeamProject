package Main.Page;


import javax.swing.*;

abstract public class PageBase {
    protected PageChangeListener listener;

    public PageBase(PageChangeListener listener) {
        this.listener = listener;
    }

    abstract public JPanel getMainPanel();
}
