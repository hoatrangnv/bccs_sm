/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Doan Thanh 8
 */
public class ImSearchBean {
    private String code;
    private String name;
    private String otherParamValue;
    private HttpServletRequest httpServletRequest;

    public ImSearchBean() {
    }

    public ImSearchBean(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public String getOtherParamValue() {
        return otherParamValue;
    }

    public void setOtherParamValue(String otherParamValue) {
        this.otherParamValue = otherParamValue;
    }
    
}
