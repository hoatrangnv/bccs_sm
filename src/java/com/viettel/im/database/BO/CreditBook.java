package com.viettel.im.database.BO;

import java.util.Date;

/**
 * AccountBook entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class CreditBook implements java.io.Serializable {

    // Fields
    private Long requestId;
    private Long accountId;
    private Long requestType;
    private Double amountRequest;
    private Double debitRequest;
    private Long status;
    private Date createDate;
    private String userRequest;
    private Long stockTransId;
    private Date returnDate;
    private Long receiptId;
    private Double openingBalance;
    private Double closingBalance;
    private String ipAddress;
    //tuannv bo sung ngay 03/04/2010
    private String guarantorName;
    private String guarantorIdNo;
    private String guarantorTitleId;
    private String guarantorPhone;
    private String guarantorDepartment;
    private String referenceNo;

    // Constructors
    /** default constructor */
    public CreditBook() {
    }

    /** minimal constructor */
    public CreditBook(Long requestId, Long requestType) {
        this.requestId = requestId;
        this.requestType = requestType;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Double getAmountRequest() {
        return amountRequest;
    }

    public void setAmountRequest(Double amountRequest) {
        this.amountRequest = amountRequest;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Double getDebitRequest() {
        return debitRequest;
    }

    public void setDebitRequest(Double debitRequest) {
        this.debitRequest = debitRequest;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getRequestType() {
        return requestType;
    }

    public void setRequestType(Long requestType) {
        this.requestType = requestType;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getStockTransId() {
        return stockTransId;
    }

    public void setStockTransId(Long stockTransId) {
        this.stockTransId = stockTransId;
    }

    public String getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(String userRequest) {
        this.userRequest = userRequest;
    }

    public Double getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(Double closingBalance) {
        this.closingBalance = closingBalance;
    }

    public Double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getGuarantorDepartment() {
        return guarantorDepartment;
    }

    public void setGuarantorDepartment(String guarantorDepartment) {
        this.guarantorDepartment = guarantorDepartment;
    }

    public String getGuarantorIdNo() {
        return guarantorIdNo;
    }

    public void setGuarantorIdNo(String guarantorIdNo) {
        this.guarantorIdNo = guarantorIdNo;
    }

    public String getGuarantorName() {
        return guarantorName;
    }

    public void setGuarantorName(String guarantorName) {
        this.guarantorName = guarantorName;
    }

    public String getGuarantorPhone() {
        return guarantorPhone;
    }

    public void setGuarantorPhone(String guarantorPhone) {
        this.guarantorPhone = guarantorPhone;
    }

    public String getGuarantorTitleId() {
        return guarantorTitleId;
    }

    public void setGuarantorTitleId(String guarantorTitleId) {
        this.guarantorTitleId = guarantorTitleId;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }
}
