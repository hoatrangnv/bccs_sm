/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

/**
 *
 * @author NamNX
 */
public class LogAssignIsdnStatusBean {

    private int no;
    private String isdn;
    private String isdnType;
    private String status;
    private String errorMessage;

    public LogAssignIsdnStatusBean() {
    }

    public LogAssignIsdnStatusBean(int no, String isdn, String isdnType, String status, String errorMessage) {

        this.no = no;
        this.isdn = isdn;
        this.isdnType = isdnType;
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getIsdnType() {
        return isdnType;
    }

    public void setIsdnType(String isdnType) {
        this.isdnType = isdnType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }
}
