/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

/**
 *
 * @author User
 */
public class ChangeGoodBeanGenaral {

    public ChangeGoodBeanGenaral(Long stockModelId, String stockModelName, String typeName, Long TotalQuantity) {
        this.stockModelId = stockModelId;
        this.stockModelName = stockModelName;
        this.typeName = typeName;
        this.TotalQuantity = TotalQuantity;
    }
    public ChangeGoodBeanGenaral() {        
    }

    private Long stockModelId;
    private String stockModelName;
    private String typeName;
    private Long TotalQuantity;

    public Long getTotalQuantity() {
        return TotalQuantity;
    }

    public void setTotalQuantity(Long TotalQuantity) {
        this.TotalQuantity = TotalQuantity;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public String getStockModelName() {
        return stockModelName;
    }

    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
