/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

/**
 *
 * @author tronglv
 */
public class ReportBankReceiptInExForm {

    private String shopCode;
    private String shopName;
    
    private String fromDate;
    private String toDate;

    private Long reportType;
    private String status;

    private String bankCode;
    private String bankName;
    
    private String bankAccountGroupCode;
    private String bankAccountGroupName;

    public ReportBankReceiptInExForm() {
    }

    public String getBankAccountGroupCode() {
        return bankAccountGroupCode;
    }

    public void setBankAccountGroupCode(String bankAccountGroupCode) {
        this.bankAccountGroupCode = bankAccountGroupCode;
    }

    public String getBankAccountGroupName() {
        return bankAccountGroupName;
    }

    public void setBankAccountGroupName(String bankAccountGroupName) {
        this.bankAccountGroupName = bankAccountGroupName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public Long getReportType() {
        return reportType;
    }

    public void setReportType(Long reportType) {
        this.reportType = reportType;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }



}
