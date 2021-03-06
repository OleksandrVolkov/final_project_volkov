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

    public static Map<String, String> getHashMapOfRegistrationPage(Language language){
        switch(language){
            case ENGLISH:
                return convertResourceBundleToMap(ResourceBundle.getBundle("reg", new Locale("en", "UK")));
            case RUSSIAN:
                return convertResourceBundleToMap(ResourceBundle.getBundle("reg", new Locale("ru", "RU")));
            case UKRAINIAN:
                return convertResourceBundleToMap(ResourceBundle.getBundle("reg", new Locale("uk", "UA")));
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



    public static Map<String, String> getHashMapOfValuesByPageUrl(String pageUrl, Language lang){
        switch (pageUrl){
            case "main.jsp":
                return getHashMapOfMainPage(lang);
            case "authorization.jsp":
                return getHashMapOfAuthorizationForm(lang);
            case "reg.jsp":
                return getHashMapOfRegistrationPage(lang);
            case "request_actions.jsp":
                return getHashMapOfRequestForm(lang);
        }
        return null;
    }











    public Map<String, String> getRelativeHashMap(String pageUrl, String lang){

//        System.out.println(req.getParameter("lang"));
//        System.out.println(req.getParameter("pageUrl"));
//        System.out.println(req.getParameter("lang") + "???????");
//
//        String pageUrl = req.getParameter("pageUrl");
//        String lang = req.getParameter("lang");

        if(pageUrl == null || pageUrl.equals(""))
            pageUrl = "main.jsp";

        if(lang == null || lang.equals(""))
            lang = "en";


        System.out.println("LANG " + lang);



        Map<String, String> authForm = LanguageHandler.getHashMapOfValuesByPageUrl(pageUrl, Language.getLanguage(lang));
        authForm.put("lang", lang);

        return authForm;
//        System.out.println(authForm+"________");
//        req.setAttribute("language", authForm);
//        req.getRequestDispatcher(pageUrl).forward(req, resp);
    }
}
