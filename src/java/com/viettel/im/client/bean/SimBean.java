/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;

/**
 *
 * @author AnDV
 */
public class SimBean implements java.io.Serializable{
    String serial;
    Long stockModelId;

    public SimBean(String serial, Long stockModelId) {
        this.serial = serial;
        this.stockModelId = stockModelId;
    }

    public SimBean() {
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

  


}
