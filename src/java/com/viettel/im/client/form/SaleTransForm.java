/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import java.util.Date;

/**
 *
 * @author MrSun
 */
public class SaleTransForm {

    private Long saleTransId;
    private String custName;
    private String company;
    private String tin;
    private String payMethod;
    private Long reasonId;
    private Date saleTransDate;
    private Double amountBeforeTax;
    private Double amountTax;
    private Double amountAfterTax;
    private Double amountDiscount;
    String amountBeforeTaxDisplay;
    String amountTaxDisplay;
    String amountAfterTaxDisplay;
    String amountDiscountDisplay;
    SaleTransDetailForm saleTransDetailForm;
    /* 120802 : TrongLV : Bo sung 3 truong thong tin de ban phat */
    private Long channelTypeId;
    private String receiverCode;
    private String receiverName;
    private Long receiverId;
    private Long receiverType;

    /**/
    //TruongNQ5 20140820 R6534
    private Long agentTypeIdSearch;
    private String agentCodeSearch;
    private String agentNameSearch;
    //toancx 20150323 R8388
    private String custMobile;
    private String custOTP;
    private String reasonIdVipCustomer;
    private String isdnWallet;
    //tannh20180111 upload file va checked
    private String imageUrl;
    private Long isChecked;
    private String orderCode;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Long isChecked) {
        this.isChecked = isChecked;
    }

    public String getIsdnWallet() {
        return isdnWallet;
    }

    public void setIsdnWallet(String isdnWallet) {
        this.isdnWallet = isdnWallet;
    }

    public String getReasonIdVipCustomer() {
        return reasonIdVipCustomer;
    }

    public void setReasonIdVipCustomer(String reasonIdVipCustomer) {
        this.reasonIdVipCustomer = reasonIdVipCustomer;
    }

    public String getCustOTP() {
        return custOTP;
    }

    public void setCustOTP(String custOTP) {
        this.custOTP = custOTP;
    }

    public String getCustMobile() {
        return custMobile;
    }

    public void setCustMobile(String custMobile) {
        this.custMobile = custMobile;
    }

    public String getAgentNameSearch() {
        return agentNameSearch;
    }

    public void setAgentNameSearch(String agentNameSearch) {
        this.agentNameSearch = agentNameSearch;
    }

    public Long getAgentTypeIdSearch() {
        return agentTypeIdSearch;
    }

    public void setAgentTypeIdSearch(Long agentTypeIdSearch) {
        this.agentTypeIdSearch = agentTypeIdSearch;
    }

    public String getAgentCodeSearch() {
        return agentCodeSearch;
    }

    public void setAgentCodeSearch(String agentCodeSearch) {
        this.agentCodeSearch = agentCodeSearch;
    }
    //End TruongNQ5
    private Boolean isNotCheckPackageGoods;

    public Boolean getIsNotCheckPackageGoods() {
        return isNotCheckPackageGoods;
    }

    public void setIsNotCheckPackageGoods(Boolean isNotCheckPackageGoods) {
        this.isNotCheckPackageGoods = isNotCheckPackageGoods;
    }

    public SaleTransForm() {
        saleTransDetailForm = new SaleTransDetailForm();
    }

    public Double getAmountAfterTax() {
        return amountAfterTax;
    }

    public void setAmountAfterTax(Double amountAfterTax) {
        this.amountAfterTax = amountAfterTax;
    }

    public String getAmountAfterTaxDisplay() {
        return amountAfterTaxDisplay;
    }

    public void setAmountAfterTaxDisplay(String amountAfterTaxDisplay) {
        this.amountAfterTaxDisplay = amountAfterTaxDisplay;
    }

    public Double getAmountBeforeTax() {
        return amountBeforeTax;
    }

    public void setAmountBeforeTax(Double amountBeforeTax) {
        this.amountBeforeTax = amountBeforeTax;
    }

    public String getAmountBeforeTaxDisplay() {
        return amountBeforeTaxDisplay;
    }

    public void setAmountBeforeTaxDisplay(String amountBeforeTaxDisplay) {
        this.amountBeforeTaxDisplay = amountBeforeTaxDisplay;
    }

    public Double getAmountTax() {
        return amountTax;
    }

    public void setAmountTax(Double amountTax) {
        this.amountTax = amountTax;
    }

    public String getAmountTaxDisplay() {
        return amountTaxDisplay;
    }

    public void setAmountTaxDisplay(String amountTaxDisplay) {
        this.amountTaxDisplay = amountTaxDisplay;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
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

    public Date getSaleTransDate() {
        return saleTransDate;
    }

    public void setSaleTransDate(Date saleTransDate) {
        this.saleTransDate = saleTransDate;
    }

    public SaleTransDetailForm getSaleTransDetailForm() {
        return saleTransDetailForm;
    }

    public void setSaleTransDetailForm(SaleTransDetailForm saleTransDetailForm) {
        this.saleTransDetailForm = saleTransDetailForm;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public Double getAmountDiscount() {
        return amountDiscount;
    }

    public void setAmountDiscount(Double amountDiscount) {
        this.amountDiscount = amountDiscount;
    }

    public String getAmountDiscountDisplay() {
        return amountDiscountDisplay;
    }

    public void setAmountDiscountDisplay(String amountDiscountDisplay) {
        this.amountDiscountDisplay = amountDiscountDisplay;
    }

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(Long receiverType) {
        this.receiverType = receiverType;
    }

    public String getReceiverCode() {
        return receiverCode;
    }

    public void setReceiverCode(String receiverCode) {
        this.receiverCode = receiverCode;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public Long getSaleTransId() {
        return saleTransId;
    }

    public void setSaleTransId(Long saleTransId) {
        this.saleTransId = saleTransId;
    }
}
