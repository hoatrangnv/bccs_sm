/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;

/**
 *
 * @author TungTV
 */
public class SaleTransInvoiceViewHelper {


    /** Name of current staff who create invoice */
    private String staffName;


    /** Name of staff's shop */
    private String shopName;


    /** Name of viewHelper field which was store in session */
    public static final String SALE_TRANS_INVOICE = "saleTransInvoice";


    /** Create invoice date */
    private String createDate;


    /** Agent shop address */
    private String agentAddress;


    /** Tax-code or Tin of agent shop */
    private String agentTin;


    /** ID of agent which was selected */
    private Long agentId;


    /** Code of pay-method */
    private String payMethodCode;


    /** ID of reason */
    private Long reasonId;


    /** Serial of selected invoice serial */
    private String serialNo;


    /** Account of agent */
    private String agentAccount;


    /** Name of agent */
    private String agentName;


    /** Amount of current trans with tax */
    private String amountTax;


    /** Amount of current trans with no tax */
    private String amountNotTax;


    /** Tax */
    private String tax;


    /** Discount */
    private String discount;


    /** Promotion */
    private String promotion;


    /** Start invoice of selected invoice list */
    private String fromInvoice;


    /** Note for create invoice */
    private String note;


    /** End invoice of selected invoice list */
    private String toInvoice;


    /** Invoice to be used */
    private String currInvoice;


    /** Id of Invoice to be used */
    private Long invoiceListId;


    /** Select sale trans or not */
    private boolean selectedSaleTrans;


    /** Type of interface
     * 1: Invoice for Agent
     * 2: Invoice for colaberation
     * 3: Invoice for retail
     */
    private Long interfaceType;


    /** Cust name */
    private String custName;


    /** Cust company */
    private String company;


    /** Cust address */
    private String address;


    /** Cust tin */
    private String tin;


    /** Cust account */
    private String account;

    /** For direct create invoice */
    private Long invoiceType;

    private String finalAmount;

    /**
     * Reserve object type in search condition in create sale trans for
     * collaborator or sub shop
     */
    private boolean saleTransForSubShop;


    public void resetViewHelper() {
        this.agentAddress = null;
        this.agentTin = null;
        this.agentAccount = null;
        this.payMethodCode = null;
        this.reasonId = null;
        this.note = null;
        this.serialNo = null;
        this.fromInvoice = null;
        this.toInvoice = null;
        this.currInvoice = null;
        this.custName = null;
        this.company = null;
        this.address = null;
        this.tin = null;
        this.account = null;
    }

    public void resetAmountInfo() {
        this.amountNotTax = null;
        this.amountTax = null;
        this.discount = null;
        this.promotion = null;
        this.tax = null;
        this.agentId = null;
        this.invoiceType = null;
        this.finalAmount = null;
    }

    public String getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(String finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getAgentAccount() {
        return agentAccount;
    }

    public void setAgentAccount(String agentAccount) {
        this.agentAccount = agentAccount;
    }

    public String getAgentAddress() {
        return agentAddress;
    }

    public void setAgentAddress(String agentAddress) {
        this.agentAddress = agentAddress;
    }

    public String getAgentTin() {
        return agentTin;
    }

    public void setAgentTin(String agentTin) {
        this.agentTin = agentTin;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getAmountNotTax() {
        return amountNotTax;
    }

    public void setAmountNotTax(String amountNotTax) {
        this.amountNotTax = amountNotTax;
    }

    public String getAmountTax() {
        return amountTax;
    }

    public void setAmountTax(String amountTax) {
        this.amountTax = amountTax;
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

    public String getToInvoice() {
        return toInvoice;
    }

    public void setToInvoice(String toInvoice) {
        this.toInvoice = toInvoice;
    }

    public Long getInvoiceListId() {
        return invoiceListId;
    }

    public void setInvoiceListId(Long invoiceListId) {
        this.invoiceListId = invoiceListId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getPayMethodCode() {
        return payMethodCode;
    }

    public void setPayMethodCode(String payMethodCode) {
        this.payMethodCode = payMethodCode;
    }

    public Long getReasonId() {
        return reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public boolean isSelectedSaleTrans() {
        return selectedSaleTrans;
    }

    public void setSelectedSaleTrans(boolean selectedSaleTrans) {
        this.selectedSaleTrans = selectedSaleTrans;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(Long interfaceType) {
        this.interfaceType = interfaceType;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
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

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public Long getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Long invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public boolean isSaleTransForSubShop() {
        return saleTransForSubShop;
    }

    public void setSaleTransForSubShop(boolean saleTransForSubShop) {
        this.saleTransForSubShop = saleTransForSubShop;
    }

}
