package com.viettel.im.client.form;

import org.apache.struts.action.ActionForm;

/**
 *
 * @author Doan Thanh 8
 */
public class BankReceiptAdjustmentForm extends ActionForm {
    // Fields
    private Double amount; //so tien can dieu chinh
    private Long adjustmentType; //loai dieu chinh
    private String content; //noi dung dieu chinh

    private Long bankReceiptExternalId;
    private Double currentAmount; //so tien hien tai cua GNT

    public void resetForm() {
        amount = null;
        adjustmentType = null;
        content = "";
        bankReceiptExternalId = 0L;
        currentAmount = null;
    }

    public Double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public Long getAdjustmentType() {
        return adjustmentType;
    }

    public void setAdjustmentType(Long adjustmentType) {
        this.adjustmentType = adjustmentType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    
}
