/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;

/**
 *
 * @author AnDV
 */
public class IsdnBean implements java.io.Serializable {
    private String isdn;
    private String stockModelName;

    public IsdnBean() {
    }

    public IsdnBean(String isdn, String stockModelName) {
        this.isdn = isdn;
        this.stockModelName = stockModelName;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getStockModelName() {
        return stockModelName;
    }

    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }


}
