/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import org.apache.struts.action.ActionForm;

/**
 * @author dattv
 * @author tungtv
 * @author anhlt
 * @date 16/03/2009
 * @date 22/03/2009
 *
 * modified tamdt1, 16/06/2009
 *
 */
public class DistributeIsdnForm extends ActionForm {
    private Long formId;
    private Long impType; //kieu nhap (theo file, theo dai so)
    //
    private Long serviceType;
    private String isdnType;
    private Long status;
    private String startIsdn;
    private String endIsdn;
    private Long countIsdn;
    //
    private Long fromShopId;
    private String fromShopCode;
    private String fromShopName;
    //
    private Long stockModelId;
    private String stockModelCode;
    private String stockModelName;
    //
    private Long shopId;
    private String shopCode;
    private String shopName;
    private String newIsdnType;
    //
    private String serverFileName; //ten file tren server trong truogn hop upload
    private String clientFileName; //ten file tren client trong truogn hop upload
    //
    private String[] selectedFormId; //mang luu tru cac formId duoc chon
    //
    private String errorMessage; //chuoi luu loi

    public DistributeIsdnForm() {
        resetForm();
    }

    public void resetForm() {
        this.formId = 0L;
        this.impType = 1L;
        this.serviceType = 0L;
        this.isdnType = "";
        this.status = 1L;
        this.startIsdn = "";
        this.endIsdn = "";
        this.countIsdn = 0L;
        this.fromShopId = 0L;
        this.fromShopCode = "";
        this.fromShopName = "";
        this.stockModelId = 0L;
        this.stockModelCode = "";
        this.stockModelName = "";
        this.shopId = 0L;
        this.shopCode = "";
        this.shopName = "";
        this.newIsdnType = "";
        this.serverFileName= "";
        this.clientFileName= "";
        this.errorMessage = "";
    }

    public DistributeIsdnForm(Long formId, Long serviceType, String isdnType, Long status, String startIsdn, String endIsdn, Long countIsdn, Long fromShopId, String fromShopCode, String fromShopName, Long stockModelId, String stockModelCode, String stockModelName) {
        this.formId = formId;
        this.serviceType = serviceType;
        this.isdnType = isdnType;
        this.status = status;
        this.startIsdn = startIsdn;
        this.endIsdn = endIsdn;
        this.countIsdn = countIsdn;
        this.fromShopId = fromShopId;
        this.fromShopCode = fromShopCode;
        this.fromShopName = fromShopName;
        this.stockModelId = stockModelId;
        this.stockModelCode = stockModelCode;
        this.stockModelName = stockModelName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String[] getSelectedFormId() {
        return selectedFormId;
    }

    public void setSelectedFormId(String[] selectedFormId) {
        this.selectedFormId = selectedFormId;
    }

    public String getClientFileName() {
        return clientFileName;
    }

    public void setClientFileName(String clientFileName) {
        this.clientFileName = clientFileName;
    }

    public String getServerFileName() {
        return serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
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

    public String getStockModelName() {
        return stockModelName;
    }

    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }

    public String getEndIsdn() {
        return endIsdn;
    }

    public void setEndIsdn(String endIsdn) {
        this.endIsdn = endIsdn;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public String getFromShopCode() {
        return fromShopCode;
    }

    public void setFromShopCode(String fromShopCode) {
        this.fromShopCode = fromShopCode;
    }

    public String getFromShopName() {
        return fromShopName;
    }

    public void setFromShopName(String fromShopName) {
        this.fromShopName = fromShopName;
    }

    public String getIsdnType() {
        return isdnType;
    }

    public void setIsdnType(String isdnType) {
        this.isdnType = isdnType;
    }

    public String getNewIsdnType() {
        return newIsdnType;
    }

    public void setNewIsdnType(String newIsdnType) {
        this.newIsdnType = newIsdnType;
    }

    public Long getServiceType() {
        return serviceType;
    }

    public void setServiceType(Long serviceType) {
        this.serviceType = serviceType;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getStartIsdn() {
        return startIsdn;
    }

    public void setStartIsdn(String startIsdn) {
        this.startIsdn = startIsdn;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public Long getImpType() {
        return impType;
    }

    public void setImpType(Long impType) {
        this.impType = impType;
    }

    public Long getFromShopId() {
        return fromShopId;
    }

    public void setFromShopId(Long fromShopId) {
        this.fromShopId = fromShopId;
    }

    public Long getCountIsdn() {
        return countIsdn;
    }

    public void setCountIsdn(Long countIsdn) {
        this.countIsdn = countIsdn;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

}
