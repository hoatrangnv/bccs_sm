/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.BO;

import java.util.Date;

/**
 *
 * @author os_levt1
 */
public class TransactionAnypay implements java.io.Serializable {

    private Long transactionId;
    private Date createDate;
    private String userName;
    private Long shopId;
    private String shopCodePlus;
    private String shopCodeMinus;
    private Double transAmountPlus;
    private Double transAmountMinus;
    private String ipAddress;
    private String description;

    public TransactionAnypay() {
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getShopCodeMinus() {
        return shopCodeMinus;
    }

    public void setShopCodeMinus(String shopCodeMinus) {
        this.shopCodeMinus = shopCodeMinus;
    }

    public String getShopCodePlus() {
        return shopCodePlus;
    }

    public void setShopCodePlus(String shopCodePlus) {
        this.shopCodePlus = shopCodePlus;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Double getTransAmountMinus() {
        return transAmountMinus;
    }

    public void setTransAmountMinus(Double transAmountMinus) {
        this.transAmountMinus = transAmountMinus;
    }

    public Double getTransAmountPlus() {
        return transAmountPlus;
    }

    public void setTransAmountPlus(Double transAmountPlus) {
        this.transAmountPlus = transAmountPlus;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
 
}
