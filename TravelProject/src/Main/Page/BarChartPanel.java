package Main.Page;

import Main.Model.Model관광지;
import Main.Service.MainService;
import Main.Service.WeatherService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// 막대 그래프
public class BarChartPanel extends JPanel {

    private ArrayList<Double> tempValues = new ArrayList<>();
    private ArrayList<Double> feelsLikeValues = new ArrayList<>();
    private ArrayList<Integer> humidityValues = new ArrayList<>();
    private ArrayList<String> descriptionValues = new ArrayList<>();
    private ArrayList<Double> popValues = new ArrayList<>();
    private ArrayList<String> dtTxtValues = new ArrayList<>();
    private ArrayList<String> iconValues = new ArrayList<>();
    private JsonArray weatherArray;
    private final WeatherService weatherService;

    // WeatherService로부터 기상정보 받아서 초기화
    public BarChartPanel(int day, Model관광지 spot) {
        weatherService = new WeatherService();
        setBackground(Color.WHITE);
        weatherArray = weatherService.getWeatherJsonArray(spot.get경도().doubleValue(), spot.get위도().doubleValue(), day);
        for (JsonElement element : weatherArray) {
            JsonObject weatherObject = element.getAsJsonObject();
            JsonObject mainObject = weatherObject.getAsJsonObject("main");
            JsonArray weatherArrayInside = weatherObject.getAsJsonArray("weather");

            // Extract values
            Double temp = mainObject.getAsJsonPrimitive("temp").getAsDouble();
            Double feelsLike = mainObject.getAsJsonPrimitive("feels_like").getAsDouble();
            int humidity = mainObject.getAsJsonPrimitive("humidity").getAsInt();
            String description = weatherArrayInside.get(0).getAsJsonObject().getAsJsonPrimitive("description").getAsString();
            double pop = weatherObject.getAsJsonPrimitive("pop").getAsDouble();
            String dtTxt = weatherObject.getAsJsonPrimitive("dt_txt").getAsString();
            String icon = weatherArrayInside.get(0).getAsJsonObject().getAsJsonPrimitive("icon").getAsString();

            // Add values to lists
            tempValues.add(temp);
            feelsLikeValues.add(feelsLike);
            humidityValues.add(humidity);
            descriptionValues.add(description);
            popValues.add(pop);
            dtTxtValues.add(dtTxt);
            iconValues.add(icon);
        }
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(weatherArray.size() * 150, getHeight());
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 바 너비x
        int barWidth = 50;
        // 바 높이를 최대값을 기준으로 하기위해 구하는 최대값
        double center = getHeight() / 4;

        // 바 간격
        int x = 50;
        for (int i = 0; i < weatherArray.size(); i++) {
            double tempValue = tempValues.get(i);
            double feelsLikeValue = feelsLikeValues.get(i);
            int humidityValue = humidityValues.get(i);
            String descriptionValue = descriptionValues.get(i);
            Double popValue = popValues.get(i);
            String dtTxtValue = dtTxtValues.get(i);
            String iconValue = iconValues.get(i);

            int barHeight = (int) (center + tempValue * 10);

            // 막대 그리기
            g.setColor(new Color(173, 216, 230));
            g.fillRect(x, (getHeight() - barHeight) - 100, barWidth, barHeight);

            // 막대 위에 아이콘, 날씨 ,기온
            Image image = Toolkit.getDefaultToolkit().getImage("TravelProject/src/Main/Image/" + iconValue + ".png");
            g.drawImage(image, x, 10, 60, 60, this);


            // 폰트 설정
            Font coreFont = new Font("맑은 고딕", Font.BOLD, 14);
            g.setFont(coreFont);

            int textWidth = g.getFontMetrics().stringWidth(descriptionValue);  // 텍스트의 가로 길이
            int textX = x + (barWidth - textWidth) / 2;  // 텍스트의 시작 위치 조정

            //날씨
            g.setColor(Color.BLACK);
            g.drawString(descriptionValue, textX, 80);

            //기온
            g.drawString(String.valueOf(tempValue) + "°C", x + 5, (getHeight() - barHeight) - 110);

            // 막대 아래에 강수확률, 체감온도, 습도, 날짜
            Font otherInfoFont = new Font("맑은 고딕", Font.PLAIN, 12);
            g.setFont(otherInfoFont);
            g.setColor(Color.BLACK);
            g.drawString("강수확률 " + (int) (popValue * 100) + "%", x - 5, getHeight() - 50);

            g.setFont(otherInfoFont);
            g.setColor(Color.BLACK);
            g.drawString("체감 " + feelsLikeValue, x - 5, getHeight() - 35);


            g.setColor(Color.BLACK);
            g.drawString("습도 " + humidityValue + "%", x - 5, getHeight() - 20);

            // 막대 아래에 날짜시간 표시
            g.setColor(Color.BLACK);
            g.drawString(dtTxtValue, x - 25, getHeight() - 5);

            x += barWidth + 100;
        }
    }
}