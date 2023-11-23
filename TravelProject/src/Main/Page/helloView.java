package Main.Page;

import Main.Module.NonEditableTableModel;

import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Vector;
import java.util.ArrayList;
import java.util.List;

public class helloView extends JFrame {
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
    private JCheckBox historyCheckBox;
    private JCheckBox cultureCheckBox;
    private JCheckBox natureCheckBox;
    private JCheckBox experienceCheckBox;
    private JCheckBox campingCheckBox;
    private JCheckBox shoppingCheckBox;
    private JCheckBox indoorCheckBox;
    private JCheckBox outdoorCheckBox;

    private JPanel searchPanel;
    private JTextField searchField;
    private JButton searchButton;

    private JPanel coursePanel;
    private JTable courseTable;
    private JPanel courseButtonPanel;
    private JButton courseAddButton;
    private JButton courseDeleteButton;

    public helloView() {
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
        historyCheckBox = new JCheckBox("종교/역사/전통");
        bottomFilterPanel1.add(historyCheckBox);
        cultureCheckBox = new JCheckBox("문화/예술");
        bottomFilterPanel1.add(cultureCheckBox);
        natureCheckBox = new JCheckBox("자연/힐링");
        bottomFilterPanel1.add(natureCheckBox);

        gbc.gridx = 0;
        gbc.gridy = 2;
        filteringPanel.add(bottomFilterPanel1, gbc);

        // 하단 체크박스들
        JPanel bottomFilterPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        experienceCheckBox = new JCheckBox("체험/학습/산업");
        bottomFilterPanel2.add(experienceCheckBox);
        campingCheckBox = new JCheckBox("캠핑/스포츠");
        bottomFilterPanel2.add(campingCheckBox);
        shoppingCheckBox = new JCheckBox("쇼핑/놀이");
        bottomFilterPanel2.add(shoppingCheckBox);

        gbc.gridx = 0;
        gbc.gridy = 3;
        filteringPanel.add(bottomFilterPanel2, gbc);

        // 하단 체크박스들
        JPanel bottomFilterPanel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        indoorCheckBox = new JCheckBox("실내");
        bottomFilterPanel3.add(indoorCheckBox);
        outdoorCheckBox = new JCheckBox("실외");
        bottomFilterPanel3.add(outdoorCheckBox);

        gbc.gridx = 0;
        gbc.gridy = 4;
        filteringPanel.add(bottomFilterPanel3, gbc);

        // 검색창 및 검색 버튼을 담은 패널
        searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBackground(new Color(255, 255, 255));  // 배경색 설정
        searchField.setBorder(BorderFactory.createLineBorder(Color.BLACK));  // 테두리 설정

        searchButton = new JButton("검색");
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
        createTable();

        setTitle("여행의 민족");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(mainPanel);
        //setSize(800, 600);
        pack();
        setVisible(true);
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

    // 표 생성 data는 임시로 했지만 검색버튼 누르면 저장소로부터 불러와야함
    private void createTable() {
        // 표 데이터
        String[] columnNames = {"관광지명", "지역", "실내/외"};

        // 임시 데이터
        Object[][] data = {
                {"아이템1", "실내", "A구분", "서울"},
                {"아이템2", "외부", "B구분", "부산"},
        };

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
    }

    // 각 탭에 이미지를 표시하는 패널 생성
    private JPanel createImagePanel(String imageName) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel imageLabel = new JLabel(new ImageIcon(imageName + ".png"));
        panel.add(imageLabel, BorderLayout.CENTER);
        return panel;
    }
}
