package com.viettel.im.database.BO;

import java.util.Date;

/**
 * SaleTransPost entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class SaleTransPost implements java.io.Serializable {

    // Fields
    private Long saleTransPostId;
    private Long saleTransId;
    private Long amount;
    private String referenceNo;
    private Long shopId;
    private String processCode;
    private String processingCode;
    private String mti;
    private Date createDate;
    private Date destroyDate;
    private String userCreate;
    private String userDestroy;
    private Long status;
    private Long tid;
    private String cardNumber;
    private String cardType;
    private String bin;
    private Date transmissionDate;
    private String viettelShopCode;
    private String traceNo;
    private Long transactionFee;
    private String error;
    

    // Constructors
    /** default constructor */
    public SaleTransPost() {
    }

    /** minimal constructor */
    public SaleTransPost(Long saleTransPostId, Long saleTransId) {
        this.saleTransPostId = saleTransPostId;
        this.saleTransId = saleTransId;
    }

    /** full constructor */
    public SaleTransPost(Long saleTransPostId, Long saleTransId, Long amount,
            String referenceNo, Long shopId, String processCode,
            String processingCode, String mti, Date createDate,
            Date destroyDate, String userCreate, String userDestroy,
            Long status, Long tid) {
        this.saleTransPostId = saleTransPostId;
        this.saleTransId = saleTransId;
        this.amount = amount;
        this.referenceNo = referenceNo;
        this.shopId = shopId;
        this.processCode = processCode;
        this.processingCode = processingCode;
        this.mti = mti;
        this.createDate = createDate;
        this.destroyDate = destroyDate;
        this.userCreate = userCreate;
        this.userDestroy = userDestroy;
        this.status = status;
        this.tid = tid;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    // Property accessors
    public Long getSaleTransPostId() {
        return this.saleTransPostId;
    }

    public void setSaleTransPostId(Long saleTransPostId) {
        this.saleTransPostId = saleTransPostId;
    }

    public Long getSaleTransId() {
        return this.saleTransId;
    }

    public void setSaleTransId(Long saleTransId) {
        this.saleTransId = saleTransId;
    }

    public Long getAmount() {
        return this.amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getReferenceNo() {
        return this.referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public Long getShopId() {
        return this.shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getProcessCode() {
        return this.processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public String getProcessingCode() {
        return this.processingCode;
    }

    public void setProcessingCode(String processingCode) {
        this.processingCode = processingCode;
    }

    public String getMti() {
        return this.mti;
    }

    public void setMti(String mti) {
        this.mti = mti;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getDestroyDate() {
        return this.destroyDate;
    }

    public void setDestroyDate(Date destroyDate) {
        this.destroyDate = destroyDate;
    }

    public String getUserCreate() {
        return this.userCreate;
    }

    public void setUserCreate(String userCreate) {
        this.userCreate = userCreate;
    }

    public String getUserDestroy() {
        return this.userDestroy;
    }

    public void setUserDestroy(String userDestroy) {
        this.userDestroy = userDestroy;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getTid() {
        return this.tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getViettelShopCode() {
        return viettelShopCode;
    }

    public Date getTransmissionDate() {
        return transmissionDate;
    }

    public void setTransmissionDate(Date transmissionDate) {
        this.transmissionDate = transmissionDate;
    }

    public void setViettelShopCode(String viettelShopCode) {
        this.viettelShopCode = viettelShopCode;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public Long getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(Long transactionFee) {
        this.transactionFee = transactionFee;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
