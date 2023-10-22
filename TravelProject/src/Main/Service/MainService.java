package Main.Service;

import Main.Model.Model관광지;
import Main.Repository.MainRepository;

import java.util.ArrayList;

public class MainService {
    private final MainRepository mainRepository;

    public MainService() {
        this. mainRepository = new MainRepository();
    }

    public ArrayList<Model관광지> findTouristSpots(String type, String data) {
        ArrayList<Model관광지> model관광지 = new ArrayList<>();
        if(type.equals("검색")){
            model관광지 = mainRepository.get관광지ListBySearch(data);
        } else if (type.equals("테마")) {
            model관광지 = mainRepository.get관광지ListByTheme(data);
        } else if (type.equals("실내외")) {
            model관광지 = mainRepository.get관광지ListByIndoorOutdoor(data);
        }
        return model관광지;
    }

}