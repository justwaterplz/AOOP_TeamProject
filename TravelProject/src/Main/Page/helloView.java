package Main.Page;

import Main.Model.Model관광지;
import Main.Module.NonEditableTableModel;
import Main.Module.SpotNameManager;
import Main.Service.MainService;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.*;

import java.util.Vector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class helloView extends JFrame implements ViewControl {
    JTabbedPane tabbedPane;
    private JPanel mainPanel;
    private JPanel favPanel;
    private JPanel topPanel;
    private JPanel favTopPanel;
    private JPanel bottomPanel;
    private JPanel favBottomPanel;
    private JLabel searchResultLabel;
    private JLabel favSearchResultLabel;
    private JTable table;
    private JTable favTable;
    private JMenuBar menuBar;
    private JPanel filteringPanel;
    private JPanel favFilteringPanel;
    private GridBagConstraints gbc;
    private GridBagConstraints favGbc;
    private JCheckBox TH01CheckBox;
    private JCheckBox favTH01CheckBox;
    private JCheckBox TH02CheckBox;
    private JCheckBox favTH02CheckBox;
    private JCheckBox TH03CheckBox;
    private JCheckBox favTH03CheckBox;
    private JCheckBox TH04CheckBox;
    private JCheckBox favTH04CheckBox;
    private JCheckBox TH05CheckBox;
    private JCheckBox favTH05CheckBox;
    private JCheckBox TH06CheckBox;
    private JCheckBox favTH06CheckBox;
    private ButtonGroup indoorOutdoorButtonGroup;
    private ButtonGroup favIndoorOutdoorButtonGroup;
    private JRadioButton indoorButton;
    private JRadioButton favIndoorButton;
    private JRadioButton outdoorButton;
    private JRadioButton favOutdoorButton;
    private JRadioButton indoorOutdoorButton;
    private JRadioButton favIndoorOutdoorButton;
    private JPanel searchPanel;
    private JPanel favSearchPanel;
    private JTextField searchField;
    private JTextField favSearchField;
    private JButton searchButton;
    private JButton favSearchButton;
    private JPanel coursePanel;
    private JPanel favCoursePanel;
    private JTable courseTable;
    private JTable favCourseTable;

    JMenu fileMenu = new JMenu("파일(구현X)");
    JMenuItem newItem = new JMenuItem("New");
    JMenuItem openItem = new JMenuItem("Open");
    JMenu toolMenu = new JMenu("기능");
    JMenuItem searchSpotItem = new JMenuItem("관광지검색");
    JMenuItem favoriteCountItem = new JMenuItem("즐겨찾는 관광지 분석");

    private final MainService mainService;

    public helloView() {
        this.mainService = new MainService();

        initializeMenuBar();
        initializeMainPanel();
        initializeFavPanel();
        addGreyOutGlassPane();
        initializeFrame();
    }

    private void initializeFrame() {
        // 프레임 기본 설정
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("검색", mainPanel);
        tabbedPane.addTab("즐겨찾기", favPanel);
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (isFavTab()) {
                    createCourseTable(true);
                }
            }
        });
        add(tabbedPane);

        setTitle("여행의 민족");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setFrameLocationToCenter();
        setVisible(true);
    }

    private void initializeMenuBar() {
        menuBar = new JMenuBar();

        fileMenu.add(newItem);
        fileMenu.add(openItem);

        toolMenu.add(searchSpotItem);
        toolMenu.add(favoriteCountItem);
        searchSpotItem.addActionListener(new MenuItemActionListener());
        favoriteCountItem.addActionListener(new MenuItemActionListener());

        menuBar.add(fileMenu);
        menuBar.add(toolMenu);
        setJMenuBar(menuBar);
    }

    private void initializeMainPanel() {
        mainPanel = new JPanel(new BorderLayout());

        /**
         * 상단 Panel에 컴포넌트 추가하는 부분
         */
        topPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // 필터링 옵션과 검색을 담은 패널 (상단 좌측)
        filteringPanel = new JPanel(new GridBagLayout());
        filteringPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;

        // 빈 패널 추가 (공간을 만들기 위함)
        JPanel emptyPanel = new JPanel();
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0); // 상단 여백 추가
        filteringPanel.add(emptyPanel, gbc);

        // 하단 체크박스들
        JPanel bottomFilterPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        TH04CheckBox = new JCheckBox("캠핑/스포츠");
        TH04CheckBox.setSelected(true);
        bottomFilterPanel1.add(TH04CheckBox);
        TH01CheckBox = new JCheckBox("문화/예술");
        TH01CheckBox.setSelected(true);
        bottomFilterPanel1.add(TH01CheckBox);
        TH02CheckBox = new JCheckBox("체험/학습/산업");
        TH02CheckBox.setSelected(true);
        bottomFilterPanel1.add(TH02CheckBox);

        gbc.gridx = 0;
        gbc.gridy = 2;
        filteringPanel.add(bottomFilterPanel1, gbc);

        // 하단 체크박스들
        JPanel bottomFilterPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        TH06CheckBox = new JCheckBox("쇼핑/놀이　");
        TH06CheckBox.setSelected(true);
        bottomFilterPanel2.add(TH06CheckBox);
        TH03CheckBox = new JCheckBox("자연/힐링");
        TH03CheckBox.setSelected(true);
        bottomFilterPanel2.add(TH03CheckBox);
        TH05CheckBox = new JCheckBox("종교/역사/전통");
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
        coursePanel = new JPanel(new FlowLayout());
        topPanel.add(coursePanel, BorderLayout.CENTER);
        createCourseTable(false);

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
    }

    private void initializeFavPanel() {
        favPanel = new JPanel(new BorderLayout());

        /**
         * 상단 Panel에 컴포넌트 추가하는 부분
         */
        favTopPanel = new JPanel(new BorderLayout());
        favPanel.add(favTopPanel, BorderLayout.NORTH);

        // 필터링 옵션과 검색을 담은 패널 (상단 좌측)
        favFilteringPanel = new JPanel(new GridBagLayout());
        favFilteringPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        favGbc = new GridBagConstraints();
        favGbc.anchor = GridBagConstraints.WEST;

        // 빈 패널 추가 (공간을 만들기 위함)
        JPanel emptyPanel = new JPanel();
        favGbc.gridy = 1;
        favGbc.insets = new Insets(0, 0, 0, 0); // 상단 여백 추가
        favFilteringPanel.add(emptyPanel, favGbc);

        // 하단 체크박스들
        JPanel bottomFilterPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        favTH04CheckBox = new JCheckBox("캠핑/스포츠");
        favTH04CheckBox.setSelected(true);
        bottomFilterPanel1.add(favTH04CheckBox);
        favTH01CheckBox = new JCheckBox("문화/예술");
        favTH01CheckBox.setSelected(true);
        bottomFilterPanel1.add(favTH01CheckBox);
        favTH02CheckBox = new JCheckBox("체험/학습/산업");
        favTH02CheckBox.setSelected(true);
        bottomFilterPanel1.add(favTH02CheckBox);

        favGbc.gridx = 0;
        favGbc.gridy = 2;
        favFilteringPanel.add(bottomFilterPanel1, favGbc);

        // 하단 체크박스들
        JPanel bottomFilterPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        favTH06CheckBox = new JCheckBox("쇼핑/놀이　");
        favTH06CheckBox.setSelected(true);
        bottomFilterPanel2.add(favTH06CheckBox);
        favTH03CheckBox = new JCheckBox("자연/힐링");
        favTH03CheckBox.setSelected(true);
        bottomFilterPanel2.add(favTH03CheckBox);
        favTH05CheckBox = new JCheckBox("종교/역사/전통");
        favTH05CheckBox.setSelected(true);
        bottomFilterPanel2.add(favTH05CheckBox);

        favGbc.gridx = 0;
        favGbc.gridy = 3;
        favFilteringPanel.add(bottomFilterPanel2, favGbc);

        // 하단 체크박스들
        JPanel bottomFilterPanel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        favIndoorButton = new JRadioButton("실내");
        favOutdoorButton = new JRadioButton("실외");
        favIndoorOutdoorButton = new JRadioButton("모두");

        favIndoorOutdoorButtonGroup = new ButtonGroup();
        favIndoorOutdoorButtonGroup.add(favIndoorButton);
        favIndoorOutdoorButtonGroup.add(favOutdoorButton);
        favIndoorOutdoorButtonGroup.add(favIndoorOutdoorButton);

        favIndoorButton.setSelected(true);
        bottomFilterPanel3.add(favIndoorButton);
        bottomFilterPanel3.add(favOutdoorButton);
        bottomFilterPanel3.add(favIndoorOutdoorButton);

        favGbc.gridx = 0;
        favGbc.gridy = 4;
        favFilteringPanel.add(bottomFilterPanel3, favGbc);

        // 검색창 및 검색 버튼을 담은 패널
        favSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        favSearchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        favSearchField = new JTextField(20);
        favSearchField.setFont(new Font("NanumGothic", Font.PLAIN, 14));
        favSearchField.setBackground(new Color(255, 255, 255));  // 배경색 설정
        favSearchField.setBorder(BorderFactory.createLineBorder(Color.BLACK));  // 테두리 설정

        favSearchButton = new JButton("검색");
        favSearchButton.addActionListener(new ButtonActionListener());
        favSearchPanel.add(favSearchField);
        favSearchPanel.add(favSearchButton);

        favGbc.gridx = 0;
        favGbc.gridy = 10;
        favGbc.insets = new Insets(0, 0, 0, 0); // 상단 여백 추가
        favFilteringPanel.add(favSearchPanel, favGbc);

        favTopPanel.add(favFilteringPanel, BorderLayout.WEST);

        /**
         * 코스 목록
         */
        favCoursePanel = new JPanel(new FlowLayout());
        favTopPanel.add(favCoursePanel, BorderLayout.CENTER);
        createCourseTable(true);

        //  JSeparator(구분선) 추가
        JSeparator separator = new JSeparator();
        favTopPanel.add(separator, BorderLayout.SOUTH);

        /**
         * 하단 Panel
         */
        favBottomPanel = new JPanel(new BorderLayout());
        favPanel.add(favBottomPanel, BorderLayout.CENTER);

        // 상단 레이블
        favSearchResultLabel = new JLabel("검색 결과");
        favSearchResultLabel.setHorizontalAlignment(JLabel.CENTER);
        favBottomPanel.add(favSearchResultLabel, BorderLayout.NORTH);

        // 검색 버튼 누르면 실행되야하는 표 임시 생성
        createSpotTable(null);
    }

    // 코스 목록 생성
    private void createCourseTable(boolean isFavorite) {
        JPanel refPanel;
        if (isFavorite) {
            refPanel = favCoursePanel;
        } else {
            refPanel = coursePanel;
        }

        // 지우고 다시 그림
        refPanel.removeAll();

        // defaultTableModel 생성
        Vector<String> courseVector = new Vector<String>();
        String s = (isFavorite ? "선호코스 목록" : "코스 목록");
        courseVector.add(s);
        DefaultTableModel model = new DefaultTableModel(courseVector, 0){
            public boolean isCellEditable(int r, int c){
                return false;
            }
        };
        
        // 코스 목록 데이터 생성
        ArrayList<Integer> courseList = mainService.get코스List(isFavorite);
        for (int i = 0; i < courseList.size(); ++i) {
            Vector<String> v = new Vector<String>();
            v.add(" " + courseList.get(i));
            model.addRow(v);
        }

        // 테이블 생성
        JTable refTable;
        if (isFavorite) {
            favCourseTable = new JTable(model);
            refTable = favCourseTable;
        } else {
            courseTable = new JTable(model);
            refTable = courseTable;
        }
        refTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        refTable.setFillsViewportHeight(true);

        // 단일 선택 모델로 변경
        ListSelectionModel selectionModel = new DefaultListSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        refTable.setSelectionModel(selectionModel);

        // 팝업 메뉴 생성
        JPopupMenu popupMenu = new JPopupMenu();
        String itemStr = isFavorite ? "즐겨찾기 삭제" : "즐겨찾기 등록";
        JMenuItem registerMenuItem = new JMenuItem(itemStr);
        registerMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = refTable.getSelectedRow();
                if (selectedRow != -1) {
                    String courseName = ((String)refTable.getValueAt(selectedRow, 0)).trim();
                    int courseID = Integer.parseInt(courseName);

                    if(isFavorite == false){
                        if (mainService.checkFavoriteCourseExists(courseID))
                            JOptionPane.showMessageDialog(null, "이미 등록 되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                        else {
                            if(mainService.addFavoriteCourse(courseID)){
                                JOptionPane.showMessageDialog(null, "즐겨찾기 등록 되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "즐겨찾기 등록 오류", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        if(mainService.deleteFavoriteCourse(courseID)){
                            JOptionPane.showMessageDialog(null, "즐겨찾기 삭제 되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                            createCourseTable(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "즐겨찾기 삭제 오류", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        popupMenu.add(registerMenuItem);

        // 표에 마우스 리스너 추가
        JFrame thisFrame = this;
        refTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 우클릭 시 즐겨찾기 등록 메뉴 팝업
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = refTable.rowAtPoint(e.getPoint());
                    int column = refTable.columnAtPoint(e.getPoint());

                    if (row >= 0 && row < refTable.getRowCount() && column >= 0 && column < refTable.getColumnCount()) {
                        refTable.setRowSelectionInterval(row, row);
                        popupMenu.show(refTable, e.getX(), e.getY());
                    }
                }
                // 더블 클릭하면 코스 창 띄우기
                else if (e.getClickCount() == 2) {
                    int selectedRow = refTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String courseName = ((String)refTable.getValueAt(selectedRow, 0)).trim();
                        int courseID = Integer.parseInt(courseName);
                        darkenBackground(true);
                        new CourseDetail(thisFrame, courseName, mainService.get관광지ListIn코스(courseID));
                    }
                }
            }
        });

        //  스크롤
        JScrollPane scrollPane = new JScrollPane(refTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(300, 200));
        refPanel.add(scrollPane);

        // 컴포넌트를 다시 그리도록 갱신
        refPanel.revalidate();
        refPanel.repaint();
    }

    // 표 생성
    private void createSpotTable(ArrayList<Model관광지> touristSpotList) {

        bottomPanel.removeAll();

        // 표 데이터
        String[] columnNames = {"관광지명", "지역", "실내/외","테마"};

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
                // 테마
                String theme = SpotNameManager.getThemeName(touristSpot.get테마분류());
                // 데이터 배열에 할당
                data[i] = new Object[]{cleanName, parenthesesContent, indoorType, theme};
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

        // 표에 마우스 리스너 추가
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
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

    // 버튼 액션 리스너
    private class MenuItemActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem clickedItem = (JMenuItem) e.getSource();

            if (clickedItem == searchSpotItem) {  // "검색"
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
            else if (clickedItem == favoriteCountItem) {
                new FavoriteBarGraphView(mainService.getFavoriteThemeCounts());
            }
        }
    }

    // 현재 즐겨찾기 탭이 선택됐는지 여부를 반환한다.
    private boolean isFavTab() {
        return tabbedPane.getSelectedIndex() == 1;
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