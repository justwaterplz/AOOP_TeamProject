package Main.Page;

import Main.Module.SpotNameManager;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class FavoriteBarGraphView extends JFrame {

    private Map<String, Integer> countMap;

    FavoriteBarGraphView(Map<String, Integer> countMap){
        this.countMap = countMap;
        setTitle("즐겨찾는 관광지 테마별 개수 막대 그래프");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        add(new BarChartPanel());
        setVisible(true);
    }

    public class BarChartPanel extends JPanel {
        private Map<String, Color> themeColors;

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            themeColors = createThemeColors();

            // 테두리를 그릴 사각형 좌표 및 크기 계산
            int rectX = 40;
            int rectY = 10;
            int rectWidth = getWidth() - 80;
            int rectHeight = getHeight() - 40;

            // 전체 그래프에 테두리 그리기
            g.setColor(Color.BLACK);
            g.drawRect(rectX, rectY, rectWidth, rectHeight);

            // 바 너비
            int barWidth = 50;
            // 바 높이를 최대값을 기준으로 하기위해 구하는 최대값
            int maxValue = countMap.values().stream().max(Integer::compareTo).orElse(0);
            // 바 간격
            int x = 50;
            for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
                String theme = entry.getKey();
                int count = entry.getValue();

                int barHeight = (int) (((double) count / maxValue) * getHeight())-70;

                // 테마에 따라 색상 가져오기
                Color themeColor = themeColors.getOrDefault(theme, Color.BLACK);

                // 테마label 너비 고려하여 가로 위치 조절
                int labelWidth = g.getFontMetrics().stringWidth(SpotNameManager.getThemeName(entry.getKey()));
                int labelX = x + barWidth / 2 - labelWidth / 2;

                // 바 그리기
                g.setColor(themeColor);
                g.fillRect(x, (getHeight()-barHeight)-30, barWidth, barHeight);

                // 막대 아래에 테마 이름 표시
                g.setColor(Color.BLACK);
                g.drawString(SpotNameManager.getThemeName(entry.getKey()), labelX, getHeight() - 5);

                // 막대 위에 카운트 표시
                g.drawString(String.valueOf(count), x+20, (getHeight()-barHeight)-40);

                x += barWidth + 70;
            }
        }

        private Map<String, Color> createThemeColors() {
            Map<String, Color> colors = new HashMap<>();
            colors.put("TH01", Color.RED);
            colors.put("TH02", Color.GREEN);
            colors.put("TH03", Color.BLUE);
            colors.put("TH04", Color.YELLOW);
            colors.put("TH05", Color.ORANGE);
            colors.put("TH06", Color.PINK);
            return colors;
        }

    }
}
