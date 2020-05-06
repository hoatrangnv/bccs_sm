/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.FineItemPrice;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author TheTM
 */
public class FineItemsPriceForm extends ActionForm {

    private Long fineItemPriceId;
    private Long fineItemId;
    private Long status;
    private String description;
    private String staDate;
    private String endDate;
    private Long vat;
    private Date createDate;
    private String userName;
    private Long price;
    private Boolean checkStatus;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getFineItemId() {
        return fineItemId;
    }

    public void setFineItemId(Long fineItemId) {
        this.fineItemId = fineItemId;
    }

    public Long getFineItemPriceId() {
        return fineItemPriceId;
    }

    public void setFineItemPriceId(Long fineItemPriceId) {
        this.fineItemPriceId = fineItemPriceId;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getStaDate() {
        return staDate;
    }

    public void setStaDate(String staDate) {
        this.staDate = staDate;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getVat() {
        return vat;
    }

    public void setVat(Long vat) {
        this.vat = vat;
    }

  
    public Boolean getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Boolean checkStatus) {
        this.checkStatus = checkStatus;
    }

    
    public void resetForm() {
        this.setFineItemPriceId(0L);
        this.setFineItemId(0L);
        this.setStatus(null);
        this.setDescription("");
        this.setStaDate(null);
        this.setEndDate(null);
        this.setVat(null);
        this.setPrice(null);
        this.setUserName("");
        this.setCreateDate(null);
    }

    public void copyDataFromBO(FineItemPrice fineItemPrice) {
        this.setFineItemPriceId(fineItemPrice.getFineItemPriceId());
        this.setFineItemId(fineItemPrice.getFineItemId());
        this.setStatus(fineItemPrice.getStatus());
        this.setDescription(fineItemPrice.getDescription());
        this.setVat(fineItemPrice.getVat());
        this.setPrice(fineItemPrice.getPrice());
        this.setUserName(fineItemPrice.getUserName());
        try {
            this.setStaDate(DateTimeUtils.convertDateToString(fineItemPrice.getStaDate()));
        } catch (Exception ex) {
            this.setStaDate("");
        }
        try {
            this.setEndDate(DateTimeUtils.convertDateToString(fineItemPrice.getEndDate()));
        } catch (Exception ex) {
            this.setEndDate("");
        }
        this.setCreateDate(fineItemPrice.getCreateDate());
    }

    public void copyDataToBO(FineItemPrice fineItemPrice) {
        fineItemPrice.setFineItemPriceId(this.getFineItemPriceId());
        fineItemPrice.setFineItemId(this.getFineItemId());
        fineItemPrice.setStatus(this.getStatus());
        fineItemPrice.setDescription(this.getDescription());
        fineItemPrice.setVat(this.getVat());
        fineItemPrice.setPrice(this.getPrice());
        fineItemPrice.setUserName(this.getUserName());
        try {
            fineItemPrice.setStaDate(DateTimeUtils.convertStringToDate(this.getStaDate()));
        } catch (Exception ex) {
            fineItemPrice.setStaDate(new Date());
        }
        try {
            fineItemPrice.setEndDate(DateTimeUtils.convertStringToDate(this.getEndDate()));
        } catch (Exception ex) {
            fineItemPrice.setEndDate(new Date());
        }
        fineItemPrice.setCreateDate(this.getCreateDate());
    }
}
