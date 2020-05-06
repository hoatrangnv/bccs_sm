/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

import java.util.Date;

/**
 *
 * @author User
 */
public class ViewSerialHistoryBean {

    private Long stockTransId;
    private String stockTransType;
    private String exportStore;
    private String importStore;
    private String stockTransStatus;
    private String userSerial;
    private Date creatDate;

     public ViewSerialHistoryBean(){
         
     }
    public ViewSerialHistoryBean(Long stockTransId, String stockTransType, String exportStore, String importStore, String stockTransStatus, String userSerial, Date creatDate) {
        this.stockTransId = stockTransId;
        this.stockTransType = stockTransType;
        this.exportStore = exportStore;
        this.importStore = importStore;
        this.stockTransStatus = stockTransStatus;
        this.userSerial = userSerial;
        this.creatDate = creatDate;
    }

    public Date getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }

    public String getExportStore() {
        return exportStore;
    }

    public void setExportStore(String exportStore) {
        this.exportStore = exportStore;
    }

    public String getImportStore() {
        return importStore;
    }

    public void setImportStore(String importStore) {
        this.importStore = importStore;
    }

    public Long getStockTransId() {
        return stockTransId;
    }

    public void setStockTransId(Long stockTransId) {
        this.stockTransId = stockTransId;
    }

    public String getStockTransStatus() {
        return stockTransStatus;
    }

    public void setStockTransStatus(String stockTransStatus) {
        this.stockTransStatus = stockTransStatus;
    }

    public String getStockTransType() {
        return stockTransType;
    }

    public void setStockTransType(String stockTransType) {
        this.stockTransType = stockTransType;
    }

    public String getUserSerial() {
        return userSerial;
    }

    public void setUserSerial(String userSerial) {
        this.userSerial = userSerial;
    }

   
}
