package com.viettel.im.database.BO;

import java.util.Date;

/**
 * AbstractBankReceiptTransDetail entity provides the base persistence
 * definition of the BankReceiptTransDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class BankReceiptTransDetail implements
        java.io.Serializable {

    // Fields
    private Long transDetailId;
    private Long transId;
    private Date transDate;
    private Long bankAccountId;
    private Long shopId;
    private String bankReceiptDate;
    private String bankReceiptCode;
    private String otherCode;
    private String content;
    private Long amount;
    private Date createDate;

    // Constructors
    /** default constructor */
    public BankReceiptTransDetail() {
    }

    /** minimal constructor */
    public BankReceiptTransDetail(Long transDetailId,
            Date transDate,
            Long bankAccountId, Long shopId, String bankReceiptDate, Long amount) {
        this.transDetailId = transDetailId;
        this.transDate = transDate;
        this.bankAccountId = bankAccountId;
        this.shopId = shopId;
        this.bankReceiptDate = bankReceiptDate;
        this.amount = amount;
    }

    /** full constructor */
    public BankReceiptTransDetail(Long transDetailId,
            Date transDate,
            Long bankAccountId, Long shopId, String bankReceiptDate,
            String bankReceiptCode, String otherCode, String content,
            Long amount, Date createDate) {
        this.transDetailId = transDetailId;
        this.transDate = transDate;
        this.bankAccountId = bankAccountId;
        this.shopId = shopId;
        this.bankReceiptDate = bankReceiptDate;
        this.bankReceiptCode = bankReceiptCode;
        this.otherCode = otherCode;
        this.content = content;
        this.amount = amount;
        this.createDate = createDate;
    }

    // Property accessors
    public Long getTransDetailId() {
        return this.transDetailId;
    }

    public void setTransDetailId(Long transDetailId) {
        this.transDetailId = transDetailId;
    }

    public Date getTransDate() {
        return this.transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public Long getBankAccountId() {
        return this.bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public Long getShopId() {
        return this.shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getBankReceiptDate() {
        return this.bankReceiptDate;
    }

    public void setBankReceiptDate(String bankReceiptDate) {
        this.bankReceiptDate = bankReceiptDate;
    }

    public String getBankReceiptCode() {
        return this.bankReceiptCode;
    }

    public void setBankReceiptCode(String bankReceiptCode) {
        this.bankReceiptCode = bankReceiptCode;
    }

    public String getOtherCode() {
        return this.otherCode;
    }

    public void setOtherCode(String otherCode) {
        this.otherCode = otherCode;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAmount() {
        return this.amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getTransId() {
        return transId;
    }

    public void setTransId(Long transId) {
        this.transId = transId;
    }

}