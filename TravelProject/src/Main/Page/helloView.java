package Main.Page;

import Main.Model.Model관광지;
import Main.Module.NonEditableTableModel;
import Main.Service.MainService;

import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class helloView extends JFrame{
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
    private JPanel courseButtonPanel;
    private JButton courseAddButton;
    private JButton courseDeleteButton;

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

        // 검색 버튼 누르면 실행되야하는 표 생성 임시함수
        createTable(null);

        setTitle("여행의 민족");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(mainPanel);
        pack();
        setFrameLocationToCenter();
        setVisible(true);
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

    // 코스 목록 생성
    private void createCourseTable() {
        Vector<String> courseVector = new Vector<String>();
        courseVector.add("코스 목록");

        //defaultTableModel 생성
        DefaultTableModel model = new DefaultTableModel(courseVector, 0){
            public boolean isCellEditable(int r, int c){
                return (c!=0) ? true : false;
            }
        };
        
        // 임시 데이터
        Vector<String> v1 = new Vector<String>();
        v1.add("내 코스1");
        model.addRow(v1);
        Vector<String> v2 = new Vector<String>();
        v2.add("내 코스2");
        model.addRow(v2);

        courseTable = new JTable(model);
        courseTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // 단일 선택 모델로 변경
        ListSelectionModel selectionModel = new DefaultListSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        courseTable.setSelectionModel(selectionModel);

        // 표에 선택 리스너 추가
        courseTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    // 선택된 행의 데이터를 출력 (예시)
                    int selectedRow = courseTable.getSelectedRow();
                    if (selectedRow != -1) {
                        System.out.println(courseTable.getValueAt(selectedRow, 0));
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

        //버튼 추가
        courseButtonPanel = new JPanel(new GridLayout(0, 1));

        courseAddButton = new JButton("코스 추가");
        courseDeleteButton = new JButton("코스 삭제");
        courseButtonPanel.add(courseAddButton);
        courseButtonPanel.add(courseDeleteButton);

        coursePanel.add(courseButtonPanel);

        topPanel.add(coursePanel, BorderLayout.CENTER);
    }

    // 표 생성
    private void createTable(ArrayList<Model관광지> touristSpotList) {

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
                String cleanName = removeParentheses(touristSpot.get관광지명());
                // 괄호 안의 내용
                String parenthesesContent = extractParenthesesContent(touristSpot.get관광지명());
                // 실내구분
                String indoorType = touristSpot.get실내구분();
                // 데이터 배열에 할당
                data[i] = new Object[]{cleanName, parenthesesContent, indoorType};
                System.out.println(data[i][0]+" "+ data[i][1]+" "+data[i][2]);
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
                    // 선택된 행의 데이터를 출력 (예시)
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        for (int i = 0; i < table.getColumnCount(); i++) {
                            System.out.print(table.getValueAt(selectedRow, i) + " ");
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
                createTable(touristSpotList);
            }
        }
    }

    // 정규 표현식을 사용하여 괄호와 괄호 안의 내용을 제거 (관광지명에서 이름만 빼오기)
    public static String removeParentheses(String input) {
        return input.replaceAll("\\([^\\)]*\\)", "").trim();
    }

    // 정규 표현식을 사용하여 괄호와 괄호 안의 내용을 제거 (관광지명에서 지역만 빼오기)
    public static String extractParenthesesContent(String input) {
        Matcher matcher = Pattern.compile("\\(([^)]+)\\)").matcher(input);
        return matcher.find() ? matcher.group(1).trim() : "";
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
