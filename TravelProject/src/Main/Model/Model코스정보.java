package Main.Model;

public class Model코스정보 {
    int 관광지ID;
    int 코스ID;
    int 코스순서;
    int 이동시간;

    public Model코스정보(int 관광지ID, int 코스ID, int 코스순서, int 이동시간) {
        this.관광지ID = 관광지ID;
        this.코스ID = 코스ID;
        this.코스순서 = 코스순서;
        this.이동시간 = 이동시간;
    }

    public Model코스정보(int 관광지ID, int 코스ID) {
        this.관광지ID = 관광지ID;
        this.코스ID = 코스ID;
        코스순서 = 0;
        이동시간 = 0;
    }

    public int get관광지ID() {
        return 관광지ID;
    }

    public void set관광지ID(int 관광지ID) {
        this.관광지ID = 관광지ID;
    }

    public int get코스ID() {
        return 코스ID;
    }

    public void set코스ID(int 코스ID) {
        this.코스ID = 코스ID;
    }

    public int get코스순서() {
        return 코스순서;
    }

    public void set코스순서(int 코스순서) {
        this.코스순서 = 코스순서;
    }

    public int get이동시간() {
        return 이동시간;
    }

    public void set이동시간(int 이동시간) {
        this.이동시간 = 이동시간;
    }
}
