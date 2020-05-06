/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.BO;

/**
 *
 * @author kdvt_thaiph1
 */
public class CustumerAccount {
    private String accountName;
    private Long moneyTotal;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Long getMoneyTotal() {
        return moneyTotal;
    }

    public void setMoneyTotal(Long moneyTotal) {
        this.moneyTotal = moneyTotal;
    }
    
}
