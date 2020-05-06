/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.common.util;

import com.guhesan.querycrypt.QueryCrypt;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 *
 * @author kdvt_haidd4
 */
public class QueryCryptSessionBean implements Serializable {

    private static final Logger logger = Logger.getLogger(QueryCryptSessionBean.class);
    private String parameterId = null;
    private String queryName = null;
    private String parameterId1 = null;
    private String queryName1 = null;
    private String parameterId2 = null;
    private String queryName2 = null;
    private Object httpRequestWeb = null;
    private String urlAction = null;

    public String getUrlAction() {
        return urlAction;
    }

    public void setUrlAction(String urlAction) {
        this.urlAction = urlAction;
    }

    public String getParameterId1() {
        return parameterId1;
    }

    public void setParameterId1(String parameterId1) {
        this.parameterId1 = parameterId1;
    }

    public String getParameterId2() {
        return parameterId2;
    }

    public void setParameterId2(String parameterId2) {
        this.parameterId2 = parameterId2;
    }

    public String getQueryName1() {
        return queryName1;
    }

    public void setQueryName1(String queryName1) {
        this.queryName1 = queryName1;
    }

    public String getQueryName2() {
        return queryName2;
    }

    public void setQueryName2(String queryName2) {
        this.queryName2 = queryName2;
    }

    public Object getHttpRequestWeb() {
        return httpRequestWeb;
    }

    public void setHttpRequestWeb(Object httpRequestWeb) {
        this.httpRequestWeb = httpRequestWeb;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getParameterId() {
        return parameterId;
    }

    public void setParameterId(String parameterId) {
        this.parameterId = parameterId;
    }

    public String printParamCrypt() {
        return parameterCrypt();
    }

    public String printUrlCrypt() {
        if (httpRequestWeb != null) {
            return ((HttpServletRequest) httpRequestWeb).getContextPath() + "/" + getUrlAction() + "?" + parameterCrypt();
        } else {
            return "";
        }
    }

    private String parameterCrypt() {
        try {
            logger.debug("queryName : " + queryName);
            logger.debug("parameterId : " + parameterId);
            if (httpRequestWeb != null) {
                String tempBuild = null;
                if (StringUtils.hasText(getParameterId())) {
                    tempBuild = getQueryName() + "=" + getParameterId();
                }

                if (StringUtils.hasText(tempBuild)) {
                    if (StringUtils.hasText(getParameterId1())) {
                        tempBuild = tempBuild + "&" + getQueryName1() + "=" + getParameterId1();
                    }
                } else {
                    if (StringUtils.hasText(getParameterId1())) {
                        tempBuild = getQueryName1() + "=" + getParameterId1();
                    }
                }

                if (StringUtils.hasText(tempBuild)) {
                    if (StringUtils.hasText(getParameterId2())) {
                        tempBuild = tempBuild + "&" + getQueryName2() + "=" + getParameterId2();
                    }
                } else {
                    if (StringUtils.hasText(getParameterId2())) {
                        tempBuild = getQueryName2() + "=" + getParameterId2();
                    }
                }

                logger.debug("Query string : " + tempBuild);
                @SuppressWarnings("static-access")
                String tempCryptParameterId = QueryCrypt.getInstance().encrypt((HttpServletRequest) httpRequestWeb, tempBuild);
                logger.debug("ParametId encrypt : " + tempCryptParameterId);
                return tempCryptParameterId;
            } else {
                logger.error("Request for session is null ");
            }
        } catch (Exception e) {
            // e.printStackTrace();
            logger.error("Exception decrypt ASE Session: " + e.getMessage());
        }
        return "";
    }
    
    public String encryptString() {
        try {
            logger.debug("parameterId : " + parameterId);            
            if (httpRequestWeb != null) {                                
                if(!StringUtils.hasText(getParameterId())){
                    return "";
                }
                
                @SuppressWarnings("static-access")
                String tempCryptParameterId = QueryCrypt.getInstance().encryptString((HttpServletRequest) httpRequestWeb, getParameterId());
                logger.debug("ParametId encrypt : " + tempCryptParameterId);
                return tempCryptParameterId;
            } else {
                logger.error("Request for session is null ");
            }
        } catch (Exception e) {
            // e.printStackTrace();
            logger.error("Exception decrypt ASE Session: ",e);
        }
        return "";
    }
}
