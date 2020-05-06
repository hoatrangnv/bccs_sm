package com.viettel.im.database.BO;

import java.util.Date;

/**
 * AbstractBankReceiptExternal entity provides the base persistence definition
 * of the BankReceiptExternal entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class BankReceiptExternal implements
        java.io.Serializable {

    // Fields
    private Long bankReceiptExternalId;
    private Long bankAccountId;
    private Long fromInternalId;
    private Long shopId;
    private Date bankReceiptDate;
    private String bankReceiptCode;
    private String otherCode;
    private Double amount;
    private String content;
    private Long status;
    private Long approvedId;
    private String approvedUser;
    private Date approvedDate;
    private Double amountAfterAdjustment;
    private Date createdDate;
    private String createdUser;
    private Date lastUpdatedDate;
    private String lastUpdatedUser;
    private Long createdUserId;
    private Long lastUpdatedUserId;
    private Long transId;
    private Date destroyedDate;
    private String destroyer;
    private Long destroyerId;
    private String ownerCode;
    private String ownerName;
    private String bankCode;
    private String bankName;
    private String bankAccountGroupCode;
    private String bankAccountGroupName;
    private String bankAccountNo;
    // Constructors

    /** default constructor */
    public BankReceiptExternal() {
    }

    /** minimal constructor */
    public BankReceiptExternal(Long bankReceiptExternalId,
            Date bankReceiptDate,
            String bankReceiptCode, Double amount, Long status) {
        this.bankReceiptExternalId = bankReceiptExternalId;
        this.bankReceiptDate = bankReceiptDate;
        this.bankReceiptCode = bankReceiptCode;
        this.amount = amount;
        this.status = status;
    }

    /** full constructor */
    public BankReceiptExternal(Long bankReceiptExternalId,
            Date bankReceiptDate, String bankReceiptCode,
            String otherCode, Double amount, String content, Long status,
            String approvedUser, Date approvedDate, Date createdDate) {
        this.bankReceiptExternalId = bankReceiptExternalId;
        this.bankReceiptDate = bankReceiptDate;
        this.bankReceiptCode = bankReceiptCode;
        this.otherCode = otherCode;
        this.amount = amount;
        this.content = content;
        this.status = status;
        this.approvedUser = approvedUser;
        this.approvedDate = approvedDate;
        this.createdDate = createdDate;
    }

    public BankReceiptExternal(Long bankReceiptExternalId, String ownerCode, String ownerName,
            String bankCode, String bankName, String bankAccountGroupCode, String bankAccountGroupName,
            String bankReceiptCode, Double amount, String content, Long status, Date bankReceiptDate) {
        this.bankReceiptExternalId = bankReceiptExternalId;
        this.ownerCode = ownerCode;
        this.ownerName = ownerName;
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.bankAccountGroupCode = bankAccountGroupCode;
        this.bankAccountGroupName = bankAccountGroupName;
        this.bankReceiptCode = bankReceiptCode;
        this.amount = amount;
        this.content = content;
        this.status = status;
        this.bankReceiptDate = bankReceiptDate;
    }

    /**
     * @Author TrongLV
     * @Purpose Khoi tao doi tuong theo thong tin GNT import he thong
     * @param bankReceiptDate
     * @param bankReceiptCode
     * @param otherCode
     * @param amount
     * @param content
     * @param bankCode
     * @param bankName
     * @param bankAccountGroupCode
     * @param bankAccountGroupName
     */
    public BankReceiptExternal(Date bankReceiptDate, String bankReceiptCode, String otherCode, Double amount, String content
            , String bankCode, String bankName
            , String bankAccountGroupCode, String bankAccountGroupName, String bankAccountNo
            , String ownerCode, String ownerName) {
        this.bankReceiptDate = bankReceiptDate;
        this.bankReceiptCode = bankReceiptCode;
        this.otherCode = otherCode;
        this.amount = amount;
        this.content = content;
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.bankAccountGroupCode = bankAccountGroupCode;
        this.bankAccountGroupName = bankAccountGroupName;
        this.bankAccountNo = bankAccountNo;
        this.ownerCode = ownerCode;
        this.ownerName = ownerName;
    }




    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmountAfterAdjustment() {
        return amountAfterAdjustment;
    }

    public void setAmountAfterAdjustment(Double amountAfterAdjustment) {
        this.amountAfterAdjustment = amountAfterAdjustment;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Long getApprovedId() {
        return approvedId;
    }

    public void setApprovedId(Long approvedId) {
        this.approvedId = approvedId;
    }

    public String getApprovedUser() {
        return approvedUser;
    }

    public void setApprovedUser(String approvedUser) {
        this.approvedUser = approvedUser;
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
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankReceiptCode() {
        return bankReceiptCode;
    }

    public void setBankReceiptCode(String bankReceiptCode) {
        this.bankReceiptCode = bankReceiptCode;
    }

    public Date getBankReceiptDate() {
        return bankReceiptDate;
    }

    public void setBankReceiptDate(Date bankReceiptDate) {
        this.bankReceiptDate = bankReceiptDate;
    }

    public Long getBankReceiptExternalId() {
        return bankReceiptExternalId;
    }

    public void setBankReceiptExternalId(Long bankReceiptExternalId) {
        this.bankReceiptExternalId = bankReceiptExternalId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Long getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Long createdUserId) {
        this.createdUserId = createdUserId;
    }

    public Date getDestroyedDate() {
        return destroyedDate;
    }

    public void setDestroyedDate(Date destroyedDate) {
        this.destroyedDate = destroyedDate;
    }

    public String getDestroyer() {
        return destroyer;
    }

    public void setDestroyer(String destroyer) {
        this.destroyer = destroyer;
    }

    public Long getDestroyerId() {
        return destroyerId;
    }

    public void setDestroyerId(Long destroyerId) {
        this.destroyerId = destroyerId;
    }

    public Long getFromInternalId() {
        return fromInternalId;
    }

    public void setFromInternalId(Long fromInternalId) {
        this.fromInternalId = fromInternalId;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public Long getLastUpdatedUserId() {
        return lastUpdatedUserId;
    }

    public void setLastUpdatedUserId(Long lastUpdatedUserId) {
        this.lastUpdatedUserId = lastUpdatedUserId;
    }

    public String getOtherCode() {
        return otherCode;
    }

    public void setOtherCode(String otherCode) {
        this.otherCode = otherCode;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getTransId() {
        return transId;
    }

    public void setTransId(Long transId) {
        this.transId = transId;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }


    // Property accessors
}
