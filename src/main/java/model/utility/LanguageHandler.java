package model.utility;

import model.Language;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class LanguageHandler {
    private Properties properties;

    public LanguageHandler(){
        properties = new Properties();
    }

//    public Map<String, String> getMainPageEn(){
//        return getMainPage("mainPage.properties");
//    }
//
//    public Map<String, String> getMainPageRus(){
//        return getMainPage("mainPage_ru_RU.properties");
//    }
//
//    public Map<String, String> getMainPageUa(){
//        return getMainPage("mainPage_uk_UA.properties");
//    }
//
//    private Map<String, String> getMainPage(String path){
//        Map<String, String> hashMap = null;
//        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//        String appConfigPath = rootPath + path;
//        try {
//            properties.load(new FileInputStream(appConfigPath));
//            hashMap = properties.entrySet().stream().collect(
//                    Collectors.toMap(
//                            e -> e.getKey().toString(),
//                            e -> e.getValue().toString()
//                    )
//            );
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return hashMap;
//    }

    public static Map<String, String> getHashMapOfMainPage(Language language){
        switch(language){
            case ENGLISH:
                return convertResourceBundleToMap(ResourceBundle.getBundle("mainPage", new Locale("en", "UK")));
            case RUSSIAN:
                return convertResourceBundleToMap(ResourceBundle.getBundle("mainPage", new Locale("ru", "RU")));
            case UKRAINIAN:
                return convertResourceBundleToMap(ResourceBundle.getBundle("mainPage", new Locale("uk", "UA")));
        }
        return null;
    }

    public static Map<String, String> getHashMapOfRequestForm(Language language){
        switch(language){
            case ENGLISH:
                return convertResourceBundleToMap(ResourceBundle.getBundle("requestForm", new Locale("en", "UK")));
            case RUSSIAN:
                return convertResourceBundleToMap(ResourceBundle.getBundle("requestForm", new Locale("ru", "RU")));
            case UKRAINIAN:
                return convertResourceBundleToMap(ResourceBundle.getBundle("requestForm", new Locale("uk", "UA")));
        }
        return null;
    }
    public static Map<String, String> getHashMapOfAuthorizationForm(Language language){
        switch(language){
            case ENGLISH:
                return convertResourceBundleToMap(ResourceBundle.getBundle("authorization", new Locale("en", "UK")));
            case RUSSIAN:
                return convertResourceBundleToMap(ResourceBundle.getBundle("authorization", new Locale("ru", "RU")));
            case UKRAINIAN:
                return convertResourceBundleToMap(ResourceBundle.getBundle("authorization", new Locale("uk", "UA")));
        }
        return null;
    }

    private static Map<String, String> convertResourceBundleToMap(ResourceBundle resource) {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> keys = resource.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            map.put(key, resource.getString(key));
        }
        return map;
    }


}
