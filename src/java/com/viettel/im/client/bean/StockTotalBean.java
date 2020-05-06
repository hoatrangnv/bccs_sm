/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

import java.util.Date;

/**
 *
 * @author mov_itbl_dinhdc
 */
public class StockTotalBean {
    
    private Long ownerId;
    private Long ownerType;
    private Long stockModelId;
    private Long stateId;
    private Long quantity;
    private Long quantityIssue;
    private Date modifiedDate;
    private Long status;
    private Long quantityDial;
    private Long maxDebit;
    private Long currentDebit;
    private Long dateReset;
    private Long quantityHang;
    private Long limitQuantity;

    public StockTotalBean() {
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(Long ownerType) {
        this.ownerType = ownerType;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getQuantityIssue() {
        return quantityIssue;
    }

    public void setQuantityIssue(Long quantityIssue) {
        this.quantityIssue = quantityIssue;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getQuantityDial() {
        return quantityDial;
    }

    public void setQuantityDial(Long quantityDial) {
        this.quantityDial = quantityDial;
    }

    public Long getMaxDebit() {
        return maxDebit;
    }

    public void setMaxDebit(Long maxDebit) {
        this.maxDebit = maxDebit;
    }

    public Long getCurrentDebit() {
        return currentDebit;
    }

    public void setCurrentDebit(Long currentDebit) {
        this.currentDebit = currentDebit;
    }

    public Long getDateReset() {
        return dateReset;
    }

    public void setDateReset(Long dateReset) {
        this.dateReset = dateReset;
    }

    public Long getQuantityHang() {
        return quantityHang;
    }

    public void setQuantityHang(Long quantityHang) {
        this.quantityHang = quantityHang;
    }

    public Long getLimitQuantity() {
        return limitQuantity;
    }

    public void setLimitQuantity(Long limitQuantity) {
        this.limitQuantity = limitQuantity;
    }
    
}
