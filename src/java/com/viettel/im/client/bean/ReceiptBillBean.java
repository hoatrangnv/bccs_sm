/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;

/**
 *
 * @author MrSun
 * 
 */
public class ReceiptBillBean {

    private Long depositId;
    private Long receiptId;

    private String fromOwnerName;
    private String fromOwnerAddress;

    private String deliverer;
    private String toOwnerName;

    private String receiptNo;
    private String createDate;

    private String payMethod;
    private String reasonName;

    private String receiptType;

    public ReceiptBillBean(String fromOwnerName, String fromOwnerAddress, String receiptNo, String createDate, String deliverer, String toOwnerName, String payMethod, String reasonName, Long depositId, Long receiptId) {
        this.depositId = depositId;
        this.receiptId = receiptId;
        this.fromOwnerName = fromOwnerName;
        this.fromOwnerAddress = fromOwnerAddress;
        this.deliverer = deliverer;
        this.toOwnerName = toOwnerName;
        this.receiptNo = receiptNo;
        this.createDate = createDate;
        this.payMethod = payMethod;
        this.reasonName = reasonName;
    }

    public ReceiptBillBean() {
    }

    public Long getDepositId() {
        return depositId;
    }

    public void setDepositId(Long depositId) {
        this.depositId = depositId;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDeliverer() {
        return deliverer;
    }

    public void setDeliverer(String deliverer) {
        this.deliverer = deliverer;
    }

    public String getFromOwnerAddress() {
        return fromOwnerAddress;
    }

    public void setFromOwnerAddress(String fromOwnerAddress) {
        this.fromOwnerAddress = fromOwnerAddress;
    }

    public String getFromOwnerName() {
        return fromOwnerName;
    }

    public void setFromOwnerName(String fromOwnerName) {
        this.fromOwnerName = fromOwnerName;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getToOwnerName() {
        return toOwnerName;
    }

    public void setToOwnerName(String toOwnerName) {
        this.toOwnerName = toOwnerName;
    }

    

}
