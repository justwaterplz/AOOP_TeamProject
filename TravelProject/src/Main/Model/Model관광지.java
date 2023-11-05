package Main.Model;

import java.math.BigDecimal;

public record Model관광지(
        int 관광지ID, int 코스ID, String 지역ID, String 관광지명, BigDecimal 경도, BigDecimal 위도,
        int 코스순서, int 이동시간, String 실내구분, String 테마분류
) {
    @Override
    public String toString() {
        return 관광지ID + " " + 코스ID + " " + 지역ID + " " + 관광지명 + " " + 경도 + " " + 위도 + " "
                + 코스순서 + " " + 이동시간 + " " + 실내구분 + " " + 테마분류;
    }
}