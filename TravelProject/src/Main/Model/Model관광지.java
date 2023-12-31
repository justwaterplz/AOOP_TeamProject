package Main.Model;

import java.math.BigDecimal;

public class Model관광지{
    int 관광지ID;
    String 지역ID;
    String 관광지명;
    BigDecimal 경도;
    BigDecimal 위도;
    String 실내구분;
    String 테마분류;

    public Model관광지(int 관광지ID, String 지역ID, String 관광지명, BigDecimal 경도, BigDecimal 위도, String 실내구분, String 테마분류) {
        this.관광지ID = 관광지ID;
        this.지역ID = 지역ID;
        this.관광지명 = 관광지명;
        this.경도 = 경도;
        this.위도 = 위도;
        this.실내구분 = 실내구분;
        this.테마분류 = 테마분류;
    }

    @Override
    public String toString() {
        return 관광지ID + " " + 지역ID + " " + 관광지명 + " " + 경도 + " " + 위도 + " "
                + 실내구분 + " " + 테마분류;
    }

    public int get관광지ID() {
        return 관광지ID;
    }

    public void set관광지ID(int 관광지ID) {
        this.관광지ID = 관광지ID;
    }

    public String get지역ID() {
        return 지역ID;
    }

    public void set지역ID(String 지역ID) {
        this.지역ID = 지역ID;
    }

    public String get관광지명() {
        return 관광지명;
    }

    public void set관광지명(String 관광지명) {
        this.관광지명 = 관광지명;
    }

    public BigDecimal get경도() {
        return 경도;
    }

    public void set경도(BigDecimal 경도) {
        this.경도 = 경도;
    }

    public BigDecimal get위도() {
        return 위도;
    }

    public void set위도(BigDecimal 위도) {
        this.위도 = 위도;
    }

    public String get실내구분() {
        return 실내구분;
    }

    public void set실내구분(String 실내구분) {
        this.실내구분 = 실내구분;
    }

    public String get테마분류() {
        return 테마분류;
    }

    public void set테마분류(String 테마분류) {
        this.테마분류 = 테마분류;
    }
}