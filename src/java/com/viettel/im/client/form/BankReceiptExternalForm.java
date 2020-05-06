/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.BankReceiptExternal;
import java.util.Date;
import org.apache.struts.action.ActionForm;

/**
 * Author: TheTM
 * Date created: Oct 28, 2010
 * Purpose:
 */
public class BankReceiptExternalForm extends ActionForm {

    private Long bankReceiptType;

    private Long bankReceiptExternalId;
    private Long bankAccountId;
    private Long shopId;
    private Date bankReceiptDate;
    private String bankReceiptCode;
    private String otherCode;
    private Double amount;
    private String content;
    private Long status;
    private String approver;
    private String approvedDate;
    private String createdDate;
    private Long fromInternalId;
    private String ownerCode;
    private String ownerName;
    private String bankCode;
    private String bankAccountGroupCode;
    private Date bankReceiptToDate;

    //tamdt1, them vao phuc vu form tim kiem, start
    private String shopCode;
    private String shopName;
    private Long bankId;
    private Long bankAccountGroupId;
    private Date bankReceiptDateFrom;
    private Date bankReceiptDateTo;
    private String bankName;
    private String bankAccountGroupName;
    private Double amountAfterAdjustment;
    private String accountNo;
    //tamdt1, them vao phuc vu form tim kiem, end

    //tamdt1, them vao phuc vu form doi soat, start
    private Long bankAccountIdInternal;
    private String accountNoInternal;
    private Long shopIdInternal;
    private String shopCodeInternal;
    private String shopNameInternal;
    private Double amountInternal;
    private Date bankReceiptDateInternal;
    private String bankReceiptCodeInternal;
    private String[] selectedFormId;
    private Long formId;
    private Boolean includeApproveRecord;
    private Boolean includeInapproveRecord;
    //tamdt1, them vao phuc vu form doi soat, end

    public Long getBankReceiptType() {
        return bankReceiptType;
    }

    public void setBankReceiptType(Long bankReceiptType) {
        this.bankReceiptType = bankReceiptType;
    }

    public BankReceiptExternalForm() {
    }

    public BankReceiptExternalForm(Long bankReceiptExternalId, String shopCode, String shopName, String bankName, String bankAccountGroupName, String bankReceiptCode, Date bankReceiptDate, Double amount, Double amountAfterAdjustment, String content, Long status) {
        this.bankReceiptExternalId = bankReceiptExternalId;
        this.shopCode = shopCode;
        this.shopName = shopName;
        this.bankName = bankName;
        this.bankAccountGroupName = bankAccountGroupName;
        this.bankReceiptCode = bankReceiptCode;
        this.bankReceiptDate = bankReceiptDate;
        this.amount = amount;
        this.amountAfterAdjustment = amountAfterAdjustment;
        this.content = content;
        this.status = status;
    }

