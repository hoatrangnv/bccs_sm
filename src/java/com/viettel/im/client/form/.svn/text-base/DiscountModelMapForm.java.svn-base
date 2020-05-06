/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.form;

import com.viettel.im.database.BO.DiscountModelMap;
import java.util.Date;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author Doan Thanh 8
 */
public class DiscountModelMapForm extends ActionForm {
    // Fields
    private Long discountModelMapId;
    private Long discountGroupId;
    private Long stockModelId;
    private Date staDate;
    private Date endDate;
    private Long status;

    //cac thuoc tinh bo sung
    private Long stockTypeId;
    private String stockModelCode;
    private String stockModelName;

    public void copyDataFromBO(DiscountModelMap discountModelMap) {
        this.setDiscountModelMapId(discountModelMap.getDiscountModelMapId());
        this.setDiscountGroupId(discountModelMap.getDiscountGroupId());
        this.setStockModelId(discountModelMap.getStockModelId());
        this.setStaDate(discountModelMap.getStaDate());
        this.setEndDate(discountModelMap.getEndDate());
        this.setStatus(discountModelMap.getStatus());
        this.setStockTypeId(discountModelMap.getStockTypeId());
        this.setStockModelCode(discountModelMap.getStockModelCode());
        this.setStockModelName(discountModelMap.getStockModelName());
    }

    public void copyDataToBO(DiscountModelMap discountModelMap) {
        discountModelMap.setDiscountModelMapId(this.getDiscountModelMapId());
        discountModelMap.setDiscountGroupId(this.getDiscountGroupId());
        discountModelMap.setStockModelId(this.getStockModelId());
        discountModelMap.setStaDate(this.getStaDate());
        discountModelMap.setEndDate(this.getEndDate());
        discountModelMap.setStatus(this.getStatus());
        discountModelMap.setStockModelCode(this.getStockModelCode());
        discountModelMap.setStockModelName(this.getStockModelName());
    }

    public void resetForm() {
        discountModelMapId = 0L;
        discountGroupId = 0L;
        stockModelId = 0L;
        status = 1L;
        stockTypeId = 0L;
        stockModelCode = "";
        stockModelName = "";
    }


    public DiscountModelMapForm() {
    }

    public Long getDiscountGroupId() {
        return discountGroupId;
    }

    public Long getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public void setDiscountGroupId(Long discountGroupId) {
        this.discountGroupId = discountGroupId;
    }

    public Long getDiscountModelMapId() {
        return discountModelMapId;
    }

    public void setDiscountModelMapId(Long discountModelMapId) {
        this.discountModelMapId = discountModelMapId;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStaDate() {
        return staDate;
    }

    public void setStaDate(Date staDate) {
        this.staDate = staDate;
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
}
