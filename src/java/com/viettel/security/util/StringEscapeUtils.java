package com.viettel.security.util;
import java.util.*;

public class StringEscapeUtils {

    public static String escapeHtml(int i) {
        return String.valueOf(i);
    }

    public static String escapeHtml(char i) {
        return String.valueOf(i);
    }

    public static String escapeHtml(float i) {
        return String.valueOf(i);
    }

    public static String escapeHtml(double i) {
        return String.valueOf(i);
    }

    public static String escapeHtml(long i) {
        return String.valueOf(i);
    }

    public static String escapeHtml(boolean i) {
        return String.valueOf(i);
    }

    public static String escapeHtml(Object i) {
        if (i != null) {
            return i.toString();
        } else {
            return "null";
        }
    }

    public static String escapeHtml(String s) {
        if (s != null) {
            return s.replace("&", "&amp;").replace("<", "&lt;");
        } else {
            return "";
        }
    }

    //Remove cac ky tu dac biet khi hien thi ten file
    public static String getSafeFileName(String input) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {

            char c = input.charAt(i);

            if (c != '/' && c != '\\' && c != 0) {

                sb.append(c);

            }
        }
        return sb.toString();
    }

    /**
     * getSafeFieldName
     * @param fieldName
     * @return
     */
    public static String getSafeFieldName(String fieldName) {
        if(fieldName==null||fieldName.equals("")){
            return null;
        }
        List lstErrorChar = new ArrayList();
        lstErrorChar.add(" ");
        lstErrorChar.add(",");
        lstErrorChar.add("/");
        lstErrorChar.add("(");
        lstErrorChar.add(")");
        lstErrorChar.add("'");
        fieldName = org.apache.commons.lang.StringEscapeUtils.escapeSql(fieldName);
        for(int i =0;i < fieldName.length(); i++){
            char c = fieldName.charAt(i);
            if(lstErrorChar.contains(String.valueOf(c))){
                return null;
            }
        }
        return fieldName;
    }



}
