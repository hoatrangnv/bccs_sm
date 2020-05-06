/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;

/**
 *
 * @author MrSun
 */
public class ReceiptBillDetailBean {
    
    private String stockModelName;
    private String stockModelUnit;
    private Long quantity;
    private Long price;
    private Long amount;

    public ReceiptBillDetailBean() {
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getStockModelName() {
        return stockModelName;
    }

    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }

    public String getStockModelUnit() {
        return stockModelUnit;
    }

    public void setStockModelUnit(String stockModelUnit) {
        this.stockModelUnit = stockModelUnit;
    }


}
