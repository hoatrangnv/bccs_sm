package com.viettel.im.client.form;

import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.BankReceiptInternal;
import java.util.Date;
import org.apache.struts.action.ActionForm;

/**
 *
 * author   : Doan Thanh 8
 * date     : 27/10/2010
 * desc     : giay nop tien (noi bo)
 *
 */
public class BankReceiptInternalForm extends ActionForm {

    private Long bankReceiptInternalId;
    private Long bankAccountId;
    private Long ownerType;
    private Long ownerId;
    private String ownerCode;
    private String ownerName;
    private Long bankId;
    private String bankCode;
    private String bankName;
    private Long bankAccountGroupId;
    private String bankAccountGroupCode;
    private String bankAccountGroupName;
    private String bankReceiptCode;
    private Date bankReceiptDate;
    private Double amountInFigure;
    private String amountInFigureString;
    private String content;
    private Long status;
    private Long createdUserId;
    private String createdUser;

    //
    private Long shopId;
    private String shopCode;
    private String shopName;
    private String accountNo;

    public BankReceiptInternalForm() {
    }

    public BankReceiptInternalForm(Long bankReceiptInternalId, String ownerCode, String ownerName, String bankName, String bankAccountGroupName, String bankReceiptCode, Date bankReceiptDate, Double amountInFigure, String content, Long status, Long createdUserId, String createdUser) {
        this.bankReceiptInternalId = bankReceiptInternalId;
        this.ownerCode = ownerCode;
        this.ownerName = ownerName;
        this.bankName = bankName;
        this.bankAccountGroupName = bankAccountGroupName;
        this.bankReceiptCode = bankReceiptCode;
        this.bankReceiptDate = bankReceiptDate;
        this.amountInFigure = amountInFigure;
        this.content = content;
        this.status = status;
        this.createdUserId = createdUserId;
        this.createdUser = createdUser;
    }
    
     public BankReceiptInternalForm(Long bankReceiptInternalId, String ownerCode, String ownerName, String bankName, String bankAccountGroupName, String bankReceiptCode, Date bankReceiptDate, Double amountInFigure, String amountInFigureString, String content, Long status, Long createdUserId, String createdUser) {
        this.bankReceiptInternalId = bankReceiptInternalId;
        this.ownerCode = ownerCode;
        this.ownerName = ownerName;
        this.bankName = bankName;
        this.bankAccountGroupName = bankAccountGroupName;
        this.bankReceiptCode = bankReceiptCode;
        this.bankReceiptDate = bankReceiptDate;
        this.amountInFigure = amountInFigure;
        this.amountInFigureString = amountInFigureString;
        this.content = content;
        this.status = status;
        this.createdUserId = createdUserId;
        this.createdUser = createdUser;
    }
     

    public BankReceiptInternalForm(Long bankReceiptInternalId, Long shopId, String shopCode, String shopName, Long bankAccountId, String accountNo, String bankReceiptCode, Date bankReceiptDate, Double amountInFigure, String content) {
        this.bankReceiptInternalId = bankReceiptInternalId;
        this.shopId = shopId;
        this.shopCode = shopCode;
        this.shopName = shopName;
        this.bankAccountId = bankAccountId;
        this.accountNo = accountNo;
        this.bankReceiptCode = bankReceiptCode;
        this.bankReceiptDate = bankReceiptDate;
        this.amountInFigure = amountInFigure;
        this.content = content;
    }
    
    public BankReceiptInternalForm(Long bankReceiptInternalId, Long shopId, String shopCode, String shopName, Long bankAccountId, String accountNo, String bankReceiptCode, Date bankReceiptDate, Double amountInFigure, String amountInFigureString, String content) {
        this.bankReceiptInternalId = bankReceiptInternalId;
        this.shopId = shopId;
        this.shopCode = shopCode;
        this.shopName = shopName;
        this.bankAccountId = bankAccountId;
        this.accountNo = accountNo;
        this.bankReceiptCode = bankReceiptCode;
        this.bankReceiptDate = bankReceiptDate;
        this.amountInFigure = amountInFigure;
        this.amountInFigureString = amountInFigureString;
        this.content = content;
    }

    public void resetForm() {
        bankReceiptInternalId = null;
        bankAccountId = null;
        ownerType = Constant.OWNER_TYPE_SHOP;
        ownerId = null;
        ownerCode = "";
        ownerName = "";
        bankId = null;
        bankCode = "";
        bankName = "";
        bankAccountGroupId = null;
        bankAccountGroupCode = "";
        bankAccountGroupName = "";
        bankReceiptCode = "";
        bankReceiptDate = null;
        amountInFigure = null;
        amountInFigureString = "";
        content = "";
    }

    public void copyDataFromBO(BankReceiptInternal bankReceiptInternal) {
        this.setBankReceiptInternalId(bankReceiptInternal.getBankReceiptInternalId());
        this.setBankAccountId(bankReceiptInternal.getBankAccountId());
        this.setOwnerType(bankReceiptInternal.getObjectType());
        this.setOwnerId(bankReceiptInternal.getObjectId());
        this.setBankReceiptCode(bankReceiptInternal.getBankReceiptCode());
        this.setBankReceiptDate(bankReceiptInternal.getBankReceiptDate());
        this.setAmountInFigure(bankReceiptInternal.getAmountInFigure());
        this.setAmountInFigureString(bankReceiptInternal.getAmountInFigureString());
        this.setContent(bankReceiptInternal.getContent());
    }

    public void copyDataToBO(BankReceiptInternal bankReceiptInternal) {
        bankReceiptInternal.setBankReceiptInternalId(this.getBankReceiptInternalId());
        bankReceiptInternal.setBankAccountId(this.getBankAccountId());
        bankReceiptInternal.setObjectType(this.getOwnerType());
        bankReceiptInternal.setObjectId(this.getOwnerId());
        bankReceiptInternal.setBankReceiptCode(this.getBankReceiptCode());
        bankReceiptInternal.setBankReceiptDate(this.getBankReceiptDate());
        bankReceiptInternal.setAmountInFigure(this.getAmountInFigure());
        bankReceiptInternal.setAmountInFigureString(this.getAmountInFigureString());
        bankReceiptInternal.setContent(this.getContent());
    }

    public Double getAmountInFigure() {
        return amountInFigure;
    }

    public void setAmountInFigure(Double amountInFigure) {
        this.amountInFigure = amountInFigure;
    }

    public Long getBankAccountGroupId() {
        return bankAccountGroupId;
    }

    public void setBankAccountGroupId(Long bankAccountGroupId) {
        this.bankAccountGroupId = bankAccountGroupId;
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

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
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

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(Long ownerType) {
        this.ownerType = ownerType;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBankAccountGroupCode() {
        return bankAccountGroupCode;
    }

    public void setBankAccountGroupCode(String bankAccountGroupCode) {
        this.bankAccountGroupCode = bankAccountGroupCode;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Long getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Long createdUserId) {
        this.createdUserId = createdUserId;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getAmountInFigureString() {
        return amountInFigureString;
    }

    public void setAmountInFigureString(String amountInFigureString) {
        this.amountInFigureString = amountInFigureString;
    }

    

}
