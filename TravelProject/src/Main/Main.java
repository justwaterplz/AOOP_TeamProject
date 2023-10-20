package Main;

import Main.Page.SwingController;
import Main.Repository.MainRepository;

public class Main {
    public static void main(String[] args) {
        try {
            // JDBC 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        new SwingController();
    }
}