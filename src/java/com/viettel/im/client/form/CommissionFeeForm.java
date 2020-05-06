/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.form;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author DatPV
 */
public class CommissionFeeForm  extends ActionForm{
    private String commItem;
    private String channelType;
    private String status;
    private String dateStart;
    private String dateEnd;
    private String fee;
    private String vat;
    private String dateInput;
    private String staffInput;

    public String getDateInput() {
        return dateInput;
    }

    public void setDateInput(String dateInput) {
        this.dateInput = dateInput;
    }

    public String getStaffInput() {
        return staffInput;
    }

    public void setStaffInput(String staffInput) {
        this.staffInput = staffInput;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getCommItem() {
        return commItem;
    }

    public void setCommItem(String commItem) {
        this.commItem = commItem;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }
}

