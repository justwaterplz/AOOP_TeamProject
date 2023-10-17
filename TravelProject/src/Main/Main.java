package Main;

import Main.Repository.MainRepository;

public class Main {
    public static void main(String[] args) {
        try {
            // JDBC 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 테스트, 실제로는 응용단에서 Service 객체를 사용
            MainRepository mainRepository = new MainRepository();
            mainRepository.get관광지List();
            mainRepository.get테마List();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        new MainView();
    }
}