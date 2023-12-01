package Main.Service;

import Main.Model.Model관광지;
import Main.Model.Model코스정보;
import Main.Repository.MainRepository;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class MainService extends Component {
    private final MainRepository mainRepository;

    public MainService() {
        this. mainRepository = new MainRepository();
    }

    /**
     *   필터를 통해 동적으로 쿼리문, 파라미터를 생성해서 Repository로부터 필터링된 관광지리스트 받아옴
     */
    public ArrayList<Model관광지> getTouristSpotListByFilters(Map<String, Object> filterData) {
        Queue<String> params = new LinkedList<>();

        // 기본 쿼리 설정
        StringBuilder query = setupQuery(filterData);

        // 동적으로 테마 분류 쿼리에 추가 체크 안되있을시 null
        if(!addThemeFilters(query, filterData, params)){
                return null;
        }

        // 동적으로 실내외 쿼리 추가
        addIndoorOutdoorFilter(query, filterData, params);

        // 동적으로 검색어 쿼리에  추가 (검색어는 입력 없어도 보여줘야함)
        addSearchTextFilter(query, filterData, params);
        cleanQuery(query);
        if(params.isEmpty()){
            return null;
        }

        ArrayList<Model관광지> result = mainRepository.find관광지ByFilter(query.toString(), params);
        return result;
    }

    public boolean addFavoriteSpot(int spotId) {
        return mainRepository.addFavoriteSpot(spotId);
    }

    public boolean deleteFavoriteSpot(int spotId) {
        return mainRepository.deleteFavoriteSpot(spotId);
    }

    // 즐겨찾는 관광지 데이터를 받은 후에 hashmap형식으로 카운트해서 뷰에 보내줌
    public Map<String,Integer> getFavoriteThemeCounts() {
        Map<String, Integer> countMap = new LinkedHashMap() {{
            put("TH01", 0); put("TH02", 0); put("TH03", 0); put("TH04", 0); put("TH05", 0); put("TH06", 0);
        }};

        ArrayList<Model관광지> favoriteSpotList = mainRepository.getFavoriteSpotList();    // 즐겨찾는 관광지들을 불러온다

        for (Model관광지 spot : favoriteSpotList) {                        // HashMap을 이용해 개수들을 넣어준다.
            int count = countMap.get(spot.get테마분류())+1;
            countMap.put(spot.get테마분류(),count);
        }

        return countMap;
    }

    public void makeDbtoCsvSpot(){mainRepository.makeDbtoCsvSpot();}

    public void makeDbtoCsvFavoriteSpot(){mainRepository.makeDbtoCsvFavoriteSpot();}

    public void makeDbtoCsvCourse(){mainRepository.makeDbtoCsvCourse();}

    public int importCsvtoDbCourse() throws IOException {
        String csvFilePath = "null";
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select CSV File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            csvFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            return mainRepository.importCsvtoDbCourse(csvFilePath);
        } else if (result == JFileChooser.CANCEL_OPTION){
            return 0;
        } else {
            return -1;
        }


    }



    /**
     *   동적으로 쿼리문, 파라미터를 생성하기위한 함수들
     */

    private static StringBuilder setupQuery(Map<String, Object> filterData) {
        StringBuilder query;

        boolean isFavoriteChecked = Boolean.TRUE.equals(filterData.get("favoriteChecked"));
        if(isFavoriteChecked == true){
            query = new StringBuilder("SELECT 관광지.* FROM 선호관광지 JOIN 관광지 ON 선호관광지.관광지ID = 관광지.관광지ID WHERE");
        } else {
            query = new StringBuilder("SELECT * FROM 관광지 WHERE ");
        }
        return query;
    }

    private boolean addThemeFilters(StringBuilder query, Map<String, Object> filterData, Queue<String> param) {
        boolean anyThemeSelected = false;

        query.append("(");

        for (String theme : getThemeFilters()) {
            if (Boolean.TRUE.equals(filterData.get(theme + "Checked"))) {
                param.add(theme);
                if (anyThemeSelected) {
                    query.append(" OR ");
                }
                query.append("테마분류 = ?");
                anyThemeSelected = true;
            }
        }

        query.append(")");

        if (anyThemeSelected) {
            query.append(" AND ");
            return true;
        } else {
            return false;
        }
    }

    private void addIndoorOutdoorFilter(StringBuilder query, Map<String, Object> filterData,Queue<String> param) {
        boolean isIndoorChecked = Boolean.TRUE.equals(filterData.get("isIndoorChecked"));
        boolean isOutdoorChecked = Boolean.TRUE.equals(filterData.get("isOutdoorChecked"));
        boolean isIndoorOutdoorChecked = Boolean.TRUE.equals(filterData.get("isIndoorOutdoorChecked"));
        query.append("(");
        if (isIndoorChecked) {
            param.add("실내");
            query.append("실내구분 = ?");
        }  else if (isOutdoorChecked) {
            param.add("실외");
            query.append("실내구분 = ?");
        } else if (isIndoorOutdoorChecked) {
            param.add("실외");
            param.add("실내");
            query.append("실내구분 = ? OR 실내구분 = ?");
        }

        query.append(")");
        query.append(" AND ");

    }

    private void addSearchTextFilter(StringBuilder query, Map<String, Object> filterData, Queue<String> param) {
        String searchText = (String) filterData.get("searchText");
        if (searchText != null && !searchText.isEmpty()) {
            query.append("(");
            param.add("%" + searchText.toString() + "%");
            query.append("관광지명 LIKE ?");
            query.append(")");
        }
    }

    // 여기에 테마 분류 목록을 반환
    private List<String> getThemeFilters() {
        List<String> themes = new ArrayList<>();
        themes.add("TH01");
        themes.add("TH02");
        themes.add("TH03");
        themes.add("TH04");
        themes.add("TH05");
        themes.add("TH06");
        return themes;
    }

    // query문을 정리한다.
    private void cleanQuery(StringBuilder query) {
        String queryString = query.toString();

        if (queryString.endsWith(" AND ")) {
            query.setLength(query.length() - 5); // remove the trailing " AND "
        }
        if (queryString.endsWith(" OR ")) {
            query.setLength(query.length() - 4); // remove the trailing " OR "
        }
    }

    public ArrayList<Integer> get코스List(boolean isFavorite)
    {
        return mainRepository.get코스List(isFavorite);
    }

    public ArrayList<Model관광지> get관광지ListIn코스(int courseID) {
        Model코스정보 코스정보 = mainRepository.get코스정보(courseID);

        // 코스정보에 등록된 모든 관광지를 Model관광지 객체로 만들어 리스트로 저장
        ArrayList<Model관광지> 관광지List = new ArrayList<>();
        for (int i = 0; i < 코스정보.get관광지ID목록().size(); ++i) {
            int spotID = 코스정보.get관광지ID목록().get(i);
            관광지List.add(mainRepository.get관광지By관광지ID(spotID));
        }

        return 관광지List;
    }

    public boolean addFavoriteCourse(int courseID) {
        return mainRepository.add선호코스(courseID);
    }

    public boolean deleteFavoriteCourse(int courseID) {
        return mainRepository.delete선호코스(courseID);
    }
    public boolean checkFavoriteCourseExists(int courseID) { return mainRepository.check선호코스Exists(courseID); }
}