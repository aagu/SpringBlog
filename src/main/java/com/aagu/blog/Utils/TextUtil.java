package com.aagu.blog.Utils;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import org.jsoup.Jsoup;

import java.util.Arrays;
import java.util.Optional;

public class TextUtil {
    private static final String[][] monthDigitMapper = new String[][] {
            {"January", "01"},
            {"February", "02"},
            {"March", "03"},
            {"April", "04"},
            {"May", "05"},
            {"June", "06"},
            {"July", "07"},
            {"August", "08"},
            {"September", "09"},
            {"October", "10"},
            {"November", "11"},
            {"December", "12"}
    };

    private static final String[][] monthZhMapper = new String[][] {
            {"January", "一月"},
            {"February", "二月"},
            {"March", "三月"},
            {"April", "四月"},
            {"May", "五月"},
            {"June", "六月"},
            {"July", "七月"},
            {"August", "八月"},
            {"September", "九月"},
            {"October", "十月"},
            {"November", "十一月"},
            {"December", "十二月"}
    };

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

    public static String getDigitMonth(String s) {
        return mapMonth(s, "-", monthDigitMapper);
    }

    public static String getZhMonth(String s) {
        return mapMonth(s, " ", monthZhMapper);
    }

    private static String mapMonth(String s, String divider, String[][] monthZhMapper) {
        String year = s.split(" ")[0];
        String month = s.split(" ")[1];
        Optional<String> opt = Arrays.stream(monthZhMapper).filter(m -> month.equals(m[0])).map(m -> m[1]).findFirst();
        return opt.map(value -> year + divider + value).orElse(s);
    }

    public static boolean notEmpty(String s) {
        return s != null && !s.isEmpty();
    }
}
