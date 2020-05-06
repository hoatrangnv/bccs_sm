/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author haidd
 */
public class StockTransBean implements Serializable{
    private static final long serialVersionUID = 1L;

    private String stockTransId = null;
    private String nameShopExport = null;
    private String nameShopImport = null;
    private Date createDatetime = null;
    private String actionCode = null;
    private String payStatus = null;
    private String userAction = null;
    private String addressShopImport = null;
    private String shopIdImport = null;
    private String shopCodeIdImport = null;
    private String shopCode = null;
    private String depositStatus = null;

    public String getDepositStatus() {
        return depositStatus;
    }

    public void setDepositStatus(String depositStatus) {
        this.depositStatus = depositStatus;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopCodeIdImport() {
        return shopCodeIdImport;
    }

    public void setShopCodeIdImport(String shopCodeIdImport) {
        this.shopCodeIdImport = shopCodeIdImport;
    }
    

    public String getShopIdImport() {
        return shopIdImport;
    }

    public void setShopIdImport(String shopIdImport) {
        this.shopIdImport = shopIdImport;
    }

    public String getAddressShopImport() {
        return addressShopImport;
    }

    public void setAddressShopImport(String addressShopImport) {
        this.addressShopImport = addressShopImport;
    }



    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getUserAction() {
        return userAction;
    }

    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getNameShopExport() {
        return nameShopExport;
    }

    public void setNameShopExport(String nameShopExport) {
        this.nameShopExport = nameShopExport;
    }

    public String getNameShopImport() {
        return nameShopImport;
    }

    public void setNameShopImport(String nameShopImport) {
        this.nameShopImport = nameShopImport;
    }

    public String getStockTransId() {
        return stockTransId;
    }

    public void setStockTransId(String stockTransId) {
        this.stockTransId = stockTransId;
    }



}
