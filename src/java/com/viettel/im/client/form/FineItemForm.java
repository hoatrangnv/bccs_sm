/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.viettel.im.database.BO.FineItems;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author TheTM
 */
public class FineItemForm extends ActionForm {

    private Long fineItemsId;
    private String fineItemsName;
    private Long status;
    private String description;
    private Long telecomServiceId;
    private String telecomServiceName;
    private Boolean checkStatus;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getFineItemsId() {
        return fineItemsId;
    }

    public void setFineItemsId(Long fineItemsId) {
        this.fineItemsId = fineItemsId;
    }

    public String getFineItemsName() {
        return fineItemsName;
    }

    public void setFineItemsName(String fineItemsName) {
        this.fineItemsName = fineItemsName;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getTelecomServiceId() {
        return telecomServiceId;
    }

    public void setTelecomServiceId(Long telecomServiceId) {
        this.telecomServiceId = telecomServiceId;
    }

    public String getTelecomServiceName() {
        return telecomServiceName;
    }

    public void setTelecomServiceName(String telecomServiceName) {
        this.telecomServiceName = telecomServiceName;
    }

    public Boolean getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Boolean checkStatus) {
        this.checkStatus = checkStatus;
    }


    public void copyDataFromBO(FineItems fineItems) {
        this.setFineItemsId(fineItems.getFineItemsId());
        this.setFineItemsName(fineItems.getFineItemsName());
        this.setDescription(fineItems.getDescription());
        this.setStatus(fineItems.getStatus());
        this.setTelecomServiceId(fineItems.getTelecomServiceId());

    }

    public void copyDataToBO(FineItems fineItems) {
        fineItems.setFineItemsId(this.getFineItemsId());
        fineItems.setFineItemsName(this.getFineItemsName());
        fineItems.setStatus(this.getStatus());
        fineItems.setDescription(this.getDescription());
        fineItems.setTelecomServiceId(this.getTelecomServiceId());

    }

    public void resetForm() {
        this.setFineItemsId(0L);
        this.setFineItemsName("");
        this.setStatus(null); 
        this.setDescription("");
        this.setTelecomServiceId(0L); 
    }
}


