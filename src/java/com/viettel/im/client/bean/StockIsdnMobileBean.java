/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

/**
 *
 * @author AnDV
 */
public class StockIsdnMobileBean implements java.io.Serializable {

    private String isdn;
    private Long stockModelId;
    private String isdnType;
    private Long stateId;

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

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public StockIsdnMobileBean() {
    }

    public StockIsdnMobileBean(String isdn, Long stockModelId, String isdnType, Long stateId) {
        this.isdn = isdn;
        this.stockModelId = stockModelId;
        this.isdnType = isdnType;
        this.stateId = stateId;
    }


}
