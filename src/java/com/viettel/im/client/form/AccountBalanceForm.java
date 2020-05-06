/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import java.util.Date;

/**
 *
 * @author TrongLV
 */
public class AccountBalanceForm {
    Long shopId;
    String shopCode;
    String shopName;
    
    Long staffId;
    String staffCode;
    String staffName;
    
    Long ownerId;
    String ownerCode;
    String ownerName;
    
    Long accountId;
    Long balanceId;
    Long balanceType;
    Long status;
   
    String currency;
    Date startDate;
    Date endDate;
    
    Date depositCreateDate;
//    Double depositAmount;
    String depositAmount;
    Long depositReasonId;
    String depositReceiptCode;
    
    Long receiptId;
    
    Double realBalance;
    Double realDebit;
    Double realCredit;
    Double currentDebit;
    Double limitDebit;    
    String realBalanceDisplay;
    String realDebitDisplay;
    String realCreditDisplay;    
    String currentDebitDisplay;
    String limitDebitDisplay;

    public AccountBalanceForm() {
    }

    
    public Double getCurrentDebit() {
        return currentDebit;
    }

    public void setCurrentDebit(Double currentDebit) {
        this.currentDebit = currentDebit;
    }

    public String getCurrentDebitDisplay() {
        return currentDebitDisplay;
    }

    public void setCurrentDebitDisplay(String currentDebitDisplay) {
        this.currentDebitDisplay = currentDebitDisplay;
    }

    public Double getLimitDebit() {
        return limitDebit;
    }

    public void setLimitDebit(Double limitDebit) {
        this.limitDebit = limitDebit;
    }

    public String getLimitDebitDisplay() {
        return limitDebitDisplay;
    }

    public void setLimitDebitDisplay(String limitDebitDisplay) {
        this.limitDebitDisplay = limitDebitDisplay;
    }

    
    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

    
    
    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
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

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    
    
    
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    
    
    public String getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(String depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Date getDepositCreateDate() {
        return depositCreateDate;
    }

    public void setDepositCreateDate(Date depositCreateDate) {
        this.depositCreateDate = depositCreateDate;
    }

    public Long getDepositReasonId() {
        return depositReasonId;
    }

    public void setDepositReasonId(Long depositReasonId) {
        this.depositReasonId = depositReasonId;
    }

    public String getDepositReceiptCode() {
        return depositReceiptCode;
    }

    public void setDepositReceiptCode(String depositReceiptCode) {
        this.depositReceiptCode = depositReceiptCode;
    }
    
    
    
    public Long getBalanceId() {
        return balanceId;
    }

    public void setBalance(String balanceId){
        this.balanceId = Long.valueOf(balanceId);
    }
    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
    }

    public Long getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(Long balanceType) {
        this.balanceType = balanceType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getRealBalance() {
        return realBalance;
    }

    public void setRealBalance(Double realBalance) {
        this.realBalance = realBalance;
    }

    public Double getRealCredit() {
        return realCredit;
    }

    public void setRealCredit(Double realCredit) {
        this.realCredit = realCredit;
    }

    public Double getRealDebit() {
        return realDebit;
    }

    public void setRealDebit(Double realDebit) {
        this.realDebit = realDebit;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getRealBalanceDisplay() {
        return realBalanceDisplay;
    }

    public void setRealBalanceDisplay(String realBalanceDisplay) {
        this.realBalanceDisplay = realBalanceDisplay;
    }

    public String getRealCreditDisplay() {
        return realCreditDisplay;
    }

    public void setRealCreditDisplay(String realCreditDisplay) {
        this.realCreditDisplay = realCreditDisplay;
    }

    public String getRealDebitDisplay() {
        return realDebitDisplay;
    }

    public void setRealDebitDisplay(String realDebitDisplay) {
        this.realDebitDisplay = realDebitDisplay;
    }
    
    

}
