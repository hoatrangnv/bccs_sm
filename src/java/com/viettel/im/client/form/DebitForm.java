/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author NamLT
 */
public class DebitForm extends ValidatorForm {

    private String channelParent;
    private String ownerType;
    private String code;
    private String name;
    private String maxDebit;
    private String dateReset;
    private String currentDebit;
    private String serverFileName;
    private String pathLogFile;
    private String error;
    private String stockId;
    String[] selectedItems;
    private String pathExpFile;
    private String shopCode;
    private String shopName;
    private String status;
    private String parentCode;
    private String channelType;
    private String clientFileName;

    public String getClientFileName() {
        return clientFileName;
    }

    public void setClientFileName(String clientFileName) {
        this.clientFileName = clientFileName;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    private String parentName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPathExpFile() {
        return pathExpFile;
    }

    public void setPathExpFile(String pathExpFile) {
        this.pathExpFile = pathExpFile;
    }

    public String[] getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(String[] selectedItems) {
        this.selectedItems = selectedItems;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPathLogFile() {
        return pathLogFile;
    }

    public void setPathLogFile(String pathLogFile) {
        this.pathLogFile = pathLogFile;
    }

    public String getServerFileName() {
        return serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    public String getCurrentDebit() {
        return currentDebit;
    }

    public void setCurrentDebit(String currentDebit) {
        this.currentDebit = currentDebit;
    }

    public String getChannelParent() {
        return channelParent;
    }

    public void setChannelParent(String channelParent) {
        this.channelParent = channelParent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDateReset() {
        return dateReset;
    }

    public void setDateReset(String dateReset) {
        this.dateReset = dateReset;
    }

    public String getMaxDebit() {
        return maxDebit;
    }

    public void setMaxDebit(String maxDebit) {
        this.maxDebit = maxDebit;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }
}
