package com.viettel.im.database.BO;

import java.util.Date;

/**
 * StockDeposit entity.
 *
 * @author MyEclipse Persistence Tools
 */
public class StockDeposit implements java.io.Serializable {

    // Fields
    private Long stockDepositId;
    private Long stockModelId;
    private Long chanelTypeId;
    private Long maxStock;
    private Long status;
    private Date dateFrom;
    private Date dateTo;
    private Long transType;
    //
    private String chanelTypeName;
    private String stransTypeName;

    public String getChanelTypeName() {
        return chanelTypeName;
    }

    public void setChanelTypeName(String chanelTypeName) {
        this.chanelTypeName = chanelTypeName;
    }

    public String getStransTypeName() {
        return stransTypeName;
    }

    public void setStransTypeName(String stransTypeName) {
        this.stransTypeName = stransTypeName;
    }

    // Constructors
    /** default constructor */
    public StockDeposit() {
    }

    /** minimal constructor */
    public StockDeposit(Long stockDepositId, Long stockModelId,
            Long chanelTypeId) {
        this.stockDepositId = stockDepositId;
        this.stockModelId = stockModelId;
        this.chanelTypeId = chanelTypeId;
    }

    /** full constructor */
    public StockDeposit(Long stockDepositId, Long stockModelId,
            Long chanelTypeId, Long maxStock, Long status, Date dateFrom,
            Date dateTo) {
        this.stockDepositId = stockDepositId;
        this.stockModelId = stockModelId;
        this.chanelTypeId = chanelTypeId;
        this.maxStock = maxStock;
        this.status = status;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public StockDeposit(Long stockDepositId, Long stockModelId, Long chanelTypeId, Long maxStock, Long status, Date dateFrom, Date dateTo, String chanelTypeName) {
        this.stockDepositId = stockDepositId;
        this.stockModelId = stockModelId;
        this.chanelTypeId = chanelTypeId;
        this.maxStock = maxStock;
        this.status = status;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.chanelTypeName = chanelTypeName;
    }

    public StockDeposit(Long stockDepositId, Long stockModelId, Long chanelTypeId, Long maxStock, Long status, Date dateFrom, Date dateTo, String chanelTypeName, Long transType) {
        this.stockDepositId = stockDepositId;
        this.stockModelId = stockModelId;
        this.chanelTypeId = chanelTypeId;
        this.maxStock = maxStock;
        this.status = status;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.chanelTypeName = chanelTypeName;
        this.transType = transType;
    }

    // Property accessors
    public Long getStockDepositId() {
        return this.stockDepositId;
    }

    public void setStockDepositId(Long stockDepositId) {
        this.stockDepositId = stockDepositId;
    }

    public Long getStockModelId() {
        return this.stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public Long getChanelTypeId() {
        return this.chanelTypeId;
    }

    public void setChanelTypeId(Long chanelTypeId) {
        this.chanelTypeId = chanelTypeId;
    }

    public Long getMaxStock() {
        return this.maxStock;
    }

    public void setMaxStock(Long maxStock) {
        this.maxStock = maxStock;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Date getDateFrom() {
        return this.dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return this.dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Long getTransType() {
        return transType;
    }

    public void setTransType(Long transType) {
        this.transType = transType;
    }
}
