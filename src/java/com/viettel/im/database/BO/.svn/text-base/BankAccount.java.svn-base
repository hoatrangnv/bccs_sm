package com.viettel.im.database.BO;

/**
 * BankAccount entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class BankAccount implements java.io.Serializable {

    // Fields
    private Long bankAccountId;
    private String bankCode;
    private String bankName;
    private String province;
    private Long status;
    private String accountNo;
    private Long bankAccountGroupId;
    private Long bankId;
    private String bankAccountGroupCode;
    private String bankAccountGroupName;

    // Constructors
    /** default constructor */
    public BankAccount() {
    }

    /** minimal constructor */
    public BankAccount(Long bankAccountId, Long status) {
        this.bankAccountId = bankAccountId;
        this.status = status;
    }

    /** full constructor */
    public BankAccount(Long bankAccountId, String bankCode, String bankName,
            String bankAddress, Long status, String fullName, String accountNo) {
        this.bankAccountId = bankAccountId;
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.status = status;
        this.accountNo = accountNo;
    }

    public BankAccount(Long bankAccountId, String bankCode, String bankName, String province, String accountNo,
            String bankAccountGroupCode, String bankAccountGroupName, Long status) {

        this.bankAccountId = bankAccountId;
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.province = province;
        this.accountNo = accountNo;
        this.bankAccountGroupCode = bankAccountGroupCode;
        this.bankAccountGroupName = bankAccountGroupName;
        this.status = status;

    }

    // Property accessors
    public Long getBankAccountId() {
        return this.bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public String getBankCode() {
        return this.bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getAccountNo() {
        return this.accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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
}