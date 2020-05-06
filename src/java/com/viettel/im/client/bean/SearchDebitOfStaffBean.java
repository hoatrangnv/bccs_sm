/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;

/**
 *
 * @author ThinhPH2
 */
public class SearchDebitOfStaffBean {
    private String staffCode;
    private String debitTypeName;
    private String debitDayName;
    private String financeName;
    private Double debitType;
    private Double currentAmount;
    private String ownerType;
    private Long requestDebitType;
    private String stockTypeName;

    public Long getRequestDebitType() {
        return requestDebitType;
    }

    public void setRequestDebitType(Long requestDebitType) {
        this.requestDebitType = requestDebitType;
    }

    public String getStockTypeName() {
        return stockTypeName;
    }

    public void setStockTypeName(String stockTypeName) {
        this.stockTypeName = stockTypeName;
    }
    
    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public String getDebitDayName() {
        return debitDayName;
    }

    public void setDebitDayName(String debitDayName) {
        this.debitDayName = debitDayName;
    }

    public String getDebitTypeName() {
        return debitTypeName;
    }

    public void setDebitTypeName(String debitTypeName) {
        this.debitTypeName = debitTypeName;
    }

    public String getFinanceName() {
        return financeName;
    }

    public void setFinanceName(String financeName) {
        this.financeName = financeName;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public Double getDebitType() {
        return debitType;
    }

    public void setDebitType(Double debitType) {
        this.debitType = debitType;
    }

    public Double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Double currentAmount) {
        this.currentAmount = currentAmount;
    }
    
}
