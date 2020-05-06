/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.BO;

import java.util.Date;

/**
 *
 * @author may01
 */
public class AjustLimitBO {
    private Long ownerId;
    private Long ownerType;
    private Long stockTypeId;
    private String oldDebitType;
    private String newDebitType;
    private Long requestDebitType;
    private Date lastUpdateDate;
    private String lastUpdateUser;
    private String note;

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(Long ownerType) {
        this.ownerType = ownerType;
    }

    public Long getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public String getOldDebitType() {
        return oldDebitType;
    }

    public void setOldDebitType(String oldDebitType) {
        this.oldDebitType = oldDebitType;
    }

    public String getNewDebitType() {
        return newDebitType;
    }

    public void setNewDebitType(String newDebitType) {
        this.newDebitType = newDebitType;
    }

    public Long getRequestDebitType() {
        return requestDebitType;
    }

    public void setRequestDebitType(Long requestDebitType) {
        this.requestDebitType = requestDebitType;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
}
