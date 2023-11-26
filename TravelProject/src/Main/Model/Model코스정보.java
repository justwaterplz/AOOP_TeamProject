package Main.Model;

import java.util.ArrayList;

public class Model코스정보 {
    int 코스ID;
    ArrayList<Integer> 관광지ID목록;
    ArrayList<Integer> 코스순서목록;
    ArrayList<Integer> 이동시간목록;

    public Model코스정보(int 코스ID, ArrayList<Integer> 관광지ID목록, ArrayList<Integer> 코스순서목록, ArrayList<Integer> 이동시간목록) {
        this.코스ID = 코스ID;
        this.관광지ID목록 = 관광지ID목록;
        this.코스순서목록 = 코스순서목록;
        this.이동시간목록 = 이동시간목록;
    }

    public int get코스ID() {
        return 코스ID;
    }

    public void set코스ID(int 코스ID) {
        this.코스ID = 코스ID;
    }

    public ArrayList<Integer> get관광지ID목록() {
        return 관광지ID목록;
    }

    public void set관광지ID목록(ArrayList<Integer> 관광지ID목록) {
        this.관광지ID목록 = 관광지ID목록;
    }

    public ArrayList<Integer> get코스순서목록() {
        return 코스순서목록;
    }

    public void set코스순서목록(ArrayList<Integer> 코스순서목록) {
        this.코스순서목록 = 코스순서목록;
    }

    public ArrayList<Integer> get이동시간목록() {
        return 이동시간목록;
    }

    public void set이동시간목록(ArrayList<Integer> 이동시간목록) {
        this.이동시간목록 = 이동시간목록;
    }
}
