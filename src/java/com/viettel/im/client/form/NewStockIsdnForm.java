/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import org.apache.struts.validator.ValidatorForm;

/**
 * @author dattv
 * @author tungtv
 * @date 16/03/2009
 */
public class NewStockIsdnForm extends ValidatorForm {

    //
    private String serviceType;
    private String startStockIsdn;
    private String endStockIsdn;
    private Long quantityIsdn;
    private String location;
    private String stockPstnDistrict;

    public NewStockIsdnForm() {
        resetForm();
    }

    public void resetForm() {
        serviceType = "";
        startStockIsdn = "";
        endStockIsdn = "";
        location = "";
        stockPstnDistrict = "";
    }


    /**
     * @return the serviceType
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * @param serviceType the serviceType to set
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getEndStockIsdn() {
        return endStockIsdn;
    }

    public void setEndStockIsdn(String endStockIsdn) {
        this.endStockIsdn = endStockIsdn;
    }

    public String getStartStockIsdn() {
        return startStockIsdn;
    }

    public void setStartStockIsdn(String startStockIsdn) {
        this.startStockIsdn = startStockIsdn;
    }

    public Long getQuantityIsdn() {
        return quantityIsdn;
    }

    public void setQuantityIsdn(Long quantityIsdn) {
        this.quantityIsdn = quantityIsdn;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStockPstnDistrict() {
        return stockPstnDistrict;
    }

    public void setStockPstnDistrict(String stockPstnDistrict) {
        this.stockPstnDistrict = stockPstnDistrict;
    }
    
}
