package com.viettel.im.client.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * InvoiceUsedDetailBean entity.
 *
 * @author MyEclipse Persistence Tools
 */
public class InvoiceUsedDetailBean implements java.io.Serializable {

    // Fields
    private Long saleTransId;
    private String custName;
    private String company;
    private String address;
    private String tin;
    private String account;
    private String payMethod;
    private Long reasonId;
    private String note;
    private Long amount;
    private Long amountTax;
    private Long invoiceListId;
    private Long fromInvoice;
    private Long toInvoice;
    private Long currInvoiceNo;

    public InvoiceUsedDetailBean(Long saleTransId, String custName, String company, String address, String tin, String account, String payMethod, Long reasonId, String note, Long amount, Long amountTax, Long invoiceListId, Long fromInvoice, Long toInvoice, Long currInvoiceNo) {
        this.saleTransId = saleTransId;
        this.custName = custName;
        this.company = company;
        this.address = address;
        this.tin = tin;
        this.account = account;
        this.payMethod = payMethod;
        this.reasonId = reasonId;
        this.note = note;
        this.amount = amount;
        this.amountTax = amountTax;
        this.invoiceListId = invoiceListId;
        this.fromInvoice = fromInvoice;
        this.toInvoice = toInvoice;
        this.currInvoiceNo = currInvoiceNo;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getAmountTax() {
        return amountTax;
    }

    public void setAmountTax(Long amountTax) {
        this.amountTax = amountTax;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Long getCurrInvoiceNo() {
        return currInvoiceNo;
    }

    public void setCurrInvoiceNo(Long currInvoiceNo) {
        this.currInvoiceNo = currInvoiceNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public Long getFromInvoice() {
        return fromInvoice;
    }

    public void setFromInvoice(Long fromInvoice) {
        this.fromInvoice = fromInvoice;
    }

    public Long getInvoiceListId() {
        return invoiceListId;
    }

    public void setInvoiceListId(Long invoiceListId) {
        this.invoiceListId = invoiceListId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public Long getReasonId() {
        return reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }

    public Long getSaleTransId() {
        return saleTransId;
    }

    public void setSaleTransId(Long saleTransId) {
        this.saleTransId = saleTransId;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public Long getToInvoice() {
        return toInvoice;
    }

    public void setToInvoice(Long toInvoice) {
        this.toInvoice = toInvoice;
    }

    
}