package com.aagu.blog.Utils;

import org.jsoup.Jsoup;

public class TextUtil {
    /**
     *
     * @param Html
     * @param length
     * @return subtext of length
     */
    public static String extractTextFromHtml(String Html, int length) {
        String text = Jsoup.parse(Html).text();
        if (length == -1) {
            return text;
        }
        text = text.replaceAll("#", "").replaceAll("[^0-9a-zA-Z\u4e00-\u9fa5,.，。？“”?' '\"]+", "");
        return length > text.length() ? text : text.substring(0, length) + "...";
    }

    /**
     *
     * @param str
     * @param length
     * @return first length' character
     */
    public static String cutString(String str, int length) {
        if (str == null) {
            return "";
        }
        return length > str.length()? str : str.substring(0, length) + "...";
    }
}
