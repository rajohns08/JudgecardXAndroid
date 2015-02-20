package com.rajohns.judgecardx.Utils;

/**
 * Created by rajohns on 2/19/15.
 *
 */
public class StringUtils {
    public static String removeNumbers(String text) {
        text = text.replace("2", "");
        text = text.replace("3", "");
        text = text.replace("4", "");
        text = text.replace("5", "");
        text = text.replace("6", "");
        text = text.replace("7", "");
        text = text.replace("8", "");
        text = text.replace("9", "");
        text = text.replace("10", "");
        text = text.replace("11", "");
        text = text.replace("12", "");
        text = text.replace("13", "");

        return text;
    }
}
