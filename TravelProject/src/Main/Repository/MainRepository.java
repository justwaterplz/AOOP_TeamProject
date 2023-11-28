package Main.Repository;
import Main.Model.*;
import Main.Module.ConnectionPool;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Queue;

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
                int i = 1;
                int 관광지ID = resultSet.getInt(i++);
                String 지역ID = resultSet.getString(i++);
                String 관광지명 = resultSet.getString(i++);
                BigDecimal 경도 = resultSet.getBigDecimal(i++);
                BigDecimal 위도 = resultSet.getBigDecimal(i++);
                String 실내구분 = resultSet.getString(i++);
                String 테마분류 = resultSet.getString(i++);
                Model관광지 관광지 = new Model관광지(관광지ID, 지역ID, 관광지명, 경도, 위도, 실내구분, 테마분류);
                관광지List.add(관광지);
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

    public ArrayList<Model관광지> find관광지ByFilter(String query, Queue<String> params) {
        Connection conn = pool.getConnection();
        if(conn == null) {
            System.out.println("Failed Get Connection");
            return null;
        }

        // SQL 쿼리 실행
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(query);
            int paramSize = params.size();
            for (int i = 0; i < paramSize; i++) {
                statement.setString(i + 1, params.poll());
            }
            ResultSet resultSet = statement.executeQuery();

            // 결과 출력 및 저장
            ArrayList<Model관광지> 관광지List = new ArrayList<>();
            while (resultSet.next()) {
                // 각 열의 값을 가져오기 (여기서는 예를 들어 1번째와 2번째 열의 값을 가져옵니다)
                int i = 1;
                int 관광지ID = resultSet.getInt(i++);
                String 지역ID = resultSet.getString(i++);
                String 관광지명 = resultSet.getString(i++);
                BigDecimal 경도 = resultSet.getBigDecimal(i++);
                BigDecimal 위도 = resultSet.getBigDecimal(i++);
                String 실내구분 = resultSet.getString(i++);
                String 테마분류 = resultSet.getString(i++);
                Model관광지 관광지 = new Model관광지(관광지ID, 지역ID, 관광지명, 경도, 위도, 실내구분, 테마분류);
                관광지List.add(관광지);
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

    public Model관광지 get관광지By관광지ID(int _관광지ID) {
        Connection conn = pool.getConnection();
        if(conn == null) {
            System.out.println("Failed Get Connection");
            return null;
        }

        String query = "SELECT * FROM 관광지 WHERE 관광지ID = ?";

        // SQL 쿼리 실행
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, _관광지ID);
            ResultSet resultSet = statement.executeQuery();

            // 결과 출력 및 저장
            Model관광지 관광지 = null;
            while (resultSet.next()) {
                int i = 1;
                int 관광지ID = resultSet.getInt(i++);
                String 지역ID = resultSet.getString(i++);
                String 관광지명 = resultSet.getString(i++);
                BigDecimal 경도 = resultSet.getBigDecimal(i++);
                BigDecimal 위도 = resultSet.getBigDecimal(i++);
                String 실내구분 = resultSet.getString(i++);
                String 테마분류 = resultSet.getString(i++);
                관광지 = new Model관광지(관광지ID, 지역ID, 관광지명, 경도, 위도, 실내구분, 테마분류);
            }

            // 자원 해제
            resultSet.close();
            statement.close();

            return 관광지;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public ArrayList<Integer> get코스List(boolean isFavorite) {
        Connection conn = pool.getConnection();
        if(conn == null) {
            System.out.println("Failed Get Connection");
            return null;
        }

        String query = (isFavorite) ? "SELECT * FROM 선호코스" : "SELECT * FROM 코스";

        // SQL 쿼리 실행
        Statement statement = null;
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // 결과 출력 및 저장
            ArrayList<Integer> 코스IDList = new ArrayList<>();
            while (resultSet.next()) {
                // 각 열의 값을 가져오기 (여기서는 예를 들어 1번째와 2번째 열의 값을 가져옵니다)
                int i = 1;
                int 코스ID = resultSet.getInt(i++);
                코스IDList.add(코스ID);
            }

            // 자원 해제
            resultSet.close();
            statement.close();

            return 코스IDList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public ArrayList<Integer> get선호코스IDList() {
        Connection conn = pool.getConnection();
        if(conn == null) {
            System.out.println("Failed Get Connection");
            return null;
        }

        // SQL 쿼리 실행
        Statement statement = null;
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM 선호코스");

            // 결과 출력 및 저장
            ArrayList<Integer> 코스IDList = new ArrayList<>();
            while (resultSet.next()) {
                // 각 열의 값을 가져오기 (여기서는 예를 들어 1번째와 2번째 열의 값을 가져옵니다)
                int i = 1;
                int 코스ID = resultSet.getInt(i++);
                코스IDList.add(코스ID);
            }

            // 자원 해제
            resultSet.close();
            statement.close();

            return 코스IDList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            pool.releaseConnection(conn);
        }
    }

    /**
     *   되는지 모름
     *   코스별로 관광지 목록 띄우는건 Service쪽에서 코스정보 모델의 ArrayList 사용해서 할 것
     */
    public Model코스정보 get코스정보(int 대상코스ID) {
        Connection conn = pool.getConnection();
        if(conn == null) {
            System.out.println("Failed Get Connection");
            return null;
        }

        // SQL 쿼리 실행
        String query = "SELECT * FROM 코스정보 WHERE 코스ID = ?";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, 대상코스ID);
            ResultSet resultSet = statement.executeQuery();

            Model코스정보 코스정보 = new Model코스정보(대상코스ID, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            // 결과 출력 및 저장
            while (resultSet.next()) {
                // 각 열의 값을 가져오기 (여기서는 예를 들어 1번째와 2번째 열의 값을 가져옵니다)
                int i = 1;
                int 관광지ID = resultSet.getInt(i++);
                int 코스ID = resultSet.getInt(i++);
                int 코스순서 = resultSet.getInt(i++);
                int 이동시간 = resultSet.getInt(i++);
                코스정보.get관광지ID목록().add(관광지ID);
                코스정보.get코스순서목록().add(코스순서);
                코스정보.get이동시간목록().add(이동시간);
            }

            // 자원 해제
            resultSet.close();
            statement.close();

            return 코스정보;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public boolean check선호코스Exists(int 코스ID) {
        Connection conn = pool.getConnection();
        if (conn == null) {
            System.out.println("Failed Get Connection");
            return false;
        }

        // SQL 쿼리 실행
        String query = "SELECT * FROM 선호코스 WHERE 코스ID = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, 코스ID);

            resultSet = statement.executeQuery();
            boolean isExists = resultSet.next();

            // 자원 해제
            resultSet.close();
            statement.close();

            // 이미 존재
            if (isExists) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public boolean add선호코스(int 코스ID) {
        Connection conn = pool.getConnection();
        if(conn == null) {
            System.out.println("Failed Get Connection");
            return false;
        }

        // SQL 쿼리 실행
        String query = "INSERT INTO 선호코스 (코스ID) VALUES (?)";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, 코스ID);

            statement.executeUpdate();

            // 자원 해제
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            pool.releaseConnection(conn);
        }
    }

}