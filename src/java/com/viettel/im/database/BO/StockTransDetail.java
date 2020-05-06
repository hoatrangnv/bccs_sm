package com.viettel.im.database.BO;

import com.viettel.database.BO.BasicBO;
import java.util.Date;
import java.util.List;

/**
 * StockTransDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class StockTransDetail extends BasicBO { 

    private static final long serialVersionUID = 1L;

    // Fields
    private Long telecomServiceId;
    private Long stockTransDetailId;
    private Long stockTransId;
    private Long stateId;
    private Long quantityRes;
    private Long quantityReal;
    private Date createDatetime;
    private String note;
    private Long stockModelId;
    private Long discountGroupId;
    private Long checkDial;
    private Long dialStatus;
    private String name = "";
    private String unit = "";
    private String nameCode = "";
    private List<Price> prices = null;
    private List<Long> discounts = null;
    private Long checkSerial;
    private Double amount = null;
    private String totalSingleModel = null;

    //Tuannv bo sung de tinh tien truoc thue
    private Double amountNotTax = null;

    private Long discountId = null;
    private Long priceId = null;
    Double vat;
    String currency;
    
    Double price = null;   
    Double amountBeforeTax;
    Double amountTax;
    Double amountAfterTax;
    
    String priceDisplay;
    String amountBeforeTaxDisplay;
    String amountTaxDisplay;
    String amountAfterTaxDisplay;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
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

    public String getPriceDisplay() {
        return priceDisplay;
    }

    public void setPriceDisplay(String priceDisplay) {
        this.priceDisplay = priceDisplay;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }
    

    

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTotalSingleModel() {
        return totalSingleModel;
    }

    public void setTotalSingleModel(String totalSingleModel) {
        this.totalSingleModel = totalSingleModel;
    }

    public List<Long> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Long> discounts) {
        this.discounts = discounts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameCode() {
        return nameCode;
    }

    public void setNameCode(String nameCode) {
        this.nameCode = nameCode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }
    // Constructors

    /** default constructor */
    public StockTransDetail() {
    }

    /** minimal constructor */
    public StockTransDetail(Long stockTransDetailId) {
        this.stockTransDetailId = stockTransDetailId;
    }

    public StockTransDetail(Long telecomServiceId, Long stockTransDetailId, Long stockTransId,
            Long quantityRes, Long stockModelId,String name,String code,String unit ) {
        this.telecomServiceId = telecomServiceId;
        this.stockTransDetailId = stockTransDetailId;
        this.stockTransId = stockTransId;
        this.quantityRes = quantityRes;
        this.stockModelId = stockModelId;
        this.name=name;
        this.nameCode=code;
        this.unit=unit;
    }


    /** full constructor */
    public StockTransDetail(Long telecomServiceId, Long stockTransDetailId, Long stockTransId,
            Long stateId, Long quantityRes, Long quantityReal,
            Date createDatetime, String note, Long stockModelId, Long checkDial) {
        this.telecomServiceId = telecomServiceId;
        this.stockTransId = stockTransId;
        this.stockTransDetailId = stockTransDetailId;
        this.stockTransId = stockTransId;
        this.stateId = stateId;
        this.quantityRes = quantityRes;
        this.quantityReal = quantityReal;
        this.createDatetime = createDatetime;
        this.note = note;
        this.stockModelId = stockModelId;
        this.checkDial = checkDial;
    }

    // Property accessors
    public Long getStockTransDetailId() {
        return this.stockTransDetailId;
    }

    public void setStockTransDetailId(Long stockTransDetailId) {
        this.stockTransDetailId = stockTransDetailId;
    }

    public Long getStockTransId() {
        return this.stockTransId;
    }

    public void setStockTransId(Long stockTransId) {
        this.stockTransId = stockTransId;
    }

    public Long getStateId() {
        return this.stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getQuantityRes() {
        return this.quantityRes;
    }

    public void setQuantityRes(Long quantityRes) {
        this.quantityRes = quantityRes;
    }

    public Long getQuantityReal() {
        return this.quantityReal;
    }

    public void setQuantityReal(Long quantityReal) {
        this.quantityReal = quantityReal;
    }

    public Date getCreateDatetime() {
        return this.createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    /**
     * @return the checkDial
     */
    public Long getCheckDial() {
        return checkDial;
    }

    /**
     * @param checkDial the checkDial to set
     */
    public void setCheckDial(Long checkDial) {
        this.checkDial = checkDial;
    }

    public Long getDialStatus() {
        return dialStatus;
    }

    public void setDialStatus(Long dialStatus) {
        this.dialStatus = dialStatus;
    }

    /**
     * @return the amountNotTax
     */
    public Double getAmountNotTax() {
        return amountNotTax;
    }

    /**
     * @param amountNotTax the amountNotTax to set
     */
    public void setAmountNotTax(Double amountNotTax) {
        this.amountNotTax = amountNotTax;
    }

    /**
     * @return the checkSerial
     */
    public Long getCheckSerial() {
        return checkSerial;
    }

    /**
     * @param checkSerial the checkSerial to set
     */
    public void setCheckSerial(Long checkSerial) {
        this.checkSerial = checkSerial;
    }

    public Long getDiscountGroupId() {
        return discountGroupId;
    }

    public void setDiscountGroupId(Long discountGroupId) {
        this.discountGroupId = discountGroupId;
    }

    public Long getTelecomServiceId() {
        return telecomServiceId;
    }

    public void setTelecomServiceId(Long telecomServiceId) {
        this.telecomServiceId = telecomServiceId;
    }
}