package Main.Page;

import Main.CourseDetail;
import Main.Model.Model관광지;
import Main.Module.NonEditableTableModel;
import Main.Module.SpotNameManager;
import Main.Service.MainService;
import Main.ViewControl;

import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.Vector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class helloView extends JFrame implements ViewControl {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JLabel searchResultLabel;
    private JTable table;

    private JToolBar toolBar;
    private JButton button1;

    private JPanel filteringPanel;
    private GridBagConstraints gbc;
    private JCheckBox favoriteCheckBox;
    private JCheckBox TH01CheckBox;
    private JCheckBox TH02CheckBox;
    private JCheckBox TH03CheckBox;
    private JCheckBox TH04CheckBox;
    private JCheckBox TH05CheckBox;
    private JCheckBox TH06CheckBox;

    private ButtonGroup indoorOutdoorButtonGroup;
    private JRadioButton indoorButton;
    private JRadioButton outdoorButton;
    private JRadioButton indoorOutdoorButton;


    private JPanel searchPanel;
    private JTextField searchField;
    private JButton searchButton;

    private JPanel coursePanel;
    private JTable courseTable;

    private final MainService mainService;

    public helloView() {
        this.mainService = new MainService();
        initializeUI();
    }

    private void initializeUI() {
        mainPanel = new JPanel(new BorderLayout());
        /**
         * 상단 Panel에 컴포넌트 추가하는 부분
         */

        // 툴바 추가
        toolBar = new JToolBar();
        toolBar.setBackground(Color.WHITE);

        button1 = new JButton("Button 1");
        toolBar.add(button1);

        topPanel = new JPanel(new BorderLayout());
        topPanel.add(toolBar, BorderLayout.NORTH);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // 필터링 옵션과 검색을 담은 패널 (상단 좌측)
        filteringPanel = new JPanel(new GridBagLayout());
        filteringPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;

        // 상단 레이블 및 체크박스
        JPanel topFilterPanel = new JPanel(new GridLayout(1, 3));
        topFilterPanel.add(new JLabel("검색 필터링"));
        topFilterPanel.add(new JPanel());

        favoriteCheckBox = new JCheckBox("즐겨찾기");
        topFilterPanel.add(favoriteCheckBox);

        gbc.gridx = 0;
        gbc.gridy = 0;
        filteringPanel.add(topFilterPanel, gbc);

        // 빈 패널 추가 (공간을 만들기 위함)
        JPanel emptyPanel = new JPanel();
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0); // 상단 여백 추가
        filteringPanel.add(emptyPanel, gbc);

        // 하단 체크박스들
        JPanel bottomFilterPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        TH04CheckBox = new JCheckBox("종교/역사/전통");
        TH04CheckBox.setSelected(true);
        bottomFilterPanel1.add(TH04CheckBox);
        TH01CheckBox = new JCheckBox("문화/예술");
        TH01CheckBox.setSelected(true);
        bottomFilterPanel1.add(TH01CheckBox);
        TH02CheckBox = new JCheckBox("자연/힐링");
        TH02CheckBox.setSelected(true);
        bottomFilterPanel1.add(TH02CheckBox);

        gbc.gridx = 0;
        gbc.gridy = 2;
        filteringPanel.add(bottomFilterPanel1, gbc);

        // 하단 체크박스들
        JPanel bottomFilterPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        TH06CheckBox = new JCheckBox("체험/학습/산업");
        TH06CheckBox.setSelected(true);
        bottomFilterPanel2.add(TH06CheckBox);
        TH03CheckBox = new JCheckBox("캠핑/스포츠");
        TH03CheckBox.setSelected(true);
        bottomFilterPanel2.add(TH03CheckBox);
        TH05CheckBox = new JCheckBox("쇼핑/놀이");
        TH05CheckBox.setSelected(true);
        bottomFilterPanel2.add(TH05CheckBox);

        gbc.gridx = 0;
        gbc.gridy = 3;
        filteringPanel.add(bottomFilterPanel2, gbc);

        // 하단 체크박스들
        JPanel bottomFilterPanel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        indoorButton = new JRadioButton("실내");
        outdoorButton = new JRadioButton("실외");
        indoorOutdoorButton = new JRadioButton("모두");

        indoorOutdoorButtonGroup = new ButtonGroup();
        indoorOutdoorButtonGroup.add(indoorButton);
        indoorOutdoorButtonGroup.add(outdoorButton);
        indoorOutdoorButtonGroup.add(indoorOutdoorButton);

        indoorButton.setSelected(true);
        bottomFilterPanel3.add(indoorButton);
        bottomFilterPanel3.add(outdoorButton);
        bottomFilterPanel3.add(indoorOutdoorButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        filteringPanel.add(bottomFilterPanel3, gbc);

        // 검색창 및 검색 버튼을 담은 패널
        searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        searchField = new JTextField(20);
        searchField.setFont(new Font("NanumGothic", Font.PLAIN, 14));
        searchField.setBackground(new Color(255, 255, 255));  // 배경색 설정
        searchField.setBorder(BorderFactory.createLineBorder(Color.BLACK));  // 테두리 설정

        searchButton = new JButton("검색");
        searchButton.addActionListener(new ButtonActionListener());
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.insets = new Insets(0, 0, 0, 0); // 상단 여백 추가
        filteringPanel.add(searchPanel, gbc);

        topPanel.add(filteringPanel, BorderLayout.WEST);

        /**
         * 코스 목록
         */
        createCourseTable();

        //  JSeparator(구분선) 추가
        JSeparator separator = new JSeparator();
        topPanel.add(separator, BorderLayout.SOUTH);

        /**
         * 하단 Panel
         */

        bottomPanel = new JPanel(new BorderLayout());
        mainPanel.add(bottomPanel, BorderLayout.CENTER);

        // 상단 레이블
        searchResultLabel = new JLabel("검색 결과");
        searchResultLabel.setHorizontalAlignment(JLabel.CENTER);
        bottomPanel.add(searchResultLabel, BorderLayout.NORTH);

        // 검색 버튼 누르면 실행되야하는 표 임시 생성
        createSpotTable(null);

        // 화면을 어둡게 만들기 위한 GlassPane 추가
        addGreyOutGlassPane();

        // 프레임 기본 설정
        setTitle("여행의 민족");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(mainPanel);
        pack();
        setFrameLocationToCenter();
        setVisible(true);
    }

    // 코스 목록 생성
    private void createCourseTable() {
        Vector<String> courseVector = new Vector<String>();
        courseVector.add("코스 목록");

        //defaultTableModel 생성
        DefaultTableModel model = new DefaultTableModel(courseVector, 0){
            public boolean isCellEditable(int r, int c){
                return false;
            }
        };
        
        // 코스 목록 데이터 생성
        ArrayList<Integer> courseList = mainService.get코스List();
        for (int i = 0; i < courseList.size(); ++i) {
            Vector<String> v = new Vector<String>();
            v.add(" " + courseList.get(i));
            model.addRow(v);
        }

        courseTable = new JTable(model);
        courseTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // 단일 선택 모델로 변경
        ListSelectionModel selectionModel = new DefaultListSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        courseTable.setSelectionModel(selectionModel);

        // 팝업 메뉴 생성
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem registerMenuItem = new JMenuItem("즐겨찾기 등록");

        registerMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = courseTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Implement your edit logic here
                    System.out.println("즐겨찾기 등록 : " + courseTable.getValueAt(selectedRow, 0));
                    // 등록 완료 알림 띄우기
                    JOptionPane.showMessageDialog(null, "즐겨찾기 등록이 완료되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        popupMenu.add(registerMenuItem);

        // 표에 마우스 리스너 추가
        JFrame thisFrame = this;
        courseTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 우클릭 시 즐겨찾기 등록 메뉴 팝업
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = courseTable.rowAtPoint(e.getPoint());
                    int column = courseTable.columnAtPoint(e.getPoint());

                    if (row >= 0 && row < courseTable.getRowCount() && column >= 0 && column < courseTable.getColumnCount()) {
                        courseTable.setRowSelectionInterval(row, row);
                        popupMenu.show(courseTable, e.getX(), e.getY());
                    }
                }
                // 더블 클릭하면 코스 창 띄우기
                else if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();

                    if (row != -1) {
                        String courseName = ((String)target.getValueAt(row, 0)).trim();
                        int courseID = Integer.parseInt(courseName);
                        darkenBackground(true);
                        new CourseDetail(thisFrame, courseName, mainService.get관광지ListIn코스(courseID));
                    }
                }
            }
        });

        //  스크롤
        JScrollPane scrollPane = new JScrollPane(courseTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(300, 200));

        courseTable.setFillsViewportHeight(true);
        coursePanel = new JPanel(new FlowLayout());
        coursePanel.add(scrollPane);

        topPanel.add(coursePanel, BorderLayout.CENTER);
    }

    // 표 생성
    private void createSpotTable(ArrayList<Model관광지> touristSpotList) {

        bottomPanel.removeAll();

        // 표 데이터
        String[] columnNames = {"관광지명", "지역", "실내/외"};

        Object[][] data;
        if(touristSpotList == null){
            data = null;
        } else {
            data = new Object[touristSpotList.size()][3];
            for (int i = 0; i < touristSpotList.size(); i++) {
                Model관광지 touristSpot = touristSpotList.get(i);
                // 괄호를 제거한 관광지명
                String cleanName = SpotNameManager.getSpotName(touristSpot.get관광지명());
                // 괄호 안의 내용
                String parenthesesContent = SpotNameManager.getSpotLocate(touristSpot.get관광지명());
                // 실내구분
                String indoorType = touristSpot.get실내구분();
                // 데이터 배열에 할당
                data[i] = new Object[]{cleanName, parenthesesContent, indoorType};
            }
        }
        // 표 모델 생성
        NonEditableTableModel tableModel = new NonEditableTableModel(data, columnNames);

        // 표 생성
        table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // 단일 선택 모델로 변경
        ListSelectionModel selectionModel = new DefaultListSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionModel(selectionModel);

        // 표에 선택 리스너 추가
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        try {
                            new SpotDetailView(touristSpotList.get(selectedRow));
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        System.out.println();
                    }
                }
            }
        });

        //  스크롤
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        table.setFillsViewportHeight(true);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        // 컴포넌트를 다시 그리도록 갱신
        bottomPanel.revalidate();
        bottomPanel.repaint();
    }


    // 각 탭에 이미지를 표시하는 패널 생성
    private JPanel createImagePanel(String imageName) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel imageLabel = new JLabel(new ImageIcon(imageName + ".png"));
        panel.add(imageLabel, BorderLayout.CENTER);
        return panel;
    }

    // 버튼 액션 리스너
    private class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();

            if (clickedButton == searchButton) {  // "검색"
                Map<String, Object> filterData = new HashMap<>();
                filterData.put("favoriteChecked", favoriteCheckBox.isSelected());
                filterData.put("TH01Checked", TH01CheckBox.isSelected());
                filterData.put("TH02Checked", TH02CheckBox.isSelected());
                filterData.put("TH03Checked", TH03CheckBox.isSelected());
                filterData.put("TH04Checked", TH04CheckBox.isSelected());
                filterData.put("TH05Checked", TH05CheckBox.isSelected());
                filterData.put("TH06Checked", TH06CheckBox.isSelected());
                filterData.put("isIndoorChecked", indoorButton.isSelected());
                filterData.put("isOutdoorChecked", outdoorButton.isSelected());
                filterData.put("isIndoorOutdoorChecked", indoorOutdoorButton.isSelected());
                filterData.put("searchText", searchField.getText());

                ArrayList<Model관광지> touristSpotList = mainService.getTouristSpotListByFilters(filterData);
                createSpotTable(touristSpotList);
            }
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    // 프레임 정중앙을 화면 정중앙에 위치시킨다.
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

    // 화면을 어둡게 만들기 위한 GlassPane을 추가한다.
    @Override
    public void addGreyOutGlassPane() {
        setGlassPane(new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();

                // 명도를 조절하여 어둡게 만듭니다.
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.dispose();
            }
        });
        getGlassPane().setVisible(false);
    }

    @Override
    public void darkenBackground(boolean isDarken) {
        getGlassPane().setVisible(isDarken);
    }
}