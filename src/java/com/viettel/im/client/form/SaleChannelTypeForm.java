package com.viettel.im.client.form;

import com.viettel.im.database.BO.ChannelType;
import org.apache.struts.action.ActionForm;
import com.viettel.im.common.util.NumberUtils;

/**
 *
 * @author tamdt1
 * date 17/02/2009
 * form chua cac thong tin ve loai kenh ban hang
 * modify by Cuongnt
 */
public class SaleChannelTypeForm extends ActionForm {

    private Long channelTypeId;
    private String name;
    private Long status;
    private String objectType;
    private String isVtUnit;
    private Long stockType;
    private String checkComm;
    private String stockReportTemplate;
    // tutm1 bo sung phan han muc
    private Double debitDefault;
    private Double rateDebit;
    private String debitDefaultStr;
    private String rateDebitStr;
    // them phan ma~ kenh
    private String code;
    private String discountPolicyDefault;
    private String pricePolicyDefault;
    private Long isPrivate;

    public SaleChannelTypeForm() {
    }

    public String getDiscountPolicyDefault() {
        return discountPolicyDefault;
    }

    public void setDiscountPolicyDefault(String discountPolicyDefault) {
        this.discountPolicyDefault = discountPolicyDefault;
    }

    public String getPricePolicyDefault() {
        return pricePolicyDefault;
    }

    public void setPricePolicyDefault(String pricePolicyDefault) {
        this.pricePolicyDefault = pricePolicyDefault;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public String getCheckComm() {
        return checkComm;
    }

    public void setCheckComm(String checkComm) {
        this.checkComm = checkComm;
    }

    public String getIsVtUnit() {
        return isVtUnit;
    }

    public void setIsVtUnit(String isVtUnit) {
        this.isVtUnit = isVtUnit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getStockReportTemplate() {
        return stockReportTemplate;
    }

    public void setStockReportTemplate(String stockReportTemplate) {
        this.stockReportTemplate = stockReportTemplate;
    }

    public Long getStockType() {
        return stockType;
    }

    public void setStockType(Long stockType) {
        this.stockType = stockType;
    }

    public Double getDebitDefault() {
        return debitDefault;
    }

    public void setDebitDefault(Double debitDefault) {
        this.debitDefault = debitDefault;
    }


    public Double getRateDebit() {
        return rateDebit;
    }

    public void setRateDebit(Double rateDebit) {
        this.rateDebit = rateDebit;
    }

    public String getDebitDefaultStr() {
        return debitDefaultStr;
    }

    public void setDebitDefaultStr(String debitDefaultStr) {
        this.debitDefaultStr = debitDefaultStr;
    }

    public String getRateDebitStr() {
        return rateDebitStr;
    }

    public void setRateDebitStr(String rateDebitStr) {
        this.rateDebitStr = rateDebitStr;
    }

    public Long getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Long isPrivate) {
        this.isPrivate = isPrivate;
    }
    

    public void resetForm() {
        this.checkComm = "";
        this.isVtUnit = "";
        this.objectType = "";
        this.status = null;
        this.stockType = null;
        this.stockReportTemplate = "";
        this.name = "";
        this.debitDefault = null;
        this.rateDebit = null;
        this.debitDefaultStr = "";
        this.rateDebitStr = "";
        this.pricePolicyDefault="";
        this.discountPolicyDefault="";
        this.code="";
    }

    public void copyDataToBO(ChannelType channelType) {
        channelType.setChannelTypeId(this.channelTypeId);
        channelType.setCheckComm(this.checkComm);
        channelType.setIsVtUnit(this.isVtUnit);
        channelType.setObjectType(this.objectType);
        channelType.setStatus(this.status);
        channelType.setStockReportTemplate(this.stockReportTemplate.trim());
        channelType.setName(this.name.trim());
        channelType.setStockType(null);
        channelType.setPricePolicyDefault(this.pricePolicyDefault);
        channelType.setDiscountPolicyDefault(discountPolicyDefault);
    }

    public void copyDataFromBO(ChannelType channelType) {
        this.checkComm = channelType.getCheckComm();
        this.isVtUnit = channelType.getIsVtUnit();
        this.objectType = channelType.getObjectType();
        this.status = channelType.getStatus();
        this.stockType = channelType.getStockType();
        this.stockReportTemplate = channelType.getStockReportTemplate();
        this.name = channelType.getName();
        this.channelTypeId = channelType.getChannelTypeId();
        this.debitDefault = channelType.getDebitDefault();
        this.rateDebit = channelType.getRateDebit();
        this.pricePolicyDefault = channelType.getPricePolicyDefault();
        this.discountPolicyDefault = channelType.getDiscountPolicyDefault();
        if (debitDefault != null)
//            this.debitDefaultStr = debitDefault.toString();
            this.debitDefaultStr = String.format("%22.5f", debitDefault);
        if (rateDebit != null)
//            this.rateDebitStr = rateDebit.toString();
            this.rateDebitStr = String.format("%22.5f", rateDebit);
    }
    
}
   
