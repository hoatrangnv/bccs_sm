/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.form;
import java.util.Date;
/**
 *
 * @author User
 */
public class ViewAccountAgentForm {
    private Long accountType;
    private String accountCode;
    private String accountName;
    private String ownerCode;
    private String ownerName;
    private Double amount;
    private Double amountBalance;
    private String amountText;
    private String amountBalanceText;

    public ViewAccountAgentForm() {
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Long getAccountType() {
        return accountType;
    }

    public void setAccountType(Long accountType) {
        this.accountType = accountType;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmountBalance() {
        return amountBalance;
    }

    public void setAmountBalance(Double amountBalance) {
        this.amountBalance = amountBalance;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getAmountBalanceText() {
        return amountBalanceText;
    }

    public void setAmountBalanceText(String amountBalanceText) {
        this.amountBalanceText = amountBalanceText;
    }

    public String getAmountText() {
        return amountText;
    }

    public void setAmountText(String amountText) {
        this.amountText = amountText;
    }


}
