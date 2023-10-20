package Main.Page;


import Main.Repository.MainRepository;
import Main.Service.MainService;

import javax.swing.*;

abstract public class PageBase {
    protected PageChangeListener listener;
    protected final MainService mainService;

    public PageBase(PageChangeListener listener) {
        this.mainService = new MainService();
        this.listener = listener;
    }

    abstract public JPanel getMainPanel();
}