    public BankReceiptExternalForm(Long bankReceiptExternalId, Long shopId, String shopCode, String shopName, Long bankAccountId, String accountNo, String bankReceiptCode, Date bankReceiptDate, Double amountAfterAdjustment, String content) {
        this.bankReceiptExternalId = bankReceiptExternalId;
        this.shopId = shopId;
        this.shopCode = shopCode;
        this.shopName = shopName;
        this.bankAccountId = bankAccountId;
        this.accountNo = accountNo;
        this.bankReceiptCode = bankReceiptCode;
        this.bankReceiptDate = bankReceiptDate;
        this.amountAfterAdjustment = amountAfterAdjustment;
        this.content = content;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
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

    public String getBankAccountGroupName() {
        return bankAccountGroupName;
    }

    public void setBankAccountGroupName(String bankAccountGroupName) {
        this.bankAccountGroupName = bankAccountGroupName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Long getFromInternalId() {
        return fromInternalId;
    }

    public void setFromInternalId(Long fromInternalId) {
        this.fromInternalId = fromInternalId;
    }

    public String getOtherCode() {
        return otherCode;
    }

    public void setOtherCode(String otherCode) {
        this.otherCode = otherCode;
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

    public Date getBankReceiptDateFrom() {
        return bankReceiptDateFrom;
    }

    public void setBankReceiptDateFrom(Date bankReceiptDateFrom) {
        this.bankReceiptDateFrom = bankReceiptDateFrom;
    }

    public Date getBankReceiptDateTo() {
        return bankReceiptDateTo;
    }

    public void setBankReceiptDateTo(Date bankReceiptDateTo) {
        this.bankReceiptDateTo = bankReceiptDateTo;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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

    public Double getAmountAfterAdjustment() {
        return amountAfterAdjustment;
    }

    public void setAmountAfterAdjustment(Double amountAfterAdjustment) {
        this.amountAfterAdjustment = amountAfterAdjustment;
    }

    public Date getBankReceiptToDate() {
        return bankReceiptToDate;
    }

    public void setBankReceiptToDate(Date bankReceiptToDate) {
        this.bankReceiptToDate = bankReceiptToDate;
    }


    public void resetForm() {
        this.bankReceiptExternalId = 0L;
        this.bankAccountId = null;
        this.bankReceiptDate = null;
        this.bankReceiptCode = "";
        this.amount = null;
        this.content = "";
        this.status = null;
        this.ownerCode = "";
        this.ownerName = "";
        this.bankCode = "";
        this.bankName = "";
        this.bankAccountGroupCode = "";
        this.bankAccountGroupName = "";
        this.bankReceiptToDate = null;
        try {
            String strNow = DateTimeUtils.convertDateToString(new Date());
            Date now = DateTimeUtils.convertStringToDate(strNow);
            Date beforeNowADay = DateTimeUtils.addDate(now, -1); //lui lai 1 ngay
            this.bankReceiptDateFrom = beforeNowADay;
            this.bankReceiptDateTo = beforeNowADay;
        } catch (Exception ex) {
            
        }
        this.includeApproveRecord = true;
        this.includeInapproveRecord = false;
    }

    public void copyDataToBO(BankReceiptExternal bankReceiptExternal) throws Exception {
        bankReceiptExternal.setBankReceiptExternalId(this.bankReceiptExternalId);
        bankReceiptExternal.setBankReceiptDate(this.bankReceiptDate);
        bankReceiptExternal.setBankReceiptCode(this.bankReceiptCode);
        bankReceiptExternal.setAmount(this.amount);
        bankReceiptExternal.setContent(this.content);
        bankReceiptExternal.setStatus(this.status);
        bankReceiptExternal.setAmountAfterAdjustment(this.amount); 
    }

    public void copyDataFromBO(BankReceiptExternal bankReceiptExternal) throws Exception {
        this.bankReceiptExternalId = bankReceiptExternal.getBankReceiptExternalId();
        this.bankAccountId = bankReceiptExternal.getBankAccountId();
        this.bankReceiptDate = bankReceiptExternal.getBankReceiptDate();
        this.bankReceiptCode = bankReceiptExternal.getBankReceiptCode();
        this.amount = bankReceiptExternal.getAmount();
        this.content = bankReceiptExternal.getContent();
        this.status = bankReceiptExternal.getStatus();
    }

    public Double getAmountInternal() {
        return amountInternal;
    }

    public void setAmountInternal(Double amountInternal) {
        this.amountInternal = amountInternal;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountNoInternal() {
        return accountNoInternal;
    }

    public void setAccountNoInternal(String accountNoInternal) {
        this.accountNoInternal = accountNoInternal;
    }

    public String getShopCodeInternal() {
        return shopCodeInternal;
    }

    public void setShopCodeInternal(String shopCodeInternal) {
        this.shopCodeInternal = shopCodeInternal;
    }

    public Long getShopIdInternal() {
        return shopIdInternal;
    }

    public void setShopIdInternal(Long shopIdInternal) {
        this.shopIdInternal = shopIdInternal;
    }

    public String getShopNameInternal() {
        return shopNameInternal;
    }

    public void setShopNameInternal(String shopNameInternal) {
        this.shopNameInternal = shopNameInternal;
    }

    public Long getBankAccountIdInternal() {
        return bankAccountIdInternal;
    }

    public void setBankAccountIdInternal(Long bankAccountIdInternal) {
        this.bankAccountIdInternal = bankAccountIdInternal;
    }

    public String getBankReceiptCodeInternal() {
        return bankReceiptCodeInternal;
    }

    public void setBankReceiptCodeInternal(String bankReceiptCodeInternal) {
        this.bankReceiptCodeInternal = bankReceiptCodeInternal;
    }

    public Date getBankReceiptDateInternal() {
        return bankReceiptDateInternal;
    }

    public void setBankReceiptDateInternal(Date bankReceiptDateInternal) {
        this.bankReceiptDateInternal = bankReceiptDateInternal;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public String[] getSelectedFormId() {
        return selectedFormId;
    }

    public void setSelectedFormId(String[] selectedFormId) {
        this.selectedFormId = selectedFormId;
    }

    public Boolean getIncludeApproveRecord() {
        return includeApproveRecord;
    }

    public void setIncludeApproveRecord(Boolean includeApproveRecord) {
        this.includeApproveRecord = includeApproveRecord;
    }

    public Boolean getIncludeInapproveRecord() {
        return includeInapproveRecord;
    }

    public void setIncludeInapproveRecord(Boolean includeInapproveRecord) {
        this.includeInapproveRecord = includeInapproveRecord;
    }

}
