package com.viettel.im.database.BO;

import java.util.Date;

/**
 * AbstractBankReceiptTransError entity provides the base persistence definition
 * of the BankReceiptTransError entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class BankReceiptTransError implements
        java.io.Serializable {

    // Fields
    private Long transErrorId;
    private Long transId;
    private Date transDate;
    private String bankReceiptDate;
    private String bankReceiptCode;
    private String otherCode;
    private String content;
    private String amount;
    private Date createdDate;//khong su dung
    private String errorCode;
    private String errorDes;
    private String bankAccountNo;

    // Constructors
    /** default constructor */
    public BankReceiptTransError() {
    }

    /** minimal constructor */
    public BankReceiptTransError(Long transErrorId,
            Date transDate, String errorCode) {
        this.transErrorId = transErrorId;
        this.transDate = transDate;
        this.errorCode = errorCode;
    }

    /** full constructor */
    public BankReceiptTransError(Long transErrorId,
            Date transDate,
            String bankReceiptDate, String bankReceiptCode, String otherCode,
            String content, String amount, Date createdDate, String errorCode,
            String bankAccountNo) {
        this.transErrorId = transErrorId;

        this.transDate = transDate;
        this.bankReceiptDate = bankReceiptDate;
        this.bankReceiptCode = bankReceiptCode;
        this.otherCode = otherCode;
        this.content = content;
        this.amount = amount;
        this.createdDate = createdDate;
        this.errorCode = errorCode;
        this.bankAccountNo = bankAccountNo;
    }

    // Property accessors
    public Long getTransErrorId() {
        return this.transErrorId;
    }

    public void setTransErrorId(Long transErrorId) {
        this.transErrorId = transErrorId;
    }

    public Long getTransId() {
        return transId;
    }

    public void setTransId(Long transId) {
        this.transId = transId;
    }

    public Date getTransDate() {
        return this.transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
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

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getBankAccountNo() {
        return this.bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getErrorDes() {
        return errorDes;
    }

    public void setErrorDes(String errorDes) {
        this.errorDes = errorDes;
    }


}