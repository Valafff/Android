package com.amicus.myapplication;

import java.util.ArrayList;
import java.util.List;

public class Common {
    private static String prompt = "";
    private static Double temp = 1.5;

    public static String getPrompt() {
        return prompt;
    }

    public static void setPrompt(String prompt) {
        Common.prompt = prompt;
    }

    public static Double getTemp() {
        return temp;
    }

    public static void setTemp(Double temp) {
        Common.temp = temp;
    }
}