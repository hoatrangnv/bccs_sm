/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;

/**
 *
 * @author tronglv
 */
public class ReportInvoiceDailyRemainBean {    
    private String shopCode;
    private String shopName;
    private String objCode;
    private String objName;
    private String serialNo;
    private String blockNo;
    private Long fromInvoice;
    private Long toInvoice;
    private Long quantity;

    public ReportInvoiceDailyRemainBean() {
    }

    public String getBlockNo() {
        return blockNo;
    }

    public void setBlockNo(String blockNo) {
        this.blockNo = blockNo;
    }

    public Long getFromInvoice() {
        return fromInvoice;
    }

    public void setFromInvoice(Long fromInvoice) {
        this.fromInvoice = fromInvoice;
    }

    public String getObjCode() {
        return objCode;
    }

    public void setObjCode(String objCode) {
        this.objCode = objCode;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getToInvoice() {
        return toInvoice;
    }

    public void setToInvoice(Long toInvoice) {
        this.toInvoice = toInvoice;
    }    
}
