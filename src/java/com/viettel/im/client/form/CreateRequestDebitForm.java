/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.form;

import java.util.Date;

/**
 *
 * @author ThinhPH2_S
 */
public class CreateRequestDebitForm {
    private String shopCode;
    private String shopName;
    private String staffCode;
    private String staffName;
    private String debitType;
    private String debitDayType;
    private String description;
    private String clientFileName;
    private String serverFileName;
    private String note;
    private byte[] fileContent;
    private String fileName;
    private String financeType;
    private Long requestDebitType;
    private Long stockTypeId;

    public String getFinanceType() {
        return financeType;
    }

    public void setFinanceType(String financeType) {
        this.financeType = financeType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getClientFileName() {
        return clientFileName;
    }

    public void setClientFileName(String clientFileName) {
        this.clientFileName = clientFileName;
    }

    public String getServerFileName() {
        return serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    public String getDebitDayType() {
        return debitDayType;
    }

    public void setDebitDayType(String debitDayType) {
        this.debitDayType = debitDayType;
    }
        
    public String getDebitType() {
        return debitType;
    }

    public void setDebitType(String debitType) {
        this.debitType = debitType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public Long getRequestDebitType() {
        return requestDebitType;
    }

    public void setRequestDebitType(Long requestDebitType) {
        this.requestDebitType = requestDebitType;
    }

    public void resetFormQuery(){
        this.shopCode = "";
        this.shopName = "";
        this.staffCode = "";
        this.staffName = "";
        this.debitType = "";
        this.debitDayType = "";
        this.description = "";
        this.financeType = "";
        this.stockTypeId = null;
        this.requestDebitType = null;
    }

    public void resetForm(){
        this.note = "";
        this.clientFileName = "";
        this.serverFileName = "";
    }
}
