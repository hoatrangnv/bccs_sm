/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.BO;

/**
 *
 * @author thinhph2
 */
public class DebitStock {
    private Long stockTypeId;
    private Long requestDebitType;
    private Double debitValue;
    
    private Long stockModelId;
    private Long quantity;
    
    private Long flag;

    public Long getFlag() {
        return flag;
    }

    public void setFlag(Long flag) {
        this.flag = flag;
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

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getDebitValue() {
        return debitValue;
    }

    public void setDebitValue(Double debitValue) {
        this.debitValue = debitValue;
    }
    
}
