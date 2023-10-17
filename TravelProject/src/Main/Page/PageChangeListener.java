package Main.Page;

import javax.swing.*;

public interface PageChangeListener {
    void onPageChanged(JPanel newPage);

    void returnToPrevPage();
}
