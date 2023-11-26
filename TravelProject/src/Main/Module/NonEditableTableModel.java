package Main.Module;

import javax.swing.table.DefaultTableModel;

/**
 * 수정 불가능한 표
 */
public class NonEditableTableModel extends DefaultTableModel {
    public NonEditableTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // 수정 불가능하게 설정
    }
}
