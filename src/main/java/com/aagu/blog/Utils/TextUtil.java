package com.aagu.blog.Utils;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import org.jsoup.Jsoup;
import org.springframework.expression.ParserContext;

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

    public static String markdownParser(String markdown) {
        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        Node document = parser.parse(markdown);
        return renderer.render(document);
    }
}
