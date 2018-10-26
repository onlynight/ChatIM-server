package com.github.onlynight.chatim.server.utils;

public class TextUtils {

    public static boolean isEmpty(String text) {
        if (text == null) {
            return true;
        }

        return text.length() <= 0;
    }

}
