package Main.Repository;
import Main.Model.*;
import Main.Module.ConnectionPool;

import javax.swing.*;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class MainRepository {
    private final ConnectionPool pool;

    public MainRepository() {
        pool = ConnectionPool.getInstance();
    }

    public ArrayList<Model관광지> get관광지List() {
        Connection conn = pool.getConnection();
        if (conn == null) {
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

    public ArrayList<Model관광지> getFavoriteSpotList() {
        Connection conn = pool.getConnection();
        if (conn == null) {
            System.out.println("Failed Get Connection");
            return null;
        }

        // SQL 쿼리 실행
        Statement statement = null;
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT 관광지.* FROM 선호관광지 JOIN 관광지 ON 선호관광지.관광지ID = 관광지.관광지ID");

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
        if (conn == null) {
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
        if (conn == null) {
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
        if (conn == null) {
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
        if (conn == null) {
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
     * 되는지 모름
     * 코스별로 관광지 목록 띄우는건 Service쪽에서 코스정보 모델의 ArrayList 사용해서 할 것
     */
    public Model코스정보 get코스정보(int 대상코스ID) {
        Connection conn = pool.getConnection();
        if (conn == null) {
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

    public boolean getFavoriteStatusBy코스ID(int 코스ID) {
        Connection conn = pool.getConnection();
        if (conn == null) {
            System.out.println("Failed Get Connection");
            return false;
        }

        // SQL 쿼리 실행
        String query = "SELECT COUNT(*) FROM 선호코스 WHERE 코스ID = ?";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, 코스ID);

            ResultSet resultSet = statement.executeQuery();

            // 결과 출력 및 저장
            int count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }

            // 자원 해제
            resultSet.close();
            statement.close();

            return count == 0 ? false : true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public boolean getFavoriteStatusBy관광지ID(int spotId) {
        Connection conn = pool.getConnection();
        if (conn == null) {
            System.out.println("Failed Get Connection");
            return false;
        }

        // SQL 쿼리 실행
        String query = "SELECT COUNT(*) FROM 선호관광지 WHERE 관광지ID = ?";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, spotId);

            ResultSet resultSet = statement.executeQuery();

            // 결과 출력 및 저장
            int count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }

            // 자원 해제
            resultSet.close();
            statement.close();

            return count == 0 ? false : true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public boolean addFavoriteSpot(int spotId) {
        Connection conn = pool.getConnection();
        if (conn == null) {
            System.out.println("Failed Get Connection");
            return false;
        }

        // SQL 쿼리 실행
        String query = "INSERT INTO 선호관광지 (관광지ID) VALUES (?)";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, spotId);

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

    public boolean deleteFavoriteSpot(int spotId) {
        Connection conn = pool.getConnection();
        if (conn == null) {
            System.out.println("Failed Get Connection");
            return false;
        }

        // SQL 쿼리 실행
        String query = "Delete From 선호관광지 WHERE 관광지ID = ?";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, spotId);

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

    public void makeDbtoCsvSpot() {
        Connection conn = pool.getConnection();
        if(conn == null) {
            System.out.println("Failed Get Connection");
        }

        // SQL 쿼리 실행
        String desktopPath = System.getProperty("user.home") + "\\Desktop\\Spotoutput.csv";

        String query = "SELECT * FROM 관광지";
        Statement statement = null;
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            // CSV 파일 생성
            try (FileWriter csvWriter = new FileWriter(desktopPath)) {
                // CSV 파일 헤더 작성
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    csvWriter.append(metaData.getColumnName(i));
                    if (i < columnCount) {
                        csvWriter.append(",");
                    }
                }
                csvWriter.append("\n");

                // 결과 집합을 CSV 파일에 쓰기
                while (resultSet.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        csvWriter.append(resultSet.getString(i));
                        if (i < columnCount) {
                            csvWriter.append(",");
                        }
                    }
                    csvWriter.append("\n");
                }
            }
            statement.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public void makeDbtoCsvFavoriteSpot() {
        Connection conn = pool.getConnection();
        if(conn == null) {
            System.out.println("Failed Get Connection");
        }

        // SQL 쿼리 실행
        String desktopPath = System.getProperty("user.home") + "\\Desktop\\FavoriteSpotoutput.csv";

        String query = "SELECT * FROM 관광지";
        Statement statement = null;
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            // CSV 파일 생성
            try (FileWriter csvWriter = new FileWriter(desktopPath)) {
                // CSV 파일 헤더 작성
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    csvWriter.append(metaData.getColumnName(i));
                    if (i < columnCount) {
                        csvWriter.append(",");
                    }
                }
                csvWriter.append("\n");

                // 결과 집합을 CSV 파일에 쓰기
                while (resultSet.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        csvWriter.append(resultSet.getString(i));
                        if (i < columnCount) {
                            csvWriter.append(",");
                        }
                    }
                    csvWriter.append("\n");
                }
            }
            statement.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public void makeDbtoCsvCourse() {
        Connection conn = pool.getConnection();
        if(conn == null) {
            System.out.println("Failed Get Connection");
        }

        // SQL 쿼리 실행
        String desktopPath = System.getProperty("user.home") + "\\Desktop\\Courseoutput.csv";

        String query = "SELECT * FROM 코스정보";
        Statement statement = null;
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            // CSV 파일 생성
            try (FileWriter csvWriter = new FileWriter(desktopPath)) {
                // CSV 파일 헤더 작성
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    csvWriter.append(metaData.getColumnName(i));
                    if (i < columnCount) {
                        csvWriter.append(",");
                    }
                }
                csvWriter.append("\n");

                // 결과 집합을 CSV 파일에 쓰기
                while (resultSet.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        csvWriter.append(resultSet.getString(i));
                        if (i < columnCount) {
                            csvWriter.append(",");
                        }
                    }
                    csvWriter.append("\n");
                }
            }
            statement.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public int importCsvtoDbCourse (String csvFilePath) throws IOException {
        Connection conn = pool.getConnection();
        if(conn == null) {
            System.out.println("Failed Get Connection");
            return -1;
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFilePath), Charset.forName("UTF-8")));

            // 첫번째 행은 무시
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                // CSV에서 데이터 추출
                String[] data = line.split(",");

                // 데이터베이스에 데이터 삽입
                String sql = "INSERT INTO 관광지 (관광지ID, 지역ID, 관광지명, 경도, 위도, 실내구분, 테마분류) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    for (int i = 0; i < data.length; i++) {
                        if (i == 0) {
                            preparedStatement.setInt(i + 1, Integer.parseInt(data[i]));
                        } else if (i == 1 || i == 2 || i == 5 || i == 6) {
                            preparedStatement.setString(i + 1, data[i]);
                        } else if (i == 3 || i == 4) {
                            preparedStatement.setDouble(i + 1, Double.parseDouble(data[i]));
                        }
                    }
                    preparedStatement.executeUpdate();
                }
            }
            return 1;
        } catch (IOException | SQLException ex ){
            System.out.println("ex = " + ex);
            JOptionPane.showMessageDialog(null, ex, "알림", JOptionPane.INFORMATION_MESSAGE);
            return -1;
        } finally {
            br.close();
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
        if (conn == null) {
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

    public boolean delete선호코스(int 코스ID) {
        Connection conn = pool.getConnection();
        if (conn == null) {
            System.out.println("Failed Get Connection");
            return false;
        }

        // SQL 쿼리 실행
        String query = "Delete From 선호코스 WHERE 코스ID = ?";
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