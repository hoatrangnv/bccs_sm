/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.form;

import com.viettel.im.database.BO.DebitType;

/**
 * @since 23/04/2013
 * @author linhnt28
 */
public class DebitTypeForm {
    private Long debitTypeId;
    private String limit;
    private String type;
    private Long value;
    private String message;
    private Long status;

    public void resetForm() {
        this.debitTypeId = null;
        this.limit = "";
        this.type = "";
        this.value = null;
        this.message = "";
    }
    
    public void copyDataToForm(DebitType debitType) {
        this.debitTypeId = debitType.getId();
        this.limit = debitType.getDebitType();
        this.type = debitType.getDebitDayType();
        this.value = debitType.getMaxDebit();
        status = debitType.getStatus();
    }

    public void copyDataToBO(DebitType debitType) {
        debitType.setId(this.debitTypeId);
        debitType.setDebitType(this.limit);
        debitType.setDebitDayType(this.type);
        debitType.setMaxDebit(this.value);
        debitType.setStatus(this.status == null || (this.status != 1 && this.status != 0) ? 0 : this.status);
    }
    public DebitTypeForm() {
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getDebitTypeId() {
        return debitTypeId;
    }

    public void setDebitTypeId(Long debitTypeId) {
        this.debitTypeId = debitTypeId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}
