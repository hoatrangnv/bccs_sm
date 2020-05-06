/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.viettel.im.database.BO.StockReportEmail;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author LeVt1_S
 */
public class StockReportEmailForm extends ActionForm {

    private Long id;
    private String email;
    private String reportType;
    private String description;
    private String status;

    public StockReportEmailForm() {
        id = 0L;
        email = "";
        reportType = "";
        description = "";
        status = "";
    }

    public void copyStockReportEmailFromBO(StockReportEmail stockReportEmail) {
        id = stockReportEmail.getId();
        email = stockReportEmail.getEmail();
        reportType = stockReportEmail.getReportType();
        status = String.valueOf(stockReportEmail.getStatus());
        description = stockReportEmail.getDescription();
    }

    public void copyStockReportEmailToBO(StockReportEmail stockReportEmail) {
        stockReportEmail.setId(id);
        stockReportEmail.setEmail(email.trim());
        stockReportEmail.setReportType(reportType);
        stockReportEmail.setDescription(description.trim());
        stockReportEmail.setStatus(status);

    }

    public void resetForm() {
        email = "";
        reportType = "";
        description = "";
        status = "";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
