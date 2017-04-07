package com.example.ankhiya.findsimilarimages.utils;

/**
 * Created by ankhiya on 3/29/17.
 */

public class StringUtils {

    public static boolean isNullOrEmpty(String string){
        if(string == null){
            return true;
        }else if(string.length() == 0){
            return true;
        }
        return false;
    }
}
