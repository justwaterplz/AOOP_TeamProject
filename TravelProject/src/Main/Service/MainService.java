package Main.Service;

import Main.Model.Model관광지;
import Main.Repository.MainRepository;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainService {
    private final MainRepository mainRepository;
    public MainService() {
        this. mainRepository = new MainRepository();
    }

    // 관광지 리스트를 찾는다.
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