package Main.Service;

import Main.Model.Model관광지;
import Main.Repository.MainRepository;

import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.util.*;

public class MainService {
    private final MainRepository mainRepository;

    public MainService() {
        this. mainRepository = new MainRepository();
    }

    /**
     *   필터를 통해 동적으로 쿼리문, 파라미터를 생성해서 Repository로부터 필터링된 관광지리스트 받아옴
     *   preparedSQL이므로 SQLInjection으로부터 안전
     */
    public ArrayList<Model관광지> getTouristSpotListByFilters(Map<String, Object> filterData) {
        Queue<String> params = new LinkedList<>();

        // 기본 쿼리
        StringBuilder query = new StringBuilder("SELECT * FROM 관광지 WHERE ");

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

    public ArrayList<Integer> get코스List()
    {
        return mainRepository.get코스List(false);
    }
}