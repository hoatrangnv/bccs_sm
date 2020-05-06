package com.viettel.im.database.BO;

import java.util.Date;

/**
 * PaymentPosLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class PaymentPosLog implements java.io.Serializable {

    // Fields
    private Long paymentPosId;
    private String mti;
    private Long transactionAmount;
    private Long traceNo;
    private Long transactionFee;
    private String reference;
    private String responseCode;
    private String viettelShopCode;
    private String viettelStaffCode;
    private String cardNumber;
    private String processingCode;
    private Date createDate;
    private String userName;
    private String status;
    private String tidNo;
    //tuannv bo sung
    private String cardType;
    private Date transmissionDate;

    // Constructors
    /** default constructor */
    public PaymentPosLog() {
    }

    /** minimal constructor */
    public PaymentPosLog(Long paymentPosId, String reference) {
        this.paymentPosId = paymentPosId;
        this.reference = reference;
    }

    /** full constructor */
    public PaymentPosLog(Long paymentPosId, String mti, Long transactionAmount,
            Long traceNo, Long transactionFee, String reference,
            String responseCode, String viettelShopCode,
            String viettelStaffCode, String cardNumber, String processingCode,
            Date createDate, String userName, String status, String tidNo) {
        this.paymentPosId = paymentPosId;
        this.mti = mti;
        this.transactionAmount = transactionAmount;
        this.traceNo = traceNo;
        this.transactionFee = transactionFee;
        this.reference = reference;
        this.responseCode = responseCode;
        this.viettelShopCode = viettelShopCode;
        this.viettelStaffCode = viettelStaffCode;
        this.cardNumber = cardNumber;
        this.processingCode = processingCode;
        this.createDate = createDate;
        this.userName = userName;
        this.status = status;
        this.tidNo = tidNo;
    }

    // Property accessors
    public Long getPaymentPosId() {
        return this.paymentPosId;
    }

    public void setPaymentPosId(Long paymentPosId) {
        this.paymentPosId = paymentPosId;
    }

    public String getMti() {
        return this.mti;
    }

    public void setMti(String mti) {
        this.mti = mti;
    }

    public Long getTransactionAmount() {
        return this.transactionAmount;
    }

    public void setTransactionAmount(Long transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Long getTraceNo() {
        return this.traceNo;
    }

    public void setTraceNo(Long traceNo) {
        this.traceNo = traceNo;
    }

    public Long getTransactionFee() {
        return this.transactionFee;
    }

    public void setTransactionFee(Long transactionFee) {
        this.transactionFee = transactionFee;
    }

    public String getReference() {
        return this.reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getResponseCode() {
        return this.responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getViettelShopCode() {
        return this.viettelShopCode;
    }

    public void setViettelShopCode(String viettelShopCode) {
        this.viettelShopCode = viettelShopCode;
    }

    public String getViettelStaffCode() {
        return this.viettelStaffCode;
    }

    public void setViettelStaffCode(String viettelStaffCode) {
        this.viettelStaffCode = viettelStaffCode;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getProcessingCode() {
        return this.processingCode;
    }

    public void setProcessingCode(String processingCode) {
        this.processingCode = processingCode;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTidNo() {
        return this.tidNo;
    }

    public void setTidNo(String tidNo) {
        this.tidNo = tidNo;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Date getTransmissionDate() {
        return transmissionDate;
    }

    public void setTransmissionDate(Date transmissionDate) {
        this.transmissionDate = transmissionDate;
    }
}
