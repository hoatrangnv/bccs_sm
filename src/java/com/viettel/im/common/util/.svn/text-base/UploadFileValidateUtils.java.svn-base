/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.common.util;

import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author thinhph2_s
 */
public class UploadFileValidateUtils {
    public static final String EXCEL_2003_EXTENSION = "xls";
    public static final String EXCEL_2007_EXTENSION = "xlsx";
    public static final String WORD_2003_EXTENSION = "doc";
    public static final String WORD_2007_EXTENSION = "docx";
    public static final String PDF_EXTENSION = "pdf";
    public static final String ZIP_EXTENSION = "zip";
    public static final String RAR_EXTENSION = "rar";

    public static final int MAX_SIZE_EXCEL_2003_EXTENSION = 20000000;
    public static final String TEXT_EXTENSION = "txt";
    public static final int MAX_SIZE_TEXT_EXTENSION = 20000000;
    public static final String MAX_SIZE_ERROR_NOTICE = "Chỉ được upload file dữ liệu < 20M. ";
    public static final String MAX_INPUT_SIZE_ERROR_NOTICE = "Chỉ được upload file dữ liệu < ";
    public static final String BAD_FILE_ERROR_NOTICE = "File dữ liệu không hợp lệ !";
    public static final String BAD_FILE_NAME_ERROR_NOTICE ="Tên file không được rỗng";
    public static final String BAD_FILE_CONTENT_NOTICE ="Nội dung file upload không đúng định dạng";
    public static final String BAD_FILE_BLANK_CONTENT ="File dữ liệu không có dữ liệu nào.";
    public static final HashMap<String,String> MAP_FILE_TYPE;
    static{
        MAP_FILE_TYPE=new HashMap<String, String>();
        MAP_FILE_TYPE.put(EXCEL_2003_EXTENSION,"Excel");
        MAP_FILE_TYPE.put(EXCEL_2007_EXTENSION,"Excel");
        MAP_FILE_TYPE.put(WORD_2003_EXTENSION,"word");
        MAP_FILE_TYPE.put(WORD_2007_EXTENSION,"word");
        MAP_FILE_TYPE.put(PDF_EXTENSION,"pdf");
        MAP_FILE_TYPE.put(TEXT_EXTENSION,"Text");
        MAP_FILE_TYPE.put(ZIP_EXTENSION,"zip");
        MAP_FILE_TYPE.put(RAR_EXTENSION,"rar");

    }
    public static String validateFileUpload(File fileAttachment, String extension, int maxSize) {
        String fileName = fileAttachment.getName();

        // check file name
        if (!StringUtils.validString(fileName)) {
            return "Chưa nhập file !";
        }

        // check duoi file
        String[] fileNameArr = fileName.split("\\.");
        if (fileNameArr == null || fileNameArr.length != 2 ) {
            return BAD_FILE_ERROR_NOTICE;
        }
        if (fileAttachment.getTotalSpace() > maxSize) {
            return MAX_SIZE_ERROR_NOTICE;
        }

        //kiem tra ten file mo rong co fai la extension ko
        String extensionFileName = fileNameArr[1];
        if (!extension.equalsIgnoreCase(extensionFileName)) {
            return "File dữ liệu gửi lên không đúng định dạng. Hệ thống chỉ hỗ trợ file dữ liệu "+MAP_FILE_TYPE.get(extension);
        }

        return validateFileName(fileName);
    }

    public static String validateFileName(String fileName) {
        String expression = "[_a-zA-Z0-9\\-\\.]+";
        //Make the comparison case-insensitive.
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(fileName);
        if (!matcher.matches()) {
            return "Tên file chỉ được bao gồm các kí tự chữ cái (a-z, A-Z), chữ số (0-9), gạch ngang (-), gạch dưới (_) và dấu chấm (.) ";
        }
        return null;
    }
    
    public static String validateFileName1(String fileName) {
        String expression = "[_a-zA-Z0-9\\-\\.]+";
        //Make the comparison case-insensitive.
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(fileName);
        if (!matcher.matches()) {
            return "Tên file chỉ được bao gồm các kí tự chữ cái (a-z, A-Z), chữ số (0-9), gạch ngang (-), gạch dưới (_) ";
        }
        return null;
    }
}
