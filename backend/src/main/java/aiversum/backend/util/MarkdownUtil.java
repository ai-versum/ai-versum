package aiversum.backend.util;

public class MarkdownUtil {
    public static String wrapImage(String altText, String url) {
        return "![" + altText + "](" + url + ")";
    }
}
