package com.viettel.im.client.form;

import com.viettel.im.client.form.*;

/**
 * SaleTransUpdateBean entity.
 *
 * @author MyEclipse Persistence Tools
 */

public class UpdateSaleTransDetailForm implements java.io.Serializable {

    // Fields

    private Long saleTransId;
    private Long saleTransDetailId;
    private Long stockTypeId;
    private String stockTypeName;
    private String tableName;
    private Long stockModelId;
    private String stockModelCode;
    private String stockModelName;
    private Long priceId;    
    private Long price;
    private Long vat;
    private Long amountVat;
    private Long quantity;
    private Long amount;
    private String serial;
    private Long isExists;


    /** full constructor */
    public UpdateSaleTransDetailForm(Long saleTransDetailId, Long stockTypeId, String stockTypeName, String tableName, Long stockModelId, String stockModelCode, String stockModelName, Long price, Long quantity, Long amount, String serial, Long isExists) {
        this.saleTransDetailId = saleTransDetailId;
        this.stockTypeId = stockTypeId;
        this.stockTypeName = stockTypeName;
        this.tableName = tableName;
        this.stockModelId = stockModelId;
        this.stockModelCode = stockModelCode;
        this.stockModelName = stockModelName;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
        this.serial = serial;
        this.isExists = isExists;
    }

    // Constructors

    /** default constructor */
    public UpdateSaleTransDetailForm() {
    }

    public UpdateSaleTransDetailForm(Long stockModelId, String stockModelName) {
        this.stockModelId = stockModelId;
        this.stockModelName = stockModelName;        
    }

    public Long getSaleTransId() {
        return saleTransId;
    }

    public void setSaleTransId(Long saleTransId) {
        this.saleTransId = saleTransId;
    }

    
    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public Long getVat() {
        return vat;
    }

    public void setVat(Long vat) {
        this.vat = vat;
    }

    public Long getAmountVat() {
        return amountVat;
    }

    public void setAmountVat(Long amountVat) {
        this.amountVat = amountVat;
    }
    
    public Long getIsExists() {
        return isExists;
    }

    public void setIsExists(Long isExists) {
        this.isExists = isExists;
    }

    public String getStockTypeName() {
        return stockTypeName;
    }

    public void setStockTypeName(String stockTypeName) {
        this.stockTypeName = stockTypeName;
    }
    
    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
 
    public String getStockModelName() {
        return stockModelName;
    }

    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getSaleTransDetailId() {
        return saleTransDetailId;
    }

    public void setSaleTransDetailId(Long saleTransDetailId) {
        this.saleTransDetailId = saleTransDetailId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getStockModelCode() {
        return stockModelCode;
    }

    public void setStockModelCode(String stockModelCode) {
        this.stockModelCode = stockModelCode;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public Long getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


        
}

