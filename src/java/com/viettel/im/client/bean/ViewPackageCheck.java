/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

/**
 *
 * @author User
 */
public class ViewPackageCheck {

    private Long stockModelId;
    private String stockModelCode;
    private Long amount;
    private Long quantity;
    private Long packageGoodsId;
    private Long packageGoodMapId;

    public ViewPackageCheck() {
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getStockModelCode() {
        return stockModelCode;
    }

    public void setStockModelCode(String stockModelCode) {
        this.stockModelCode = stockModelCode;
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

    public Long getPackageGoodMapId() {
        return packageGoodMapId;
    }

    public void setPackageGoodMapId(Long packageGoodMapId) {
        this.packageGoodMapId = packageGoodMapId;
    }

    public Long getPackageGoodsId() {
        return packageGoodsId;
    }

    public void setPackageGoodsId(Long packageGoodsId) {
        this.packageGoodsId = packageGoodsId;
    }
}
