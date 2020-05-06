package com.viettel.im.client.form;

import java.util.List;

/**
 *
 * @author Doan Thanh 8
 * form cap nhat mat hang cho so isdn
 *
 */
public class AssignStockModelForIsdnForm {
    private Long formId;
    private Long impType; //kieu nhap (theo file, theo dai so)

    //
    private Long stockTypeId;
    private Long shopId;
    private String shopCode;
    private String shopName;
    private Long stockModelId;
    private String stockModelCode;
    private String stockModelName;
    private String fromIsdn;
    private String toIsdn;
    private Long countIsdn;
    private String fromPrice;
    private String toPrice;
    private String groupFilterRuleCode;
    private Long filterTypeId;
    private Long rulesId;
    //thogn tin ve stockModel moi
    private Long newStockModelId;
    private String newStockModelCode;
    private String newStockModelName;
    //
    private String serverFileName; //ten file tren server trong truogn hop upload
    private String clientFileName; //ten file tren client trong truogn hop upload
    //
    private String[] selectedFormId; //mang luu tru cac formId duoc chon
    //
    private String errorMessage; //chuoi luu loi

    public AssignStockModelForIsdnForm() {
        resetForm();
    }
    
    public AssignStockModelForIsdnForm(Long formId, Long stockTypeId, String fromIsdn, String toIsdn, Long countIsdn, String fromPrice, String toPrice, Long shopId, String shopCode, String shopName, Long stockModelId, String stockModelCode, String stockModelName) {
        this.formId = formId;
        this.stockTypeId = stockTypeId;
        this.fromIsdn = fromIsdn;
        this.toIsdn = toIsdn;
        this.countIsdn = countIsdn;
        this.fromPrice = fromPrice;
        this.toPrice = toPrice;
        this.shopId = shopId;
        this.shopCode = shopCode;
        this.shopName = shopName;
        this.stockModelId = stockModelId;
        this.stockModelCode = stockModelCode;
        this.stockModelName = stockModelName;
    }

    public void resetForm() {
        this.formId = 0L;
        this.impType = 2L;
        this.stockTypeId = 0L;
        this.fromIsdn = "";
        this.toIsdn = "";
        this.fromPrice = "";
        this.toPrice = "";
        this.countIsdn = 0L;
        this.shopId = 0L;
        this.shopCode = "";
        this.shopName = "";
        this.stockModelId = 0L;
        this.stockModelCode = "";
        this.stockModelName = "";
        this.newStockModelId = 0L;
        this.newStockModelCode = "";
        this.newStockModelName = "";
        this.serverFileName= "";
        this.clientFileName= "";
        this.errorMessage = "";
    }

    public void copyProperties(AssignStockModelForIsdnForm sourceObject) {
        if (sourceObject != null) {
            this.setFromIsdn(sourceObject.getFromIsdn());
            this.setToIsdn(sourceObject.getToIsdn());
            this.setStockTypeId(sourceObject.getStockTypeId());
            this.setShopCode(sourceObject.getShopCode());
            this.setStockModelCode(sourceObject.getStockModelCode());
            this.setFromPrice(sourceObject.getFromPrice());
            this.setToPrice(sourceObject.getToPrice());
        }
    }

    public Long getFilterTypeId() {
        return filterTypeId;
    }

    public void setFilterTypeId(Long filterTypeId) {
        this.filterTypeId = filterTypeId;
    }

    public String getFromIsdn() {
        return fromIsdn;
    }

    public void setFromIsdn(String fromIsdn) {
        this.fromIsdn = fromIsdn;
    }

    public String getFromPrice() {
        return fromPrice;
    }

    public void setFromPrice(String fromPrice) {
        this.fromPrice = fromPrice;
    }

    public String getToPrice() {
        return toPrice;
    }

    public void setToPrice(String toPrice) {
        this.toPrice = toPrice;
    }

    public String getGroupFilterRuleCode() {
        return groupFilterRuleCode;
    }

    public void setGroupFilterRuleCode(String groupFilterRuleCode) {
        this.groupFilterRuleCode = groupFilterRuleCode;
    }

    public Long getRulesId() {
        return rulesId;
    }

    public void setRulesId(Long rulesId) {
        this.rulesId = rulesId;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public Long getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public String getToIsdn() {
        return toIsdn;
    }

    public void setToIsdn(String toIsdn) {
        this.toIsdn = toIsdn;
    }

    public Long getNewStockModelId() {
        return newStockModelId;
    }

    public void setNewStockModelId(Long newStockModelId) {
        this.newStockModelId = newStockModelId;
    }

    public String getClientFileName() {
        return clientFileName;
    }

    public void setClientFileName(String clientFileName) {
        this.clientFileName = clientFileName;
    }

    public Long getCountIsdn() {
        return countIsdn;
    }

    public void setCountIsdn(Long countIsdn) {
        this.countIsdn = countIsdn;
    }

    public Long getImpType() {
        return impType;
    }

    public void setImpType(Long impType) {
        this.impType = impType;
    }

    public String getServerFileName() {
        return serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public String getNewStockModelCode() {
        return newStockModelCode;
    }

    public void setNewStockModelCode(String newStockModelCode) {
        this.newStockModelCode = newStockModelCode;
    }

    public String getNewStockModelName() {
        return newStockModelName;
    }

    public void setNewStockModelName(String newStockModelName) {
        this.newStockModelName = newStockModelName;
    }

    public String[] getSelectedFormId() {
        return selectedFormId;
    }

    public void setSelectedFormId(String[] selectedFormId) {
        this.selectedFormId = selectedFormId;
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
    
}
