package Main.Module;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 관광지명을 그대로 불러오면 (서울) 63빌딩과 같이 모호하게 출력되서
 * 이름이나 지역만 빼오는 도구
 * 테마도 가능
 */

public class SpotNameManager {

    public static final String TH01 = "문화/예술";
    public static final String TH02 = "자연/힐링";
    public static final String TH03 = "캠핑/스포츠";
    public static final String TH04 = "종교/역사/전통";
    public static final String TH05 = "쇼핑/놀이";
    public static final String TH06 = "체험/학습/산업로";

    // 정규 표현식을 사용하여 괄호와 괄호 안의 내용을 제거
    public static String getSpotName(String input) {
        return input.replaceAll("\\([^\\)]*\\)", "").trim();
    }

    // 정규 표현식을 사용하여 괄호와 괄호 안의 내용을 추출
    public static String getSpotLocate(String input) {
        Matcher matcher = Pattern.compile("\\(([^)]+)\\)").matcher(input);
        return matcher.find() ? matcher.group(1).trim() : "";
    }

    public static String getThemeName(String categoryCode) {
        switch (categoryCode) {
            case "TH01":
                return TH01;
            case "TH02":
                return TH02;
            case "TH03":
                return TH03;
            case "TH04":
                return TH04;
            case "TH05":
                return TH05;
            case "TH06":
                return TH06;
            default:
                return "여행지";
        }
    }
}
