package model;

import java.util.Arrays;
import java.util.List;

public enum Language {
    ENGLISH("en"), RUSSIAN("ru"), UKRAINIAN("ua");
    private String shortName;
    Language(String shortName){
        this.shortName = shortName;
    }


    public String getShortName(){
        return shortName;
    }
    public static Language getLanguage(String shortName){
        List<Language> languages = Arrays.asList(Language.values());
        for(Language language : languages)
            if(language.shortName.equals(shortName))
                return language;

        return null;
    }
}
