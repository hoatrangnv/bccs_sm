/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;

import java.io.Serializable;

/**
 *
 * @author haidd
 */
public class ReceiptExpenseBean implements Serializable{
    private static final long serialVersionUID = 1L;

    private String stockModelName = null;
    private String stockTransId = null;
    private String stockModelId = null;
    private String totalReceipt = null;
    private String totalPay = null;
    private String totalMoneyReceipt = null;
    private String depositId = null;
    private String depositType = null;
    private String quantity = null;
    private Double amount = null;
    private String deduct = null;

    public String getDeduct() {
        return deduct;
    }

    public void setDeduct(String deduct) {
        this.deduct = deduct;
    }


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDepositId() {
        return depositId;
    }

    public void setDepositId(String depositId) {
        this.depositId = depositId;
    }

    public String getDepositType() {
        return depositType;
    }

    public void setDepositType(String depositType) {
        this.depositType = depositType;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    

    public String getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(String stockModelId) {
        this.stockModelId = stockModelId;
    }

    public String getStockModelName() {
        return stockModelName;
    }

    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }

    public String getStockTransId() {
        return stockTransId;
    }

    public void setStockTransId(String stockTransId) {
        this.stockTransId = stockTransId;
    }

    public String getTotalMoneyReceipt() {
        return totalMoneyReceipt;
    }

    public void setTotalMoneyReceipt(String totalMoneyReceipt) {
        this.totalMoneyReceipt = totalMoneyReceipt;
    }

    public String getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(String totalPay) {
        this.totalPay = totalPay;
    }

    public String getTotalReceipt() {
        return totalReceipt;
    }

    public void setTotalReceipt(String totalReceipt) {
        this.totalReceipt = totalReceipt;
    }

    


}
