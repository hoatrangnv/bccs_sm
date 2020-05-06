package com.viettel.im.database.BO;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * SaleTrans entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class SaleTrans implements java.io.Serializable {

    // Fields
    private Long saleTransId;
    //private InvoiceUsed invoiceUsed;
    private Long invoiceUsedId;
    private Date saleTransDate;
    private String saleTransType;
    private String status;
    private String checkStock;
    private Date invoiceCreateDate;
    private Long shopId;
    private Long staffId;
    private String payMethod;
    private Long saleServiceId;
    private Long saleServicePriceId;

    private Double amountService;//Khong dung (de luu tam thoi tong tien dat coc khi lap giao dich ban thay ctv)
    private Double amountModel;//Khong dung
    private Double discount;//Tong tien chiet khau chua thue =sum(discount_amount)
    private Double promotion;//Tong tien khuyen mai
    private Double amountTax;//Tong tien thanh toan
    private Double amountNotTax;//Tong tien truoc thue da tru ck
    private Double vat;//vat = ti suat thue vat
    private Double tax;//Tong tien thue =sum(vat_amount)

    private Long subId;
    private String isdn;
    private String custName;
    private String contractNo;
    private String telNumber;
    private String company;
    private String address;
    private String tin;
    private String note;
    private String destroyUser;
    private Date destroyDate;
    private String approverUser;
    private Date approverDate;
    private Long reasonId;
    private Long telecomServiceId;
    private String transferGoods;
    private Set saleTransDetails = new HashSet(0);
    private String saleTransCode;
    private Long stockTransId;
    private Long createStaffId;

    private Long receiverId;
    private Long receiverType;
    private String inTransId;

    private List listVSaleTransDetail = new ArrayList(); //tamdt1, 25/06/2009, phuc vu viec xuat bao cao doanh thu

    private Long fromSaleTransId;

    private String currency;
    private Long channel;
    
    //22670 xuat hang ban cho dai ly
    private Long parentMasterAgentId;
    private String paymentPapersCode;
    private String amountPayment;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    // Constructors
    /** default constructor */
    public SaleTrans() {
    }

    /** minimal constructor */
    public SaleTrans(Long invoiceUsedId, Long saleTransId, Date saleTransDate, String status,
            String checkStock, Double promotion) {
        this.invoiceUsedId = invoiceUsedId;
        this.saleTransId = saleTransId;
        this.saleTransDate = saleTransDate;
        this.status = status;
        this.checkStock = checkStock;
        this.promotion = promotion;
    }

    /** full constructor */
    public SaleTrans(Long invoiceUsedId, Long saleTransId,
            Date saleTransDate, String saleTransType, String status,
            String checkStock, Date invoiceCreateDate, Long shopId,
            Long staffId, String payMethod, Long saleServiceId,
            Long saleServicePriceId, Double amountService, Double amountModel,
            Double discount, Double promotion, Double amountTax, Double amountNotTax,
            Double vat, Double tax, Long subId, String isdn, String custName,
            String contractNo, String telNumber, String company,
            String address, String tin, String note, String destroyUser,
            Date destroyDate, String approverUser, Date approverDate,
            Long reasonId, Long telecomServiceId, String transferGoods,
            Set saleTransDetails) {
        this.saleTransId = saleTransId;
        this.invoiceUsedId = invoiceUsedId;
        this.saleTransDate = saleTransDate;
        this.saleTransType = saleTransType;
        this.status = status;
        this.checkStock = checkStock;
        this.invoiceCreateDate = invoiceCreateDate;
        this.shopId = shopId;
        this.staffId = staffId;
        this.payMethod = payMethod;
        this.saleServiceId = saleServiceId;
        this.saleServicePriceId = saleServicePriceId;
        this.amountService = amountService;
        this.amountModel = amountModel;
        this.discount = discount;
        this.promotion = promotion;
        this.amountTax = amountTax;
        this.amountNotTax = amountNotTax;
        this.vat = vat;
        this.tax = tax;
        this.subId = subId;
        this.isdn = isdn;
        this.custName = custName;
        this.contractNo = contractNo;
        this.telNumber = telNumber;
        this.company = company;
        this.address = address;
        this.tin = tin;
        this.note = note;
        this.destroyUser = destroyUser;
        this.destroyDate = destroyDate;
        this.approverUser = approverUser;
        this.approverDate = approverDate;
        this.reasonId = reasonId;
        this.telecomServiceId = telecomServiceId;
        this.transferGoods = transferGoods;
        this.saleTransDetails = saleTransDetails;
    }


    // Property accessors


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
    
    public Long getSaleTransId() {
        return this.saleTransId;
    }

    public void setSaleTransId(Long saleTransId) {
        this.saleTransId = saleTransId;
    }
    
    public Date getSaleTransDate() {
        return this.saleTransDate;
    }

    public void setSaleTransDate(Date saleTransDate) {
        this.saleTransDate = saleTransDate;
    }

    public String getSaleTransType() {
        return this.saleTransType;
    }

    public void setSaleTransType(String saleTransType) {
        this.saleTransType = saleTransType;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCheckStock() {
        return this.checkStock;
    }

    public void setCheckStock(String checkStock) {
        this.checkStock = checkStock;
    }

    public Date getInvoiceCreateDate() {
        return this.invoiceCreateDate;
    }

    public void setInvoiceCreateDate(Date invoiceCreateDate) {
        this.invoiceCreateDate = invoiceCreateDate;
    }

    public Long getShopId() {
        return this.shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getStaffId() {
        return this.staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getPayMethod() {
        return this.payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public Long getSaleServiceId() {
        return this.saleServiceId;
    }

    public void setSaleServiceId(Long saleServiceId) {
        this.saleServiceId = saleServiceId;
    }

    public Long getSaleServicePriceId() {
        return this.saleServicePriceId;
    }

    public void setSaleServicePriceId(Long saleServicePriceId) {
        this.saleServicePriceId = saleServicePriceId;
    }

    public Double getAmountService() {
        return this.amountService;
    }

    public void setAmountService(Double amountService) {
        this.amountService = amountService;
    }

    public Double getAmountModel() {
        return this.amountModel;
    }

    public void setAmountModel(Double amountModel) {
        this.amountModel = amountModel;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getPromotion() {
        return this.promotion;
    }

    public void setPromotion(Double promotion) {
        this.promotion = promotion;
    }

    public Double getAmountTax() {
        return this.amountTax;
    }

    public void setAmountTax(Double amountTax) {
        this.amountTax = amountTax;
    }

    public Double getAmountNotTax() {
        return this.amountNotTax;
    }

    public void setAmountNotTax(Double amountNotTax) {
        this.amountNotTax = amountNotTax;
    }

    public Double getVat() {
        return this.vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public Double getTax() {
        return this.tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Long getSubId() {
        return this.subId;
    }

    public void setSubId(Long subId) {
        this.subId = subId;
    }

    public String getIsdn() {
        return this.isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getCustName() {
        return this.custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getContractNo() {
        return this.contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getTelNumber() {
        return this.telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTin() {
        return this.tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDestroyUser() {
        return this.destroyUser;
    }

    public void setDestroyUser(String destroyUser) {
        this.destroyUser = destroyUser;
    }

    public Date getDestroyDate() {
        return this.destroyDate;
    }

    public void setDestroyDate(Date destroyDate) {
        this.destroyDate = destroyDate;
    }

    public String getApproverUser() {
        return this.approverUser;
    }

    public void setApproverUser(String approverUser) {
        this.approverUser = approverUser;
    }

    public Date getApproverDate() {
        return this.approverDate;
    }

    public void setApproverDate(Date approverDate) {
        this.approverDate = approverDate;
    }

    public Long getReasonId() {
        return this.reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }

    public Long getTelecomServiceId() {
        return this.telecomServiceId;
    }

    public void setTelecomServiceId(Long telecomServiceId) {
        this.telecomServiceId = telecomServiceId;
    }

    public String getTransferGoods() {
        return this.transferGoods;
    }

    public void setTransferGoods(String transferGoods) {
        this.transferGoods = transferGoods;
    }

    public Set getSaleTransDetails() {
        return this.saleTransDetails;
    }

    public void setSaleTransDetails(Set saleTransDetails) {
        this.saleTransDetails = saleTransDetails;
    }

    public String getSaleTransCode() {
        return saleTransCode;
    }

    public void setSaleTransCode(String saleTransCode) {
        this.saleTransCode = saleTransCode;
    }

    public Long getInvoiceUsedId() {
        return invoiceUsedId;
    }

    public void setInvoiceUsedId(Long invoiceUsedId) {
        this.invoiceUsedId = invoiceUsedId;
    }

    public Long getStockTransId() {
        return stockTransId;
    }

    public void setStockTransId(Long stockTransId) {
        this.stockTransId = stockTransId;
    }

    public Long getCreateStaffId() {
        return createStaffId;
    }

    public void setCreateStaffId(Long createStaffId) {
        this.createStaffId = createStaffId;
    }

    public List getListVSaleTransDetail() {
        return listVSaleTransDetail;
    }

    public void setListVSaleTransDetail(List listVSaleTransDetail) {
        this.listVSaleTransDetail = listVSaleTransDetail;
    }

    public String getInTransId() {
        return inTransId;
    }

    public void setInTransId(String inTransId) {
        this.inTransId = inTransId;
    }

    public Long getFromSaleTransId() {
        return fromSaleTransId;
    }

    public void setFromSaleTransId(Long fromSaleTransId) {
        this.fromSaleTransId = fromSaleTransId;
    }

    public Long getChannel() {
        return channel;
    }

    public void setChannel(Long channel) {
        this.channel = channel;
    }

    public Long getParentMasterAgentId() {
        return parentMasterAgentId;
    }

    public void setParentMasterAgentId(Long parentMasterAgentId) {
        this.parentMasterAgentId = parentMasterAgentId;
    }

    public String getPaymentPapersCode() {
        return paymentPapersCode;
    }

    public void setPaymentPapersCode(String paymentPapersCode) {
        this.paymentPapersCode = paymentPapersCode;
    }

    public String getAmountPayment() {
        return amountPayment;
    }

    public void setAmountPayment(String amountPayment) {
        this.amountPayment = amountPayment;
    }

}