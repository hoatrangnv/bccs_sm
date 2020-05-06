package com.viettel.im.database.BO;

import java.util.List;

/**
 * SaleServices entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class SaleServices implements java.io.Serializable {

    // Fields
    private Long saleServicesId;
    private String code;
    private String name;
    private Long status;
    private String notes;
    private Long telecomServicesId;
    private Long saleType;
    
    private String accountingModelCode;
    private String accountingModelName;

    //tree
    private List<SaleServicesModel> listSaleServicesModels;
    //

    // Constructors
    /** default constructor */
    public SaleServices() {
    }

    /** minimal constructor */
    public SaleServices(Long saleServicesId, String code, String name,
            Long status) {
        this.saleServicesId = saleServicesId;
        this.code = code;
        this.name = name;
        this.status = status;
    }

    /** full constructor */
    public SaleServices(Long saleServicesId, String code, String name,
            Long status, String notes) {
        this.saleServicesId = saleServicesId;
        this.code = code;
        this.name = name;
        this.status = status;
        this.notes = notes;
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

    // Property accessors
    public Long getTelecomServicesId() {
        return telecomServicesId;
    }

    public void setTelecomServicesId(Long telecomServicesId) {
        this.telecomServicesId = telecomServicesId;
    }

    public List<SaleServicesModel> getListSaleServicesModels() {
        return listSaleServicesModels;
    }

    public void setListSaleServicesModels(List<SaleServicesModel> listSaleServicesModels) {
        this.listSaleServicesModels = listSaleServicesModels;
    }

    public Long getSaleServicesId() {
        return this.saleServicesId;
    }

    public void setSaleServicesId(Long saleServicesId) {
        this.saleServicesId = saleServicesId;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getSaleType() {
        return saleType;
    }

    public void setSaleType(Long saleType) {
        this.saleType = saleType;
    }
    
}