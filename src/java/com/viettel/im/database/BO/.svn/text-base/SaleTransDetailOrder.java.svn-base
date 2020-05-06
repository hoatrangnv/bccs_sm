package com.viettel.im.database.BO;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * SaleTransDetail entity.
 *
 * @author MyEclipse Persistence Tools
 */
public class SaleTransDetailOrder implements java.io.Serializable {

    // Fields
    private Long saleTransDetailId;
    //private SaleTrans saleTrans;
    private Long saleTransId;
    private Long stockModelId;
    //private StockModel stockModel;
    private Date saleTransDate;
    private Long stateId;
    private Long priceId;
    private Long quantity;//So luong
    private Long discountId;
    private Double amount;//Sau thue
    private String transferGood;
    private Set saleTransSerials = new HashSet(0);
    private String note;
    private Long promotionId;
    private Double promotionAmount;
    private Double discountAmount;
    private Double vatAmount;
    private String serial;
    private Long saleServicesId;
    private Long saleServicesPriceId;
    //tamdt1, start, 15/07/2010
    //cap nhat them cac truong can thiet phuc vu bao cao doanh thu
    private Long stockTypeId;
    private String stockTypeCode;
    private String stockTypeName;
    private String stockModelCode;
    private String stockModelName;
    private String accountingModelCode;
    private String accountingModelName;
    private Double price;//Gia
    private Double priceVat;
    private String saleServicesCode;
    private String saleServicesName;
    private Double saleServicesPrice;
    private Double saleServicesPriceVat;
    private Double amountBeforeTax;
    private Double amountTax;
    private Double amountAfterTax;
    private String currency;
    String kitMfsIsdn;
    private String quantityMoney;
    private String priceMoney;
    private String amountMoney;
    //LinhNBV start add field saleTransOrderId - 20180522
    private Long saleTransOrderId;

    public Long getSaleTransOrderId() {
        return saleTransOrderId;
    }

    public void setSaleTransOrderId(Long saleTransOrderId) {
        this.saleTransOrderId = saleTransOrderId;
    }

    public String getQuantityMoney() {
        return quantityMoney;
    }

    public void setQuantityMoney(String quantityMoney) {
        this.quantityMoney = quantityMoney;
    }

    public String getPriceMoney() {
        return priceMoney;
    }

    public void setPriceMoney(String priceMoney) {
        this.priceMoney = priceMoney;
    }

    public String getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(String amountMoney) {
        this.amountMoney = amountMoney;
    }

    public String getKitMfsIsdn() {
        return kitMfsIsdn;
    }

