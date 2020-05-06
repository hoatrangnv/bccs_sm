/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.common.util;

/**
 *
 * @author mov_itbl_dinhdc
 */
public class CancelPaymentEMolaSaleTrans {
    
    private String mobile;
    private String amount;
    private String transID;

    public CancelPaymentEMolaSaleTrans() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransID() {
        return transID;
    }

    public void setTransID(String transID) {
        this.transID = transID;
    }
    
}
