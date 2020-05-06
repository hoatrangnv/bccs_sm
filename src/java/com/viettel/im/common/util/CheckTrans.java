/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.common.util;

/**
 *
 * @author User
 */
public class CheckTrans {
    
    private String transCode;
    private String bankName;
    private String amount;
    private String userName;
    private String passWord;

    public CheckTrans() {
    }

    public CheckTrans(String transCode, String bankName, String amount, String userName, String passWord) {
        this.transCode = transCode;
        this.bankName = bankName;
        this.amount = amount;
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    
}
