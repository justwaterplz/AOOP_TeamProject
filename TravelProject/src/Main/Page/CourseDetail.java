package Main.Page;

import Main.Model.Model관광지;
import Main.Module.SpotNameManager;
import Main.Service.MainService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CourseDetail extends JDialog {
    private final MainService mainService;
    private JFrame parentFrame;
    private JLabel courseLabel;
    private JTable listTable;
    private JPanel buttonPanel;
    private JButton favoriteButton;
    private JButton closeButton;
    private boolean isFavorited;
    private boolean isFavoriteTab;
    private int courseID;

    public CourseDetail(JFrame _parentFrame, String _courseName, ArrayList<Model관광지> 관광지List, boolean _isFavoriteTab) {
        // 모달 다이얼로그 창 생성
        super(_parentFrame, "", true);
        this.parentFrame = _parentFrame;
        this.mainService = new MainService();
        this.isFavoriteTab = _isFavoriteTab;
        this.courseID = Integer.parseInt(_courseName);

        // 다이얼로그가 닫힐 때 특정 작업 수행을 위한 WindowListener 추가
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ViewControl frame = (ViewControl)parentFrame;
                if (frame != null) {
                    frame.darkenBackground(false);
                }
            }
            @Override
            public void windowClosed(WindowEvent e) {
                // 창이 닫힌 후 수행할 동작
                ViewControl frame = (ViewControl) parentFrame;
                if (frame != null) {
                    frame.darkenBackground(false);
                }
            }
        });

        initializeUI(_courseName, 관광지List);
        checkFavoritedStatus();
        setVisible(true);
    }

    private void initializeUI(String _courseName, ArrayList<Model관광지> 관광지List) {
        setLayout(new BorderLayout());

        // 코스 이름
        courseLabel = new JLabel(_courseName);
        courseLabel.setHorizontalAlignment(JLabel.CENTER);
        // 폰트 크기 조절
        Font originalFont = courseLabel.getFont();
        Font newFont = originalFont.deriveFont(originalFont.getSize() * 1.3f); // 1.5배 크기로 조절
        courseLabel.setFont(newFont);
        // 상하좌우로 빈 공간 추가
        courseLabel.setBorder(BorderFactory.createEmptyBorder(7, 0, 7, 0));

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

        // 데이터 설정
        for (int i = 0; i < 관광지List.size(); ++i) {
            Model관광지 spot = 관광지List.get(i);
            Vector<String> v = new Vector<>();
            v.add(SpotNameManager.getSpotName(spot.get관광지명()));
            v.add(SpotNameManager.getSpotLocate(spot.get관광지명()));
            v.add(SpotNameManager.getThemeName(spot.get테마분류()));
            v.add(spot.get실내구분());
            model.addRow(v);
        }

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

        // 즐겨찾기 버튼
        favoriteButton = new JButton("");
        favoriteButton.addActionListener(new ButtonActionListener());
        // 닫기 버튼
        closeButton = new JButton("닫기");
        closeButton.addActionListener(new ButtonActionListener());

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        favoriteButton.setPreferredSize(new Dimension(150, 30)); // 예시 크기
        closeButton.setPreferredSize(new Dimension(150, 30)); // 예시 크기
        buttonPanel.add(favoriteButton);
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // 다이얼로그 기본 설정
        pack();
        setFrameLocationToCenter();
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

    // 즐겨찾기 상태 체크
    public void checkFavoritedStatus() {
        isFavorited = mainService.checkCourseFavoritedStatus(courseID);
        favoriteButton.setText(isFavorited == true ? "즐겨찾기 삭제" : "즐겨찾기 등록");
    }

    // 버튼 액션 리스너
    private class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();

            if (clickedButton == closeButton) {  // "닫기"
                dispose();
            }
            else if (clickedButton == favoriteButton) {  // "검색"
                if(isFavorited == false){
                    if(mainService.addFavoriteCourse(courseID)){
                        JOptionPane.showMessageDialog(null, "즐겨찾기 등록 되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "즐겨찾기 등록 오류", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    if(mainService.deleteFavoriteCourse(courseID)){
                        JOptionPane.showMessageDialog(null, "즐겨찾기 삭제 되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "즐겨찾기 삭제 오류", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
                checkFavoritedStatus();
                ViewControl vc = (ViewControl) parentFrame;
                if (vc != null) {
                    vc.createCourseTable(isFavoriteTab);
                }
            }
        }
    }
}