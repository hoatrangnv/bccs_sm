/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.BO;

/**
 *
 * @author dev_linh
 */
public class DebitUser {

    private String branch;
    private String name;
    private String debitUser;
    private String employeeCode;
    private String isdn;
    private long debitOpening;
    private long debitEnding;
    private long sequence;
    private long currencyLimit;

    public long getCurrencyLimit() {
        return currencyLimit;
    }

    public void setCurrencyLimit(long currencyLimit) {
        this.currencyLimit = currencyLimit;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDebitUser() {
        return debitUser;
    }

    public void setDebitUser(String debitUser) {
        this.debitUser = debitUser;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public long getDebitOpening() {
        return debitOpening;
    }

    public void setDebitOpening(long debitOpening) {
        this.debitOpening = debitOpening;
    }

    public long getDebitEnding() {
        return debitEnding;
    }

    public void setDebitEnding(long debitEnding) {
        this.debitEnding = debitEnding;
    }
}
