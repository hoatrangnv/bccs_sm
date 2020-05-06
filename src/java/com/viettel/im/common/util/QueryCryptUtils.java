/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.common.util;

import com.guhesan.querycrypt.QueryCrypt;
import com.guhesan.querycrypt.beans.RequestParameterObject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author conglt
 */
public class QueryCryptUtils {
    public static final String FILE_PATH_PREFIX = "filePath";
    public static final String CRYPT_PREFIX = "_vt";

    /**
     * @author CongLT
     * @param req
     * @param para
     * @return
     */
    @SuppressWarnings("static-access")
    public static String getParameter(HttpServletRequest req, String parameterName) throws Exception {
        String checkCrypt = req.getParameter(CRYPT_PREFIX);
        RequestParameterObject reqCrypt = null;
        if (checkCrypt != null) {
            reqCrypt = QueryCrypt.getInstance().decrypt(req);
        }

        String strResult = null;
        if (checkCrypt != null) {
            strResult = reqCrypt.getParameter(parameterName);
            if (strResult == null){
                strResult = reqCrypt.getParameter("amp;"+parameterName);
            }
        } else {
            strResult = req.getParameter(parameterName);
            if (strResult == null){
                strResult = req.getParameter("amp;"+parameterName);
            }
        }

        return strResult;
    }

    @SuppressWarnings("static-access")
    public static String getParameterString(HttpServletRequest req, String parameterName) throws Exception {
        String encryptString = req.getParameter(parameterName);
        if (StringUtils.hasText(encryptString)) {
            encryptString = QueryCrypt.getInstance().decryptString(req, encryptString);
            return encryptString;
        }else{
            return null;
        }
    }
    
    /*
     * thinhph2 
     */
    public static String setDownloadPath(String filePath) {
        String strReturn;
        strReturn = FILE_PATH_PREFIX + "=" + filePath;
        return strReturn;
    }
}
