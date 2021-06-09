package ru.senina.itmo.lab8;

import java.util.Locale;

public enum Language {
    RUSSIAN("ru", "RU", "russian"),
    ALBANIAN("sq", "AL", "albanian"),
    SPANISH("es", "MX", "spanish"),
    ENGLISH("en", "US", "english"),
    SERBIAN("sr", "SR", "serbian"),
    TROLL("trol", "TROL", "troll");

    private final Locale locale;
    private final String languageStr;

    Language(String language, String country, String languageStr) {
        locale = new Locale(language, country);
        this.languageStr = languageStr;
    }

    public Locale getLocale() {
        return this.locale;
    }

    @Override
    public String toString() {
        return ClientMain.getRB().getString(languageStr + "Language");
    }
}