    public void setKitMfsIsdn(String kitMfsIsdn) {
        this.kitMfsIsdn = kitMfsIsdn;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    //tamdt1, end

    public Long getSaleServicesId() {
        return saleServicesId;
    }

    public void setSaleServicesId(Long saleServicesId) {
        this.saleServicesId = saleServicesId;
    }

    public Long getSaleServicesPriceId() {
        return saleServicesPriceId;
    }

    public void setSaleServicesPriceId(Long saleServicesPriceId) {
        this.saleServicesPriceId = saleServicesPriceId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    // Constructors
    /**
     * default constructor
     */
    public SaleTransDetailOrder() {
    }

    /**
     * minimal constructor
     */
    public SaleTransDetailOrder(Long saleTransDetailId,
            StockModel stockModel, Date saleTransDate, Long stateId,
            Long priceId, Long quantity) {
        this.saleTransDetailId = saleTransDetailId;
        //this.saleTrans = saleTrans;
        //this.stockModel = stockModel;
        this.saleTransDate = saleTransDate;
        this.stateId = stateId;
        this.priceId = priceId;
        this.quantity = quantity;
    }

    /**
     * full constructor
     */
    public SaleTransDetailOrder(Long saleTransDetailId,
            Date saleTransDate, Long stateId,
            Long priceId, Long quantity, Long discountId, Double amount,
            String transferGood, Set saleTransSerials) {
        this.saleTransDetailId = saleTransDetailId;
        //this.saleTrans = saleTrans;
        //this.stockModel = stockModel;
        this.saleTransDate = saleTransDate;
        this.stateId = stateId;
        this.priceId = priceId;
        this.quantity = quantity;
        this.discountId = discountId;
        this.amount = amount;
        this.transferGood = transferGood;
        this.saleTransSerials = saleTransSerials;
    }

    // Property accessors
    public Long getSaleTransDetailId() {
        return this.saleTransDetailId;
    }

    public void setSaleTransDetailId(Long saleTransDetailId) {
        this.saleTransDetailId = saleTransDetailId;
    }

//	public SaleTrans getSaleTrans() {
//		return this.saleTrans;
//	}
//
//	public void setSaleTrans(SaleTrans saleTrans) {
//		this.saleTrans = saleTrans;
//	}
//	public StockModel getStockModel() {
//		return this.stockModel;
//	}
//
//	public void setStockModel(StockModel stockModel) {
//		this.stockModel = stockModel;
//	}
    public Date getSaleTransDate() {
        return this.saleTransDate;
    }

    public void setSaleTransDate(Date saleTransDate) {
        this.saleTransDate = saleTransDate;
    }

    public Long getStateId() {
        return this.stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getPriceId() {
        return this.priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getDiscountId() {
        return this.discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public Long getSaleTransId() {
        return saleTransId;
    }

    public void setSaleTransId(Long saleTransId) {
        this.saleTransId = saleTransId;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransferGood() {
        return this.transferGood;
    }

    public void setTransferGood(String transferGood) {
        this.transferGood = transferGood;
    }

    public Set getSaleTransSerials() {
        return this.saleTransSerials;
    }

    public void setSaleTransSerials(Set saleTransSerials) {
        this.saleTransSerials = saleTransSerials;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Double getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(Double promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(Double vatAmount) {
        this.vatAmount = vatAmount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPriceVat() {
        return priceVat;
    }

    public void setPriceVat(Double priceVat) {
        this.priceVat = priceVat;
    }

    public String getSaleServicesCode() {
        return saleServicesCode;
    }

    public void setSaleServicesCode(String saleServicesCode) {
        this.saleServicesCode = saleServicesCode;
    }

    public String getSaleServicesName() {
        return saleServicesName;
    }

    public void setSaleServicesName(String saleServicesName) {
        this.saleServicesName = saleServicesName;
    }

    public Double getSaleServicesPrice() {
        return saleServicesPrice;
    }

    public void setSaleServicesPrice(Double saleServicesPrice) {
        this.saleServicesPrice = saleServicesPrice;
    }

    public Double getSaleServicesPriceVat() {
        return saleServicesPriceVat;
    }

    public void setSaleServicesPriceVat(Double saleServicesPriceVat) {
        this.saleServicesPriceVat = saleServicesPriceVat;
    }

    public String getStockModelCode() {
        return stockModelCode;
    }

    public void setStockModelCode(String stockModelCode) {
        this.stockModelCode = stockModelCode;
    }

    public String getStockModelName() {
        return stockModelName;
    }

    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }

    public String getStockTypeCode() {
        return stockTypeCode;
    }

    public void setStockTypeCode(String stockTypeCode) {
        this.stockTypeCode = stockTypeCode;
    }

    public Long getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public String getStockTypeName() {
        return stockTypeName;
    }

    public void setStockTypeName(String stockTypeName) {
        this.stockTypeName = stockTypeName;
    }

    public Double getAmountAfterTax() {
        return amountAfterTax;
    }

    public void setAmountAfterTax(Double amountAfterTax) {
        this.amountAfterTax = amountAfterTax;
    }

    public Double getAmountBeforeTax() {
        return amountBeforeTax;
    }

    public void setAmountBeforeTax(Double amountBeforeTax) {
        this.amountBeforeTax = amountBeforeTax;
    }

    public Double getAmountTax() {
        return amountTax;
    }

    public void setAmountTax(Double amountTax) {
        this.amountTax = amountTax;
    }

    public String getAccountingModelCode() {
        return accountingModelCode;
    }

    public void setAccountingModelCode(String accountingModelCode) {
        this.accountingModelCode = accountingModelCode;
    }

    public String getAccountingModelName() {
        return accountingModelName;
    }

    public void setAccountingModelName(String accountingModelName) {
        this.accountingModelName = accountingModelName;
    }
}
