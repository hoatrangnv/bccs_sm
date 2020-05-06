package com.viettel.im.client.form;

import com.viettel.im.database.BO.SaleServices;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author tamdt1
 * date 14/03/2009
 *
 */
public class SaleServicesForm extends ActionForm {
    //tuong ung cac truong trong DB
	private Long saleServicesId;
	private String code;
	private String name;
    private Long status;
	private String notes;
    private Long telecomServicesId;

    private String accountingModelCode;
    private String accountingModelName;
    //TruongNQ5 20140725 R6237
    private Long rvnServiceId;
    private Long rvnServiceQualityId;
    private Long serviceIndex;
    private Long serviceQualityIndex;

    public Long getServiceIndex() {
        return serviceIndex;
    }

    public void setServiceIndex(Long serviceIndex) {
        this.serviceIndex = serviceIndex;
    }

    public Long getServiceQualityIndex() {
        return serviceQualityIndex;
    }

    public void setServiceQualityIndex(Long serviceQualityIndex) {
        this.serviceQualityIndex = serviceQualityIndex;
    }

    public Long getRvnServiceId() {
        return rvnServiceId;
    }

    public void setRvnServiceId(Long rvnServiceId) {
        this.rvnServiceId = rvnServiceId;
    }

    public Long getRvnServiceQualityId() {
        return rvnServiceQualityId;
    }

    public void setRvnServiceQualityId(Long rvnServiceQualityId) {
        this.rvnServiceQualityId = rvnServiceQualityId;
    }

    //End TruongNQ5
    public SaleServicesForm() {
        saleServicesId = 0L;
        code = "";
        name = "";
        status = 1L;
        notes = "";
        telecomServicesId = 0L;
        
        accountingModelCode = "";
        accountingModelName = "";
    }

    public void copyDataFromBO(SaleServices saleServices){
        this.setSaleServicesId(saleServices.getSaleServicesId());
        this.setCode(saleServices.getCode());
        this.setName(saleServices.getName());
        this.setStatus(saleServices.getStatus());
        this.setNotes(saleServices.getNotes());
        this.setTelecomServicesId(saleServices.getTelecomServicesId());
        
        this.setAccountingModelCode(saleServices.getAccountingModelCode());
        this.setAccountingModelName(saleServices.getAccountingModelName());
        
    }

    public void copyDataToBO(SaleServices saleServices){
        saleServices.setSaleServicesId(this.getSaleServicesId());
        saleServices.setCode(this.getCode());
        saleServices.setName(this.getName());
        saleServices.setStatus(this.getStatus());
        saleServices.setNotes(this.getNotes());
        saleServices.setTelecomServicesId(this.getTelecomServicesId());
        
        
        saleServices.setAccountingModelCode(this.getAccountingModelCode());
        saleServices.setAccountingModelName(this.getAccountingModelName());
    }

    public void resetForm(){
        saleServicesId = 0L;
        code = "";
        name = "";
        status = 1L;
        notes = "";
        telecomServicesId = 0L;
        
        accountingModelCode = "";
        accountingModelName = "";
    }

    public Long getTelecomServicesId() {
        return telecomServicesId;
    }

    public void setTelecomServicesId(Long telecomServicesId) {
        this.telecomServicesId = telecomServicesId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getSaleServicesId() {
        return saleServicesId;
    }

    public void setSaleServicesId(Long saleServicesId) {
        this.saleServicesId = saleServicesId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
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

