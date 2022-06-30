package com.qentelli.automation.testdata;

import com.qentelli.automation.common.World;

import java.util.Locale;
import java.util.ResourceBundle;

public class LangTranslator {
    World world;
    ResourceBundle words;

    public LangTranslator(World world) {
        this.world = world;
        words = ResourceBundle.getBundle("dictionary.words", world.getFormattedLocale());
    }

    public LangTranslator(World world, Locale locale) {
        this.world = world;
        words = ResourceBundle.getBundle("dictionary.words", locale);
    }

    public String translate(String key) {
        try {
            return words.getString(key);
        } catch (Exception e) {
            return key;
        }
    }
}