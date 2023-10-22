package Main.Repository;
import Main.ConnectionPool;
import Main.Model.Model관광지;
import Main.Model.Model테마;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class MainRepository {
    private final ConnectionPool pool;

    public MainRepository() {
        pool = ConnectionPool.getInstance();
    }

    public ArrayList<Model관광지> get관광지List() {
        Connection conn = pool.getConnection();
        if(conn == null) {
            System.out.println("Failed Get Connection");
            return null;
        }

        // SQL 쿼리 실행
        Statement statement = null;
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM 관광지");

            // 결과 출력 및 저장
            ArrayList<Model관광지> 관광지List = new ArrayList<>();
            while (resultSet.next()) {
                // 각 열의 값을 가져오기 (여기서는 예를 들어 1번째와 2번째 열의 값을 가져옵니다)
                int 관광지ID = resultSet.getInt(1);
                int 코스ID = resultSet.getInt(2);
                String 지역ID = resultSet.getString(3);
                String 관광지명 = resultSet.getString(4);
                BigDecimal 경도 = resultSet.getBigDecimal(5);
                BigDecimal 위도 = resultSet.getBigDecimal(6);
                int 코스순서 = resultSet.getInt(7);
                int 이동시간 = resultSet.getInt(8);
                String 실내구분 = resultSet.getString(9);
                String 테마분류 = resultSet.getString(10);
                Model관광지 관광지 = new Model관광지(관광지ID, 코스ID, 지역ID, 관광지명, 경도, 위도, 코스순서, 이동시간, 실내구분, 테마분류);
                관광지List.add(관광지);
                System.out.println(관광지);
            }

            // 자원 해제
            resultSet.close();
            statement.close();

            return 관광지List;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            pool.releaseConnection(conn);
        }
    }
    public ArrayList<Model테마> get테마List() {
        Connection conn = pool.getConnection();
        if(conn == null) {
            System.out.println("Failed Get Connection");
            return null;
        }

        // SQL 쿼리 실행
        Statement statement = null;
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM 테마");

            // 결과 출력 및 저장
            ArrayList<Model테마> 테마List = new ArrayList<>();
            while (resultSet.next()) {
                // 각 열의 값을 가져오기 (여기서는 예를 들어 1번째와 2번째 열의 값을 가져옵니다)
                String 테마분류 = resultSet.getString(1);
                String 테마명 = resultSet.getString(2);
                Model테마 테마 = new Model테마(테마분류, 테마명);
                테마List.add(테마);
                System.out.println(테마분류 + " " + 테마명);
            }

            // 자원 해제
            resultSet.close();
            statement.close();

            return 테마List;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public ArrayList<Model관광지> get관광지ListByTheme(String theme) {
        Connection conn = pool.getConnection();
        if(conn == null) {
            System.out.println("Failed Get Connection");
            return null;
        }

        // SQL 쿼리 실행
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement("SELECT * FROM 관광지 WHERE 테마분류 = ?");
            statement.setString(1, theme); // 첫 번째 파라미터에 theme을 설정합니다.
            ResultSet resultSet = statement.executeQuery();

            // 결과 출력 및 저장
            ArrayList<Model관광지> 관광지List = new ArrayList<>();
            while (resultSet.next()) {
                // 각 열의 값을 가져오기 (여기서는 예를 들어 1번째와 2번째 열의 값을 가져옵니다)
                int 관광지ID = resultSet.getInt(1);
                int 코스ID = resultSet.getInt(2);
                String 지역ID = resultSet.getString(3);
                String 관광지명 = resultSet.getString(4);
                BigDecimal 경도 = resultSet.getBigDecimal(5);
                BigDecimal 위도 = resultSet.getBigDecimal(6);
                int 코스순서 = resultSet.getInt(7);
                int 이동시간 = resultSet.getInt(8);
                String 실내구분 = resultSet.getString(9);
                String 테마분류 = resultSet.getString(10);
                Model관광지 관광지 = new Model관광지(관광지ID, 코스ID, 지역ID, 관광지명, 경도, 위도, 코스순서, 이동시간, 실내구분, 테마분류);
                관광지List.add(관광지);
                System.out.println(관광지);
            }

            // 자원 해제
            resultSet.close();
            statement.close();

            return 관광지List;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public ArrayList<Model관광지> get관광지ListBySearch(String data) {
        Connection conn = pool.getConnection();
        if(conn == null) {
            System.out.println("Failed Get Connection");
            return null;
        }

        // SQL 쿼리 실행
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement( "SELECT * FROM 관광지 WHERE 관광지명 LIKE ?");
            statement.setString(1, "%" + data + "%");
            ResultSet resultSet = statement.executeQuery();

            // 결과 출력 및 저장
            ArrayList<Model관광지> 관광지List = new ArrayList<>();
            while (resultSet.next()) {
                // 각 열의 값을 가져오기 (여기서는 예를 들어 1번째와 2번째 열의 값을 가져옵니다)
                int 관광지ID = resultSet.getInt(1);
                int 코스ID = resultSet.getInt(2);
                String 지역ID = resultSet.getString(3);
                String 관광지명 = resultSet.getString(4);
                BigDecimal 경도 = resultSet.getBigDecimal(5);
                BigDecimal 위도 = resultSet.getBigDecimal(6);
                int 코스순서 = resultSet.getInt(7);
                int 이동시간 = resultSet.getInt(8);
                String 실내구분 = resultSet.getString(9);
                String 테마분류 = resultSet.getString(10);
                Model관광지 관광지 = new Model관광지(관광지ID, 코스ID, 지역ID, 관광지명, 경도, 위도, 코스순서, 이동시간, 실내구분, 테마분류);
                관광지List.add(관광지);
                System.out.println(관광지);
            }

            // 자원 해제
            resultSet.close();
            statement.close();

            return 관광지List;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public ArrayList<Model관광지> get관광지ListByIndoorOutdoor(String theme) {
        Connection conn = pool.getConnection();
        if(conn == null) {
            System.out.println("Failed Get Connection");
            return null;
        }

        // SQL 쿼리 실행
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement("SELECT * FROM 관광지 WHERE 실내구분 = ?");
            statement.setString(1, theme); // 첫 번째 파라미터에 theme을 설정합니다.
            ResultSet resultSet = statement.executeQuery();

            // 결과 출력 및 저장
            ArrayList<Model관광지> 관광지List = new ArrayList<>();
            while (resultSet.next()) {
                // 각 열의 값을 가져오기 (여기서는 예를 들어 1번째와 2번째 열의 값을 가져옵니다)
                int 관광지ID = resultSet.getInt(1);
                int 코스ID = resultSet.getInt(2);
                String 지역ID = resultSet.getString(3);
                String 관광지명 = resultSet.getString(4);
                BigDecimal 경도 = resultSet.getBigDecimal(5);
                BigDecimal 위도 = resultSet.getBigDecimal(6);
                int 코스순서 = resultSet.getInt(7);
                int 이동시간 = resultSet.getInt(8);
                String 실내구분 = resultSet.getString(9);
                String 테마분류 = resultSet.getString(10);
                Model관광지 관광지 = new Model관광지(관광지ID, 코스ID, 지역ID, 관광지명, 경도, 위도, 코스순서, 이동시간, 실내구분, 테마분류);
                관광지List.add(관광지);
                System.out.println(관광지);
            }

            // 자원 해제
            resultSet.close();
            statement.close();

            return 관광지List;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            pool.releaseConnection(conn);
        }
    }

}
