/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

import java.math.BigDecimal;

/**
 *
 * @author kdvt_phuoctv
 */
public class StockOwnerTmpBean {

    private Long stockId;
    private String shopCode;
    private String shopName;
    private String staffCode;
    private String staffName;
    private String channelTypeName;
    private Double currentDebit;
    private String strCurrentDebit;
    private Double maxDebit;
    private String strMaxDebit;
    private Long ownerId;
    private String ownerType;
    private Long channelTypeId;

    public StockOwnerTmpBean() {
    }

    public StockOwnerTmpBean(Long ownerId, String staffCode, String staffName, Long channelTypeId) {
        this.ownerId = ownerId;
        this.staffCode = staffCode;
        this.staffName = staffName;
        this.channelTypeId = channelTypeId;
    }

    public StockOwnerTmpBean(Long stockId,
            String shopCode,
            String shopName,
            String staffCode,
            String staffName,
            String channelTypeName,
            Double currentDebit,
            Double maxDebit) {
        this.stockId = stockId;
        this.shopCode = shopCode;
        this.shopName = shopName;
        this.staffCode = staffCode;
        this.staffName = staffName;
        this.channelTypeName = channelTypeName;
        this.currentDebit = currentDebit;
        this.maxDebit = maxDebit;
        if (maxDebit != null) {
            this.strMaxDebit = BigDecimal.valueOf(maxDebit).toString();
        }
        if (currentDebit != null) {
            this.strCurrentDebit = BigDecimal.valueOf(currentDebit).toString();
        }
    }

    public Double getCurrentDebit() {
        return currentDebit;
    }

    public void setCurrentDebit(Double currentDebit) {
        this.currentDebit = currentDebit;
    }

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public Double getMaxDebit() {
        return maxDebit;
    }

    public void setMaxDebit(Double maxDebit) {
        this.maxDebit = maxDebit;
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

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getChannelTypeName() {
        return channelTypeName;
    }

    public void setChannelTypeName(String channelTypeName) {
        this.channelTypeName = channelTypeName;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public String getStrMaxDebit() {
        return strMaxDebit;
    }

    public void setStrMaxDebit(String strMaxDebit) {
        this.strMaxDebit = strMaxDebit;
    }

    public String getStrCurrentDebit() {
        return strCurrentDebit;
    }

    public void setStrCurrentDebit(String strCurrentDebit) {
        this.strCurrentDebit = strCurrentDebit;
    }
}
