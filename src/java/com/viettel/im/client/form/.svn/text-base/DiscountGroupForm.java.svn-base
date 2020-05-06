package com.viettel.im.client.form;

import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.DiscountGroup;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author tamdt1
 * date 24/03/20009
 *
 */
public class DiscountGroupForm extends ActionForm {
    private Long discountGroupId;
    private String name;
    private String notes;
    private String discountPolicy;
    private Long telecomServiceId;
    private Long status;
    private Long discountMethod;

    //phan bo sung phuc vu chuc nang tim kiem
    private Long stockModelId;
    private String stockModelCode;
    private String stockModelName;

    public DiscountGroupForm() {
        resetForm();
    }

    public void copyDataFromBO(DiscountGroup discountGroup){
        this.setDiscountGroupId(discountGroup.getDiscountGroupId());
        this.setName(discountGroup.getName());
        this.setNotes(discountGroup.getNotes());
        this.setDiscountPolicy(discountGroup.getDiscountPolicy());
        this.setTelecomServiceId(discountGroup.getTelecomServiceId());
        this.setStatus(discountGroup.getStatus());
        this.setDiscountMethod(discountGroup.getDiscountMethod());
    }

    public void copyDataToBO(DiscountGroup discountGroup){
        discountGroup.setDiscountGroupId(this.getDiscountGroupId());
        discountGroup.setName(this.getName());
        discountGroup.setNotes(this.getNotes());
        discountGroup.setDiscountPolicy(this.getDiscountPolicy());
        discountGroup.setTelecomServiceId(this.getTelecomServiceId());
        discountGroup.setStatus(this.getStatus());
        discountGroup.setDiscountMethod(this.getDiscountMethod());
    }

    public void resetForm() {
        discountGroupId = 0L;
        name = "";
        notes = "";
        discountPolicy = "";
        telecomServiceId = 0L;
        status = 1L;
        discountMethod = Constant.DISCOUNT_METHOD_AMOUNT;

        stockModelId = 0L;
        stockModelCode = "";
        stockModelName = "";
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
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

    public String getStockModelName() {
        return stockModelName;
    }

    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }

    public String getDiscountPolicy() {
        return discountPolicy;
    }

    public void setDiscountPolicy(String discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public Long getTelecomServiceId() {
        return telecomServiceId;
    }

    public void setTelecomServiceId(Long telecomServiceId) {
        this.telecomServiceId = telecomServiceId;
    }

    

    public Long getDiscountGroupId() {
        return discountGroupId;
    }

    public void setDiscountGroupId(Long discountGroupId) {
        this.discountGroupId = discountGroupId;
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

    public Long getDiscountMethod() {
        return discountMethod;
    }

    public void setDiscountMethod(Long discountMethod) {
        this.discountMethod = discountMethod;
    }

}
