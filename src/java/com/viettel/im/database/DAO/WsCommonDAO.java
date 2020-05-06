/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.database.DAO;

//import com.viettel.im.common.Constant;
//import java.math.BigInteger;
//import java.security.MessageDigest;
//import java.text.DecimalFormat;
//import java.text.NumberFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import oracle.jdbc.Const;
//
//import java.security.Key;
//import java.security.Security;


/**
 *
 * @author MrSun
 */
public class WsCommonDAO {

    /*------------------------------------------------------------------------*/
    /* Security Utils */
    /*------------------------------------------------------------------------*/
    
//    static private String strKey = "iexsbccs";
//    static private String algorithm = "DES";
//    
//    static private String strDefaultKey = "123456789";
//    static private String defaultAlgorithm = "DES/ECB/NoPadding";
//    
//    static private String formatPatternLongDateZTEIN = "yyyy.MM.DD HH:mm:ss";
//    static private String formatPatternShortDateZTEIN = "yyyy.MM.DD";
//    static private String formatPatternLongDateBCCS = "DD/MM/yyyy HH:mm:ss";
//    static private String formatPatternShortDateBCCS = "DD/MM/yyyy";
//  
//    static private byte[] getKey(byte[] arrBTmp) throws Exception {
//        byte[] arrB = new byte[8];
//        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
//            arrB[i] = arrBTmp[i];
//        }
//        return arrB;
//    }
//    
//    /**
//     * Ma hoa MD5
//     */
//    public static String getMD5(String inputString) {
//        try{
//            if (inputString == null)  // Check value to encrypt
//                return null;
//            MessageDigest mdEnc = MessageDigest.getInstance("MD5"); // Encryption algorithm
//            mdEnc.update(inputString.getBytes(), 0, inputString.length()); // Encrypting
//            return new BigInteger(1, mdEnc.digest()).toString(16); // Encrypted string
//        }
//        catch(Exception ex) {
//            ex.printStackTrace();
//            return "";
//        }
//    }
//
//     /**
//     * Ma hoa DES64
//     */    
//    public static String encryptByDES64(String str) {
//        try {            
//            Security.addProvider(new com.sun.crypto.provider.SunJCE());
//            byte[] key = getKey(strKey.getBytes());
//
//            //gen SecretKey
//            SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
//            Cipher ecipher = Cipher.getInstance(algorithm);
//            ecipher.init(Cipher.ENCRYPT_MODE, keySpec);
//
//            // Encode the string into bytes using utf-8
//            byte[] utf8 = str.getBytes("UTF8");
//
//            // Encrypt
//            byte[] enc = ecipher.doFinal(utf8);
//
//            // Encode bytes to base64 to get a string
//            return new sun.misc.BASE64Encoder().encode(enc);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }            
//        return null;        
//    }
//
//    public static String decryptByDES64(String str) {
//       try {           
//           Security.addProvider(new com.sun.crypto.provider.SunJCE());
//            byte[] key = getKey(strKey.getBytes());
//            
//           //gen SecretKey
//            SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
//            Cipher dcipher = Cipher.getInstance(algorithm);
//            dcipher.init(Cipher.DECRYPT_MODE, keySpec);
//
//            // Decode base64 to get bytes
//            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
//
//            // Decrypt
//            byte[] utf8 = dcipher.doFinal(dec);
//
//            // Decode using utf-8
//            return new String(utf8, "UTF8");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
//    
//    /*------------------------------------------------------------------------*/
//    /*------------------------------------------------------------------------*/
//    
//    
//    /*------------------------------------------------------------------------*/
//    /* Datetime Utils */
//    /*------------------------------------------------------------------------*/
//    
//    //get string of current date output format yyyymmddhhmiss
//    public static String getCurrentDateLongString() {
//        Date date = new Date();
//        String pattern = "yyyyMMddHHmmss";
//        SimpleDateFormat format = new SimpleDateFormat(pattern);
//        return format.format(date);
//    }
//    
//    //get string of current date output format yyyymmdd
//    public static String getCurrentDateShortString() {
//        Date date = new Date();
//        String pattern = "yyyyMMDD";                
//        SimpleDateFormat format = new SimpleDateFormat(pattern);
//        return format.format(date);
//    }
//    
//    //get string of date output format yyyymmddhhmiss
//    public static String getDateLongString(Date arg0) {
//        Date date = arg0;
//        String pattern = "yyyyMMDDHHmmss";                
//        SimpleDateFormat format = new SimpleDateFormat(pattern);
//        return format.format(date);
//    }
//    
//    //get string of date output format yyyymmdd
//    public static String getDateShortString(Date arg0) {
//        Date date = arg0;
//        String pattern = "yyyyMMDD";
//        SimpleDateFormat format = new SimpleDateFormat(pattern);
//        return format.format(date);
//    }
//    
//    //get string of date output format pattern
//    public static String getDateString(Date arg0, String arg1) {
//        Date date = arg0;
//        String pattern = arg1;
//        SimpleDateFormat format = new SimpleDateFormat(pattern);
//        return format.format(date);
//    }    
//    
//    //get date from string format yyyymmddhhmiss
//    public static Date getDateFromLongString(String arg0) throws Exception {
//        String pattern = "yyyyMMDDHHmmss";
//        SimpleDateFormat format = new SimpleDateFormat(pattern);
//        return format.parse(arg0);
//    }
//    
//    //get date from string input format yyyymmdd
//    public static Date getDateFromShortString(String arg0) throws Exception {
//        String pattern = "yyyyMMDD";
//        SimpleDateFormat format = new SimpleDateFormat(pattern);
//        return format.parse(arg0);
//    }
//    
//    //get date from string input format pattern
//    public static Date getDateFromString(String arg0, String arg1) throws Exception {
//        String pattern = arg1;
//        SimpleDateFormat format = new SimpleDateFormat(pattern);
//        return format.parse(arg0);
//    }
//        
//    
//    public static String formatLongDateZTEIN(String arg0) throws Exception {
//        String pattern = formatPatternLongDateZTEIN;
//        SimpleDateFormat format = new SimpleDateFormat(pattern);
//        Date date = format.parse(arg0);
//        return getDateString(date,formatPatternLongDateBCCS);
//    }
//    
//    public static String formatShortDateZTEIN(String arg0) throws Exception {
//        String pattern = formatPatternShortDateZTEIN;
//        SimpleDateFormat format = new SimpleDateFormat(pattern);
//        Date date = format.parse(arg0);
//        return getDateString(date,formatPatternLongDateBCCS);
//    }
//    /*------------------------------------------------------------------------*/
//    /*------------------------------------------------------------------------*/
//    
//    
//    public static SecretKey createSecretKey(String arg0) throws Exception {
//        return KeyGenerator.getInstance(arg0).generateKey();
//    }
//
//
//    /*------------------------------------------------------------------------*/
//    /* Nummeric Utils */
//    /*------------------------------------------------------------------------*/
//    
//    public static String formatNumber(String pattern, double value ) {
//        DecimalFormat myFormatter = new DecimalFormat(pattern);
//        String output = myFormatter.format(value);
//        //System.out.println(value + "  " + pattern + "  " + output);
//        return output;
//   }
//    
//     public static String formatNumber(double value ) {
//        String pattern = "###,###,###,###";        
//        return formatNumber(pattern, value);
//   }
//
//    public static String formatNumber(long value ) {
//        String pattern = "###,###,###,###";
//        DecimalFormat myFormatter = new DecimalFormat(pattern);
//        String output = myFormatter.format(value);
//        //System.out.println(value + "  " + pattern + "  " + output);
//        return output;
//   }
//    public static String formatMoney(Long input) {
//        NumberFormat nf = NumberFormat.getInstance(Locale.US);
//        return nf.format(input);
//    }
//    
//    public static String formatMoney(String localeName, Long input) {
//        NumberFormat nf;
//        if (localeName.equals(Locale.FRANCE.toString()))
//            nf = NumberFormat.getInstance(Locale.FRANCE);
//        else if (localeName.equals(Locale.US.toString()))
//            nf = NumberFormat.getInstance(Locale.US);
//        else if (localeName.equals(Locale.UK.toString()))
//            nf = NumberFormat.getInstance(Locale.UK);
//        else
//            nf = NumberFormat.getInstance(Locale.US);
//        return nf.format(input);
//    }
//
//    
//    
//    /*------------------------------------------------------------------------*/
//    /* String Utils */
//    /*------------------------------------------------------------------------*/
//    //SerialId
//    public static String formatSerialId(Long serialId){
//        return Constant.ACCOUNT_SERIALID_PREFIX + String.format("%0"+Constant.ACCOUNT_ID_LENGTH+"d",serialId);
//        //return String.format("%0"+Constant.ACCOUNT_ID_LENGTH+"d",serialId);
//    }
//    public static String formatSerialId(String serialId){
//        return Constant.ACCOUNT_SERIALID_PREFIX + String.format("%0"+Constant.ACCOUNT_ID_LENGTH+"d",Long.valueOf(serialId));
//        //return String.format("%0"+Constant.ACCOUNT_ID_LENGTH+"d",Long.valueOf(serialId));
//    }
//    public static String getAutoSerialId(){
//        String serialId = WsCommonDAO.getCurrentDateLongString();
//        return formatSerialId(serialId);
//    }
//    //TransSerial
//    public static String formatTransSerial(Long transSerial){
//        return Constant.ACCOUNT_TRANSSERIAL_PREFIX + String.format("%0"+Constant.ACCOUNT_ID_LENGTH+"d",transSerial);
//        //return String.format("%0"+Constant.ACCOUNT_ID_LENGTH+"d",transSerial);
//    }
//    public static String formatTransSerial(String transSerial){
//        return Constant.ACCOUNT_TRANSSERIAL_PREFIX + String.format("%0"+Constant.ACCOUNT_ID_LENGTH+"d",Long.valueOf(transSerial));
//        //return String.format("%0"+Constant.ACCOUNT_ID_LENGTH+"d",Long.valueOf(transSerial));
//    }
//    public static String getAutoTransSerial(){
//        String transSerial = WsCommonDAO.getCurrentDateLongString();
//        return formatTransSerial(transSerial);
//    }    
//    /*------------------------------------------------------------------------*/
//    /*------------------------------------------------------------------------*/
}
