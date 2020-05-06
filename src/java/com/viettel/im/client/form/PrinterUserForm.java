/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.viettel.im.database.BO.PrinterUser;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author TheTM
 */
public class PrinterUserForm extends ActionForm {

    private Long id;
    private String userName;
    private String ipAddress;
    private String XAmplitude;
    private String YAmplitude;
    private Long invoiceType;
    private String serialCode;

    public String getXAmplitude() {
        return XAmplitude;
    }

    public void setXAmplitude(String XAmplitude) {
        this.XAmplitude = XAmplitude;
    }

    public String getYAmplitude() {
        return YAmplitude;
    }

    public void setYAmplitude(String YAmplitude) {
        this.YAmplitude = YAmplitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Long invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSerialCode() {
        return serialCode;
    }

    public void setSerialCode(String serialCode) {
        this.serialCode = serialCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void resetForm() {
        this.XAmplitude = "";
        this.YAmplitude = "";
        this.invoiceType = null;
        this.serialCode = null;
        this.ipAddress = null;
        this.userName = null;
        this.id= null; 
    }

    public void copyDataFromBO(PrinterUser printerUser) {
        this.setId(printerUser.getId());
        this.setSerialCode(printerUser.getSerialCode());
        this.setUserName(printerUser.getUserName());
        this.setInvoiceType(printerUser.getInvoiceType());
        this.setIpAddress(printerUser.getIpAddress()); 
        this.setXAmplitude(printerUser.getXAmplitude() == null ? "" : String.valueOf(printerUser.getXAmplitude()));
        this.setYAmplitude(printerUser.getYAmplitude() == null ? "" : String.valueOf(printerUser.getYAmplitude()));
    }

    public void copyDataToBO(PrinterUser printerUser) {
        printerUser.setId(this.getId());
        printerUser.setSerialCode(this.getSerialCode());
        printerUser.setUserName(this.getUserName());
        if (this.getXAmplitude() != null && !this.getXAmplitude().trim().equals("")) {
            printerUser.setXAmplitude(Long.parseLong(this.getXAmplitude().trim()));
        }
        if (this.getYAmplitude() != null && !this.getYAmplitude().trim().equals("")) {
            printerUser.setYAmplitude(Long.parseLong(this.getYAmplitude().trim()));
        }
        printerUser.setInvoiceType(this.getInvoiceType());
        printerUser.setIpAddress(this.getIpAddress());
    }
}
