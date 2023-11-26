package Main;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class CourseDetail extends JDialog {
    private JLabel courseLabel;
    private JTable listTable;
    private JPanel buttonPanel;
    private JButton courseNameChangeButton;
    private JButton tourSpotDeleteButton;
    private JButton upwardButton;
    private JButton downwardButton;
    private JButton saveButton;

    public CourseDetail(String _courseName, JFrame parentFrame) {
        // 모달 다이얼로그 창 생성
        super(parentFrame, "", true);

    public CourseDetail(String _courseName) {
        setLayout(new BorderLayout());

        // 코스 이름
        courseLabel = new JLabel(_courseName);
        courseLabel.setHorizontalAlignment(JLabel.CENTER);
        add(courseLabel, BorderLayout.NORTH);

        // 코스에 포함된 관광지 목록
        Vector<String> tourSpotListVector = new Vector<String>();
        tourSpotListVector.add("관광지명");
        tourSpotListVector.add("지역");
        tourSpotListVector.add("테마");
        tourSpotListVector.add("실내/외");

        //defaultTableModel 생성
        DefaultTableModel model = new DefaultTableModel(tourSpotListVector, 0){
            public boolean isCellEditable(int r, int c){
                return false;
            }
        };

        // 임시 데이터
        Vector<String> v1 = new Vector<String>();
        v1.add("내 코스1");
        v1.add("내 코스1");
        v1.add("내 코스1");
        v1.add("내 코스1");
        model.addRow(v1);
        Vector<String> v2 = new Vector<String>();
        v2.add("내 코스2");
        v2.add("내 코스2");
        v2.add("내 코스2");
        v2.add("내 코스2");
        model.addRow(v2);

        listTable = new JTable(model);
        listTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // 단일 선택 모델로 변경
        ListSelectionModel selectionModel = new DefaultListSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listTable.setSelectionModel(selectionModel);

        // 표에 선택 리스너 추가
        listTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    // 선택된 행의 데이터를 출력 (예시)
                    int selectedRow = listTable.getSelectedRow();
                    if (selectedRow != -1) {
                        System.out.println(listTable.getValueAt(selectedRow, 0));
                    }
                }
            }
        });

        //  스크롤
        JScrollPane scrollPane = new JScrollPane(listTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        listTable.setFillsViewportHeight(true);
        add(scrollPane, BorderLayout.CENTER);

        //버튼 추가
        buttonPanel = new JPanel(new GridLayout(0, 1));

        courseNameChangeButton = new JButton("코스 이름 변경");
        tourSpotDeleteButton = new JButton("관광지 삭제");
        upwardButton = new JButton("Up");
        downwardButton = new JButton("Down");
        saveButton = new JButton("저장");

        buttonPanel.add(courseNameChangeButton);
        buttonPanel.add(tourSpotDeleteButton);
        buttonPanel.add(upwardButton);
        buttonPanel.add(downwardButton);
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.EAST);

        // 다이얼로그 기본 설정
        pack();
        setFrameLocationToCenter();
        setVisible(true);
    }

    private void setFrameLocationToCenter() {
        // 화면 크기 구하기
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // 프레임 크기 구하기
        Dimension frameSize = getSize();
        // 화면 중앙에 프레임 위치 계산
        int x = (screenSize.width - frameSize.width) / 2;
        int y = (screenSize.height - frameSize.height) / 2;
        // 프레임 위치 설정
        setLocation(x, y);
    }
}