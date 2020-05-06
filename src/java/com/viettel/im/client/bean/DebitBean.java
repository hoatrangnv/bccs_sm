/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

/**
 *
 * @author TrongLV_S
 */
public class DebitBean {
    Long shopId;
    Long staffId;
    Long channelTypeId;
    Double rateDebit;
    Double maxDebit;
    Double currentDebit;    

    public DebitBean() {
    }

    public DebitBean(Long shopId, Long staffId, Long channelTypeId, Double rateDebit, Double maxDebit, Double currentDebit) {
        this.shopId = shopId;
        this.staffId = staffId;
        this.channelTypeId = channelTypeId;
        this.rateDebit = rateDebit;
        this.maxDebit = maxDebit;
        this.currentDebit = currentDebit;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public Double getRateDebit() {
        return rateDebit;
    }

    public void setRateDebit(Double rateDebit) {
        this.rateDebit = rateDebit;
    }

    public Double getMaxDebit() {
        return maxDebit;
    }

    public void setMaxDebit(Double maxDebit) {
        this.maxDebit = maxDebit;
    }

    public Double getCurrentDebit() {
        return currentDebit;
    }

    public void setCurrentDebit(Double currentDebit) {
        this.currentDebit = currentDebit;
    }
    
    
}
