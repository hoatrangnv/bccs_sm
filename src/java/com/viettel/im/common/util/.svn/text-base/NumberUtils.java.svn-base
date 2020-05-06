/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.common.util;

import com.viettel.im.common.Constant;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

/**
 *
 * @author tronglv
 */
public class NumberUtils {

    public static Double convertStringtoNumber(String pNumber) {
        String number = (pNumber != null) ? pNumber.trim() : "";
        number = number.replaceAll(",", "");

        return Double.parseDouble(number);
    }

    public static Double roundNumber(Double pNumber) {
        return roundNumber(pNumber, Constant.NUMBER_DIGITS_AFTER_DECIMAL_DEFAULT);
    }

    public static Double roundNumber(Double pNumber, Integer decimalPlace) {
        if (pNumber == null) {
            return null;
        }
        if (decimalPlace == null) {
            return null;
        }

        BigDecimal bd = new BigDecimal(pNumber);
        bd = bd.setScale(6, BigDecimal.ROUND_HALF_UP);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    //Format kieu double
    public static String formatNumber(Double pNumber) {
        if (pNumber == null) {
            return "";
        }
        Locale locale = com.viettel.database.DAO.BaseDAOAction.locale;
        if (locale == null) {
            locale = Constant.LOCALE_DEFAULT;
        }
        locale = Constant.LOCALE_DEFAULT;//Da xu ly fix ngon ngu tieng Nhat roi ==> 000,000,000.0000
        NumberFormat nf = NumberFormat.getInstance(locale);

        if (nf instanceof DecimalFormat) {
            ((DecimalFormat) nf).setDecimalSeparatorAlwaysShown(true);
        }
        String result = nf.format(pNumber);
        if (nf instanceof DecimalFormat) {
            char symbol = ((DecimalFormat) nf).getDecimalFormatSymbols().getDecimalSeparator();
            int idx = result.lastIndexOf(symbol);
            String nguyen = result.substring(0, idx + 1);
            String thapphan = result.substring(idx + 1);
            while (thapphan.length() < Constant.NUMBER_DIGITS_AFTER_DECIMAL_AMOUNT) {
                thapphan += "0";
            }
            result = nguyen + thapphan;
        }

        return result;

    }

    //Format kieu long
    public static String formatNumber(Long pNumber) {
        if (pNumber == null) {
            return "";
        }
        Locale locale = com.viettel.database.DAO.BaseDAOAction.locale;
        if (locale == null) {
            locale = Constant.LOCALE_DEFAULT;
        }
        locale = Constant.LOCALE_DEFAULT;
        NumberFormat nf = NumberFormat.getInstance(locale);
        String result = nf.format(pNumber);
        return result;
    }

    //Lam tron so (theo sl chu so thap phan) & format kieu string
    public static String rounđAndFormatNumber(Double pNumber, Integer decimalPlace) {
        pNumber = roundNumber(pNumber, decimalPlace);
        return formatNumber(pNumber);
    }

    //Lam tron so & format kieu string
    public static String rounđAndFormatNumber(Double pNumber) {
        pNumber = roundNumber(pNumber);
        return formatNumber(pNumber);
    }

    //Lam tron tong tien va format kieu string
    public static String rounđAndFormatAmount(Double pAmount) {
        pAmount = roundNumber(pAmount, Constant.NUMBER_DIGITS_AFTER_DECIMAL_AMOUNT);
        return formatNumber(pAmount);
    }

    //Lam tron so luong va format kieu string
    public static String rounđAndFormatQuantity(Double pQuantity) {
        pQuantity = roundNumber(pQuantity, Constant.NUMBER_DIGITS_AFTER_DECIMAL_QUANTITY);
        return formatNumber(pQuantity);
    }

    // tutm1 - 12/10/11
    //format string theo language 
    public static String roundAndFormatNumberUSLocale(Double number) {
        //String l = System.getProperty("user.language");
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        Double pNumber = roundNumber(number);
        if (pNumber == null) {
            return "";
        }
        return nf.format(pNumber);
    }

    // tutm1 - 12/10/11
    // format Double theo language 
    public static Double convertStringToNumberUSLocale(String number) {
        try {
            NumberFormat nf = NumberFormat.getInstance(Locale.US);
            DecimalFormat df = (DecimalFormat) nf;
            df.setParseBigDecimal(true);
            BigDecimal bd = (BigDecimal) df.parse(number);
            return bd.doubleValue();
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {

        System.out.println("================> amount = ");
        Double a = convertStringtoNumber("11,000.12345");
        System.out.println("a = " + a);
        String b = rounđAndFormatAmount(a);
        System.out.println("b = " + b);
        System.out.println(" ");


        System.out.println("================> amount = ");
        System.out.println(rounđAndFormatAmount(1000.12345));
        System.out.println(" ");

        System.out.println("================> amount = ");
        System.out.println(rounđAndFormatAmount(0.12345));
        System.out.println(" ");

        System.out.println("================> amount = ");
        System.out.println(rounđAndFormatAmount(1000.0));
        System.out.println(" ");

        System.out.println("================> amount = ");
        System.out.println(rounđAndFormatAmount(1000.1));
        System.out.println(" ");


    }
    // ThinDM R6762
    public static boolean chkNumber(String sNumber) {
        boolean result = true;
        int i = 0;
        for (i = 0; i < sNumber.length(); i++) {
            // Check that current character is number.
            if (!Character.isDigit(sNumber.charAt(i))) {
                result = false;
            }
        }
        return result;
    }
    
    private static final String numeric = "0123456789";
    private static final Random rd = new Random();
    
    public static String randomString(int length){
        StringBuilder sb = new StringBuilder(length);
        for(int i = 0 ; i < length; i++){
            sb.append(numeric.charAt(rd.nextInt(length)));
        }
        return sb.toString();
    }
}
