/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.form;

/**
 *
 * @author Doan Thanh 8
 */
public class AssignChannelTypeForSerialForm {
    private Long formId;
    private Long impType; //kieu nhap (theo file, theo dai so)
    //
    private Long channelTypeId;
    private String channelTypeName;
    private Long shopId;
    private String shopCode;
    private String shopName;
    private Long stockTypeId;
    private Long stockModelId;
    private String stockModelCode;
    private String stockModelName;
    private String fromSerial;
    private String toSerial;
    private Long countSerial;
    //thong tin ve channelTypeId moi
    private Long newChannelTypeId;
    private String newChannelTypeName;
    //
    private String serverFileName; //ten file tren server trong truogn hop upload
    private String clientFileName; //ten file tren client trong truogn hop upload
    //
    private String[] selectedFormId; //mang luu tru cac formId duoc chon
    //
    private String errorMessage; //chuoi luu loi
    private String shopCodeNew; //haint41 3/10/2011 : shopCode khi chon nhap du lieu theo file
    private String shopNameNew; //haint41 3/10/2011 : shopname khi chon nhap du lieu theo file

    public String getShopNameNew() {
        return shopNameNew;
    }

    public void setShopNameNew(String shopNameNew) {
        this.shopNameNew = shopNameNew;
    }
    
    public String getShopCodeNew() {
        return shopCodeNew;
    }

    public void setShopCodeNew(String shopCodeNew) {
        this.shopCodeNew = shopCodeNew;
    }
    
    public AssignChannelTypeForSerialForm() {
    }

    public AssignChannelTypeForSerialForm(Long formId, Long stockTypeId, String fromSerial, String toSerial, Long countSerial, Long shopId, String shopCode, String shopName, Long stockModelId, String stockModelCode, String stockModelName, Long channelTypeId, String channelTypeName) {
        this.formId = formId;
        this.stockTypeId = stockTypeId;
        this.fromSerial = fromSerial;
        this.toSerial = toSerial;
        this.countSerial = countSerial;
        this.shopId = shopId;
        this.shopCode = shopCode;
        this.shopName = shopName;
        this.stockModelId = stockModelId;
        this.stockModelCode = stockModelCode;
        this.stockModelName = stockModelName;
        this.channelTypeId = channelTypeId;
        this.channelTypeName = channelTypeName;
    }




    public void resetForm() {
        this.formId = 0L;
        this.impType = 2L;
        this.channelTypeId = 0L;
        this.channelTypeName = "";
        this.fromSerial = "";
        this.toSerial = "";
        this.countSerial = 0L;
        this.stockTypeId = 0L;
        this.shopId = 0L;
        this.shopCode = "";
        this.shopName = "";
        this.stockModelId = 0L;
        this.stockModelCode = "";
        this.stockModelName = "";
        this.newChannelTypeId = 0L;
        this.serverFileName= "";
        this.clientFileName= "";
        this.errorMessage = "";
    }

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public String getClientFileName() {
        return clientFileName;
    }

    public void setClientFileName(String clientFileName) {
        this.clientFileName = clientFileName;
    }

    public Long getCountSerial() {
        return countSerial;
    }

    public void setCountSerial(Long countSerial) {
        this.countSerial = countSerial;
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

    public String getFromSerial() {
        return fromSerial;
    }

    public void setFromSerial(String fromSerial) {
        this.fromSerial = fromSerial;
    }

    public Long getImpType() {
        return impType;
    }

    public void setImpType(Long impType) {
        this.impType = impType;
    }

    public Long getNewChannelTypeId() {
        return newChannelTypeId;
    }

    public void setNewChannelTypeId(Long newChannelTypeId) {
        this.newChannelTypeId = newChannelTypeId;
    }

    public String[] getSelectedFormId() {
        return selectedFormId;
    }

    public void setSelectedFormId(String[] selectedFormId) {
        this.selectedFormId = selectedFormId;
    }

    public String getServerFileName() {
        return serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
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

    public String getStockModelCode() {
        return stockModelCode;
    }

    public void setStockModelCode(String stockModelCode) {
        this.stockModelCode = stockModelCode;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public String getStockModelName() {
        return stockModelName;
    }

    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }

    public Long getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public String getToSerial() {
        return toSerial;
    }

    public void setToSerial(String toSerial) {
        this.toSerial = toSerial;
    }

    public String getChannelTypeName() {
        return channelTypeName;
    }

    public void setChannelTypeName(String channelTypeName) {
        this.channelTypeName = channelTypeName;
    }

    public String getNewChannelTypeName() {
        return newChannelTypeName;
    }

    public void setNewChannelTypeName(String newChannelTypeName) {
        this.newChannelTypeName = newChannelTypeName;
    }

    
    

}
