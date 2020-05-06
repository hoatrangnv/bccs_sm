/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

import java.util.Date;

/**
 *<P>InvoiceListBean for display in interface</P>
 * @author TungTV
 */
public class InvoiceSaleListBean implements java.io.Serializable {

    /** ID of invoice used */
    private Long invoiceUsedId;

    /** Serial of invoice used */
    private String serialNo;

    /** Block number of invoice used */
    private String blockNo;

    /** invoice used number in invoice list */
    private Long invoiceId;

    /** date created invoice used */
    private Date createdate;
    private Date invoiceDate;

    /** customer name */
    private String custName;

    /** customer address */
    private String address;

    /** customer company */
    private String company;

    /** customer tin */
    private String tin;

    /** customer account */
    private String account;

    /** customer reason */
    private String reasonName;
    private String reasonId;

    /** customer pay method */
    private String payMethodName;
    private String payMethodId;

    /** customer account tax */
    private Double amountTax;

    /** customer account not tax */
    private Double amountNotTax;

    /** customer discount */
    private Double discount;

    /** customer discount */
    private Double promotion;

    /** invoice used note */
    private String note;
    private String isdn;
    private Long telecomServiceId;

    /** invoice used tax */
    private Double tax;

    private Double vat;

    private String InvoiceNo;

    private Long invoiceStatus;
    private String invoiceStatusName;

    private String shopCode;
    private String shopName;
    private String staffCode;
    private String staffName;
    private String fromInvoice;
    private String toInvoice;
    private String currInvoice;

    private String canEdit;

    private String currency;
    //add by sonbc2
    private String printStatus;
    private Long requestPrint;
    private Long checkPrint;
     //tannh20180425 start: chi duoc in 3 lan theo YC TraTV phong TC
    private Long checkPrint2;
    private Long checkRequestPrint;
    private Long checkApprovePrint;
    private String esdCode;
    private Long printType1;
    private Long printType2;
    private String requestCreditInv;
    private Long checkRequestInvoice;
    private Long checkApproveInvoice;
    private String isCreditInv;
    private String originalInvoice;

    public Long getCheckApproveInvoice() {
        return checkApproveInvoice;
    }

    public void setCheckApproveInvoice(Long checkApproveInvoice) {
        this.checkApproveInvoice = checkApproveInvoice;
    }

    public Long getCheckApprovePrint() {
        return checkApprovePrint;
    }

    public void setCheckApprovePrint(Long checkApprovePrint) {
        this.checkApprovePrint = checkApprovePrint;
    }

    public Long getCheckPrint() {
        return checkPrint;
    }

    public void setCheckPrint(Long checkPrint) {
        this.checkPrint = checkPrint;
    }

    public Long getCheckRequestInvoice() {
        return checkRequestInvoice;
    }

    public void setCheckRequestInvoice(Long checkRequestInvoice) {
        this.checkRequestInvoice = checkRequestInvoice;
    }

    public Long getCheckRequestPrint() {
        return checkRequestPrint;
    }

    public void setCheckRequestPrint(Long checkRequestPrint) {
        this.checkRequestPrint = checkRequestPrint;
    }

    public String getEsdCode() {
        return esdCode;
    }

    public void setEsdCode(String esdCode) {
        this.esdCode = esdCode;
    }

    public String getIsCreditInv() {
        return isCreditInv;
    }

    public void setIsCreditInv(String isCreditInv) {
        this.isCreditInv = isCreditInv;
    }

    public String getOriginalInvoice() {
        return originalInvoice;
    }

    public void setOriginalInvoice(String originalInvoice) {
        this.originalInvoice = originalInvoice;
    }

    public String getPrintStatus() {
        return printStatus;
    }

    public void setPrintStatus(String printStatus) {
        this.printStatus = printStatus;
    }

    public Long getPrintType1() {
        return printType1;
    }

    public void setPrintType1(Long printType1) {
        this.printType1 = printType1;
    }

    public Long getPrintType2() {
        return printType2;
    }

    public void setPrintType2(Long printType2) {
        this.printType2 = printType2;
    }

    public String getRequestCreditInv() {
        return requestCreditInv;
    }

    public void setRequestCreditInv(String requestCreditInv) {
        this.requestCreditInv = requestCreditInv;
    }

    public Long getRequestPrint() {
        return requestPrint;
    }

    public void setRequestPrint(Long requestPrint) {
        this.requestPrint = requestPrint;
    }

    public Long getTelecomServiceId() {
        return telecomServiceId;
    }

    public void setTelecomServiceId(Long telecomServiceId) {
        this.telecomServiceId = telecomServiceId;
    }

    
    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    
    
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    

    public String getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(String canEdit) {
        this.canEdit = canEdit;
    }

    

    public Long getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(Long invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }
    

    public String getInvoiceNo() {
        return InvoiceNo;
    }

    public void setInvoiceNo(String InvoiceNo) {
        this.InvoiceNo = InvoiceNo;
    }

    public Double getVAT() {
        return vat;
    }

    public void setVAT(Double VAT) {
        this.vat = VAT;
    }

    /** name of user created invoice used */
    


    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Long getInvoiceUsedId() {
        return invoiceUsedId;
    }

    public void setInvoiceUsedId(Long invoiceUsedId) {
        this.invoiceUsedId = invoiceUsedId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPayMethodName() {
        return payMethodName;
    }

    public void setPayMethodName(String payMethodName) {
        this.payMethodName = payMethodName;
    }

   public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Double getAmountNotTax() {
        return amountNotTax;
    }

    public void setAmountNotTax(Double amountNotTax) {
        this.amountNotTax = amountNotTax;
    }

    public Double getAmountTax() {
        return amountTax;
    }

    public void setAmountTax(Double amountTax) {
        this.amountTax = amountTax;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getPromotion() {
        return promotion;
    }

    public void setPromotion(Double promotion) {
        this.promotion = promotion;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public String getBlockNo() {
        return blockNo;
    }

    public void setBlockNo(String blockNo) {
        this.blockNo = blockNo;
    }

    public String getPayMethodId() {
        return payMethodId;
    }

    public void setPayMethodId(String payMethodId) {
        this.payMethodId = payMethodId;
    }

    public String getReasonId() {
        return reasonId;
    }

    public void setReasonId(String reasonId) {
        this.reasonId = reasonId;
    }

    public String getCurrInvoice() {
        return currInvoice;
    }

    public void setCurrInvoice(String currInvoice) {
        this.currInvoice = currInvoice;
    }

    public String getFromInvoice() {
        return fromInvoice;
    }

    public void setFromInvoice(String fromInvoice) {
        this.fromInvoice = fromInvoice;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getToInvoice() {
        return toInvoice;
    }

    public void setToInvoice(String toInvoice) {
        this.toInvoice = toInvoice;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceStatusName() {
        return invoiceStatusName;
    }

    public void setInvoiceStatusName(String invoiceStatusName) {
        this.invoiceStatusName = invoiceStatusName;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public Long getCheckPrint2() {
        return checkPrint2;
    }

    public void setCheckPrint2(Long checkPrint2) {
        this.checkPrint2 = checkPrint2;
    }

}

