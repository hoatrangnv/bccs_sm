package com.viettel.im.database.BO;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractBankReceiptInternal entity provides the base persistence definition
 * of the BankReceiptInternal entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class BankReceiptInternal implements
        java.io.Serializable {

    // Fields
    private Long bankReceiptInternalId;
    private Long bankAccountId;
    private Long shopId;
    private Long staffId;
    private Long objectId;
    private Long objectType;
    private Date bankReceiptDate;
    private String bankReceiptCode;
    private Double amountInFigure;
    private String amountInFigureString;
    private String content;
    private Long status;
    private String approvedUser;
    private Date destroyedDate;
    private String destroyer;
    private Date approvedDate;
    private String amountInWord;
    private Date createdDate;
    private String createdUser;
    private Date lastUpdatedDate;
    private String lastUpdatedUser;
    private Long createdUserId;
    private Long lastUpdatedUserId;
    private Long approvedId;
    private Long destroyerId;

    // Constructors
    /** default constructor */
    public BankReceiptInternal() {
    }

    /** minimal constructor */
    public BankReceiptInternal(Long bankReceiptInternalId,
            Date bankReceiptDate,
            String bankReceiptCode, Double amountInFigure, Long status) {
        this.bankReceiptInternalId = bankReceiptInternalId;

        this.bankReceiptDate = bankReceiptDate;
        this.bankReceiptCode = bankReceiptCode;
        this.amountInFigure = amountInFigure;
        this.status = status;
    }

    /** full constructor */
    public BankReceiptInternal(Long bankReceiptInternalId,
            Long staffId, Long objectId,
            Long objectType, Date bankReceiptDate, String bankReceiptCode,
            Double amountInFigure, String content, Long status, String approvedUser,
            Date destroyedDate, String destroyer, Date approvedDate,
            Date createdDate) {
        this.bankReceiptInternalId = bankReceiptInternalId;
        this.staffId = staffId;
        this.objectId = objectId;
        this.objectType = objectType;
        this.bankReceiptDate = bankReceiptDate;
        this.bankReceiptCode = bankReceiptCode;
        this.amountInFigure = amountInFigure;
        this.content = content;
        this.status = status;
        this.approvedUser = approvedUser;
        this.destroyedDate = destroyedDate;
        this.destroyer = destroyer;
        this.approvedDate = approvedDate;
        this.createdDate = createdDate;

    }
    
    public BankReceiptInternal(Long bankReceiptInternalId,
            Long staffId, Long objectId,
            Long objectType, Date bankReceiptDate, String bankReceiptCode,
            Double amountInFigure, String amountInFigureString, String content, Long status, String approvedUser,
            Date destroyedDate, String destroyer, Date approvedDate,
            Date createdDate) {
        this.bankReceiptInternalId = bankReceiptInternalId;
        this.staffId = staffId;
        this.objectId = objectId;
        this.objectType = objectType;
        this.bankReceiptDate = bankReceiptDate;
        this.bankReceiptCode = bankReceiptCode;
        this.amountInFigure = amountInFigure;
        this.amountInFigureString = amountInFigureString;
        this.content = content;
        this.status = status;
        this.approvedUser = approvedUser;
        this.destroyedDate = destroyedDate;
        this.destroyer = destroyer;
        this.approvedDate = approvedDate;
        this.createdDate = createdDate;

    }

    public Double getAmountInFigure() {
        return amountInFigure;
    }

    public void setAmountInFigure(Double amountInFigure) {
        this.amountInFigure = amountInFigure;
    }

    public String getAmountInWord() {
        return amountInWord;
    }

    public void setAmountInWord(String amountInWord) {
        this.amountInWord = amountInWord;
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

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
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

    public Long getBankReceiptInternalId() {
        return bankReceiptInternalId;
    }

    public void setBankReceiptInternalId(Long bankReceiptInternalId) {
        this.bankReceiptInternalId = bankReceiptInternalId;
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

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Long getObjectType() {
        return objectType;
    }

    public void setObjectType(Long objectType) {
        this.objectType = objectType;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
    
    
    // Property accessors

    public String getAmountInFigureString() {
        return amountInFigureString;
    }

    public void setAmountInFigureString(String amountInFigureString) {
        this.amountInFigureString = amountInFigureString;
    }
}
