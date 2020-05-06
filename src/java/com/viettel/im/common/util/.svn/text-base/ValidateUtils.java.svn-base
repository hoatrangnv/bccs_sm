/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.common.util;

/**
 *
 * @author TungTV
 */
public class ValidateUtils {

    
    /**
     * <P>Check min length</P>
     *
     * @param str String to check
     * @param minLength 
     * @return @boolean true if valid, false if not valid
     */
    public static boolean isMinLength(String str, int minLength) {

        if (str.length() >= minLength) {
            return true;
        }else{
            return false;
        }
    }
    

    /**
     * <P>Check max length</P>
     *
     * @param str String to check
     * @param maxLength
     * @return @boolean true if valid, false if not valid
     */
    public static boolean isMaxLength(String str, int maxLength) {

       if (str.length() <= maxLength) {
           return true;
       } else {
           return false;
       }
    }


    /**
     * <P>Check is Integer or not</P>
     *
     * @param str String to check
     * @param str
     * @return @boolean true if valid, false if not valid
     */
    public  static boolean isInteger(String str) {
        if (str == null || !str.matches("[0-9]+$")) {
            return false;
        }
        return true;
    }

}
