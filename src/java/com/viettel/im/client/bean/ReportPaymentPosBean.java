/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

import com.viettel.im.common.util.StringUtils;
import java.util.Date;

/**
 *
 * @author NamDX
 */
public class ReportPaymentPosBean implements java.io.Serializable {

    /* So thu tu the */
    private Long index;
    private String reference;
    private Long paymentId;
    private Long contractId;
    private Long paymentAmount;
    private Long transactionFee;
    private String cardNumber;
    private String processCode;
    private String viettelShopCode;
    private String mti;
    private String processingCode;
    private Date createDate;
    private String userName;
    private String status;
    private String tid;
    private String isdn;
    private String responseCode;//Ma code tra ve khi ket noi voi MB Bank
    private String traceNo;
    private String notValidStatus;//1: Tien, 2: Tid not valid
    //Ma loai the
    private String bin;
    //Ten loai the
    private String cardType;
    //Ten loai the
    private String cardTypeName;
    //Ten cua hang
    private String groupName;
    //Ngay giao dich kieu String
    private String createDateString;
    //Bao co cho Viettel
    private Long viettelPayment;
    private String strPaymentAmount;
    private String strTransactionFee;
    private String strViettelPayment;

    public ReportPaymentPosBean() {
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public String getStrPaymentAmount() {
        if (paymentAmount != null) {
            return StringUtils.formatMoney(paymentAmount);
        } else {
            return "";
        }
    }

    public void setStrPaymentAmount(String strPaymentAmount) {
        this.strPaymentAmount = strPaymentAmount;
    }

    public Long getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Long paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getStrTransactionFee() {
        if (transactionFee != null) {
            return StringUtils.formatMoney(transactionFee);
        } else {
            return "";
        }
    }

    public void setStrTransactionFee(String strTransactionFee) {
        this.strTransactionFee = strTransactionFee;
    }

    public String getStrViettelPayment() {
        if (viettelPayment != null) {
            return StringUtils.formatMoney(viettelPayment);
        } else {
            return "";
        }
    }

    public void setStrViettelPayment(String strViettelPayment) {
        this.strViettelPayment = strViettelPayment;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateDateString() {
        return createDateString;
    }

    public void setCreateDateString(String createDateString) {
        this.createDateString = createDateString;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getMti() {
        return mti;
    }

    public void setMti(String mti) {
        this.mti = mti;
    }

    public String getNotValidStatus() {
        return notValidStatus;
    }

    public void setNotValidStatus(String notValidStatus) {
        this.notValidStatus = notValidStatus;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public String getProcessingCode() {
        return processingCode;
    }

    public void setProcessingCode(String processingCode) {
        this.processingCode = processingCode;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getViettelPayment() {
        return viettelPayment;
    }

    public void setViettelPayment(Long viettelPayment) {
        this.viettelPayment = viettelPayment;
    }

    public String getViettelShopCode() {
        return viettelShopCode;
    }

    public void setViettelShopCode(String viettelShopCode) {
        this.viettelShopCode = viettelShopCode;
    }
}
