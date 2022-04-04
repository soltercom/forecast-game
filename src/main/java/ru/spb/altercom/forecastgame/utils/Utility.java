package ru.spb.altercom.forecastgame.utils;

public class Utility {

    private Utility(){}

    public static String getFormTitle(String formName) {
        return formName + " (New)";
    }

    public static String getFormTitle(String formName, Long id) {
        return formName + " (" + id + ")";
    }

}
