/*
 * EditorUtils.java
 *
 * Created on August 26, 2006, 11:11 PM
 * @author quandv
 */

package com.viettel.privilege.common;

public class EditorUtils {
    public static char[][] signChars={
            {97,224,225,7843,227,7841,259,7857,7855,7859,7861,7863,226,7855,7847,7845,7849,7851,7853},
            {111,242,243,7887,245,7885,244,7891,7889,7893,7895,7897,417,7901,7899,7903,7905,7907},
            {101,232,233,283,202,7867,7869,7865,234,7873,7871,7875,7875,7879},
            {117,249,250,7911,361,7909,432,7915,7913,7917,7919,7921},
            {105,236,237,7881,297,7883},
            {121,7923,253,7927,7929,7925},
            {100, 273},
            {68, 272}            
    };

  /* Method convert VietNamese String with sign charactor to
     * VietNamese String with unsign charactor. This method is
     * use when convert message to send to subcriber by sms
     * which many device can't display sms as VietNamese.
     * @param orgStr
     * @return String with all sign character is converted to unsign
     */
    public static String toUnSign(String orgStr){
        if(orgStr == null || orgStr.length() ==0) return null;
        StringBuffer buf = new StringBuffer();
        for(int i=0 ; i < orgStr.length() ; i++){
            buf.append(toUnsign(orgStr.charAt(i)));
        }
        return buf.toString();
    }
    /**
     * decode a sign char to unsign char
     * @param c
     * @return unsign character
     */
    public static char toUnsign(char c){
        for (char[] signChar : signChars) {
            for (char aSignChar : signChar) {
                if (aSignChar == c)
                    return signChar[0];
            }
        }
        return c;
    }

    public static int getIntValue(String s, int defaultValue){
        try{
            return Integer.parseInt(s);
        }catch(NumberFormatException ex){
            return defaultValue;
        }
    }
}
