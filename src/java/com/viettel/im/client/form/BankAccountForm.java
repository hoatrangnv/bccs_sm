/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.viettel.im.database.BO.BankAccount;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author NamNX
 */
public class BankAccountForm extends ActionForm {

    private Long bankAccountId;
    private Long bankId;
    private String bankCode;
    private String bankName;
    private Long status;
    private String accountNo;
    private Long bankAccountGroupId;
    private String bankAccountGroupCode;
    private String bankAccountGroupName;

    public Long getBankAccountGroupId() {
        return bankAccountGroupId;
    }

    public void setBankAccountGroupId(Long bankAccountGroupId) {
        this.bankAccountGroupId = bankAccountGroupId;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo.trim();
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName.trim();
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getBankAccountGroupCode() {
        return bankAccountGroupCode;
    }

    public void setBankAccountGroupCode(String bankAccountGroupCode) {
        this.bankAccountGroupCode = bankAccountGroupCode;
    }

    public String getBankAccountGroupName() {
        return bankAccountGroupName;
    }

    public void setBankAccountGroupName(String bankAccountGroupName) {
        this.bankAccountGroupName = bankAccountGroupName;
    }

    public void resetForm() {
        this.bankAccountId = 0L;
        this.accountNo = "";
        this.bankCode = "";
        this.status = null;
        this.bankName = "";
        this.bankAccountGroupCode = "";
        this.bankAccountGroupName = "";
    }

    public void copyDataToBO(BankAccount bankAccount) {
        bankAccount.setBankAccountId(this.bankAccountId);
        bankAccount.setBankId(this.bankId);
        bankAccount.setBankAccountGroupId(this.bankAccountGroupId);
        bankAccount.setAccountNo(this.accountNo);
        bankAccount.setStatus(this.status);
    }

    public void copyDataFromBO(BankAccount bankAccount) {
        this.bankAccountId = bankAccount.getBankAccountId();
        this.bankId = bankAccount.getBankId();
        this.bankAccountGroupId = bankAccount.getBankAccountGroupId();
        this.accountNo = bankAccount.getAccountNo();
        this.status = bankAccount.getStatus();
    }
}
