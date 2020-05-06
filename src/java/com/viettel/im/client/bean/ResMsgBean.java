/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;

/**
 *
 * @author MrSun
 */
public class ResMsgBean {
    
    private String result;
    private String resInfo;
    
    private long transId;

    public ResMsgBean() {
        this.result = "";
        this.resInfo = "";
        this.transId = 0L;
    }

    public ResMsgBean(String result, String resInfo) {
        this.result = result;
        this.resInfo = resInfo;
    }
    
    public String getResInfo() {
        return resInfo;
    }

    public void setResInfo(String resInfo) {
        this.resInfo = resInfo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public long getTransId() {
        return transId;
    }

    public void setTransId(long transId) {
        this.transId = transId;
    }    
}
