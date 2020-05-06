/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import java.util.Date;

/**
 *
 * @author mov_itbl_dinhdc
 */
public class ManagementActionOfUserForm {
    
    private Date fromDate;
    private Date toDate;
    private String typeReport;

    public ManagementActionOfUserForm() {
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getTypeReport() {
        return typeReport;
    }

    public void setTypeReport(String typeReport) {
        this.typeReport = typeReport;
    }
    
}
