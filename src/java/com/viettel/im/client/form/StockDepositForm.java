/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.viettel.im.database.BO.StockDeposit;
import java.util.Date;

/**
 *
 * @author Doan Thanh 8
 */
public class StockDepositForm {

    private Long stockDepositId;
    private Long stockModelId;
    private Long chanelTypeId;
    private Long maxStock;
    private Long status;
    private Date dateFrom;
    private Date dateTo;
    private Long transType;
    //loint
    private String stockModelName;
    private String stockModelCode;

    public String getStockModelCode() {
        return stockModelCode;
    }

    public void setStockModelCode(String stockModelCode) {
        this.stockModelCode = stockModelCode;
    }

    public String getStockModelName() {
        return stockModelName;
    }

    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }


    public StockDepositForm() {
        resetForm();
    }

    public void resetForm() {
        stockDepositId = 0L;
        transType = null;
        stockModelId = 0L;
        chanelTypeId = 0L;
        maxStock = null;
        status = 1L;
        dateFrom = new Date();
        dateTo = null;
    }

    public void copyDataToBO(StockDeposit stockDeposit) {
        stockDeposit.setStockDepositId(this.getStockDepositId());
        stockDeposit.setStockModelId(this.getStockModelId());
        stockDeposit.setChanelTypeId(this.getChanelTypeId());
        stockDeposit.setMaxStock(this.getMaxStock());
        stockDeposit.setStatus(this.getStatus());
        stockDeposit.setDateFrom(this.getDateFrom());
        stockDeposit.setDateTo(this.getDateTo());
        stockDeposit.setTransType(this.getTransType());
    }

    public void copyDataFromBO(StockDeposit stockDeposit) {
        this.setStockDepositId(stockDeposit.getStockDepositId());
        this.setStockModelId(stockDeposit.getStockModelId());
        this.setChanelTypeId(stockDeposit.getChanelTypeId());
        this.setMaxStock(stockDeposit.getMaxStock());
        this.setStatus(stockDeposit.getStatus());
        this.setDateFrom(stockDeposit.getDateFrom());
        this.setDateTo(stockDeposit.getDateTo());
        this.setTransType(stockDeposit.getTransType());
    }

    public Long getChanelTypeId() {
        return chanelTypeId;
    }

    public void setChanelTypeId(Long chanelTypeId) {
        this.chanelTypeId = chanelTypeId;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Long getMaxStock() {
        return maxStock;
    }

    public void setMaxStock(Long maxStock) {
        this.maxStock = maxStock;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getStockDepositId() {
        return stockDepositId;
    }

    public void setStockDepositId(Long stockDepositId) {
        this.stockDepositId = stockDepositId;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public Long getTransType() {
        return transType;
    }

    public void setTransType(Long transType) {
        this.transType = transType;
    }
}
