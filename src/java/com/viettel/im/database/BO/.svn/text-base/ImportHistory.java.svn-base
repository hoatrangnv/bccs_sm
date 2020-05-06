package com.viettel.im.database.BO;

import java.util.Date;

/**
 * InvoiceTransferLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class ImportHistory implements java.io.Serializable {

    // Fields
    private Long importHistoryId;
    private Long stockTypeId;
    private String stockStartIsdn;
    private String stockEndIsdn;
    private Date dateCreated;
    private StockType stockType;
    private String userCreated;
    private Long isdnQuantity;


    // Constructors
    /** default constructor */
    public ImportHistory() {
    }

    /** minimal constructor */
    public ImportHistory(Long importHistoryId) {
        this.importHistoryId = importHistoryId;
    }

    /** minimal constructor */
    public ImportHistory(Long importHistoryId, StockType stockType) {
        this.importHistoryId = importHistoryId;
        this.stockType = stockType;
    }

    /** full constructor */
    public ImportHistory(Long importHistoryId, StockType stockType,
            String stockStartIsdn, String stockEndIsdn, Date dateCreated) {
        this.importHistoryId = importHistoryId;
        this.stockType = stockType;
        this.stockStartIsdn = stockStartIsdn;
        this.stockEndIsdn = stockEndIsdn;
        this.dateCreated = dateCreated;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getImportHistoryId() {
        return importHistoryId;
    }

    public void setImportHistoryId(Long importHistoryId) {
        this.importHistoryId = importHistoryId;
    }

    public String getStockEndIsdn() {
        return stockEndIsdn;
    }

    public void setStockEndIsdn(String stockEndIsdn) {
        this.stockEndIsdn = stockEndIsdn;
    }

    public String getStockStartIsdn() {
        return stockStartIsdn;
    }

    public void setStockStartIsdn(String stockStartIsdn) {
        this.stockStartIsdn = stockStartIsdn;
    }

    public StockType getStockType() {
        return stockType;
    }

    public void setStockType(StockType stockType) {
        this.stockType = stockType;
    }

    public Long getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public Long getIsdnQuantity() {
        return isdnQuantity;
    }

    public void setIsdnQuantity(Long isdnQuantity) {
        this.isdnQuantity = isdnQuantity;
    }

    public String getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }

}