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

    @Override
    public int 관광지ID() {
        return 관광지ID;
    }

    @Override
    public int 코스ID() {
        return 코스ID;
    }

    @Override
    public String 지역ID() {
        return 지역ID;
    }

    @Override
    public String 관광지명() {
        return 관광지명;
    }

    @Override
    public BigDecimal 경도() {
        return 경도;
    }

    @Override
    public BigDecimal 위도() {
        return 위도;
    }

    @Override
    public int 코스순서() {
        return 코스순서;
    }

    @Override
    public int 이동시간() {
        return 이동시간;
    }

    @Override
    public String 실내구분() {
        return 실내구분;
    }

    @Override
    public String 테마분류() {
        return 테마분류;
    }
}