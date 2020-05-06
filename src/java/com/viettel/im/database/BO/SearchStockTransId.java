package com.viettel.im.database.BO;

/**
 * SearchStockTrans entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class SearchStockTransId implements java.io.Serializable {

    // Fields
    private Long stockTransId;
    private String actionCode;
    private Long actionId;
    // Constructors

    /** default constructor */
    public SearchStockTransId() {
    }

    public SearchStockTransId(Long stockTransId, String actionCode, Long actionId) {
        this.stockTransId = stockTransId;
        this.actionCode = actionCode;
        this.actionId = actionId;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public Long getStockTransId() {
        return stockTransId;
    }

    public void setStockTransId(Long stockTransId) {
        this.stockTransId = stockTransId;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }
}