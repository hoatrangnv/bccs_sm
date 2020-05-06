/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import org.apache.struts.action.ActionForm;

/**
 *
 * @author kdvt_phuoctv
 */
public class StockOwnerTmpForm extends ActionForm {

    private Long stockId;
    private String shopCode;
    private String shopName;
    private String staffCode;
    private String staffName;
    private Long channelTypeId;
    private String maxDebit;

    /*Dung cho tim kiem*/
    private String prevShopCode;
    private String prevStaffCode;
    private Long prevChannelTypeId;
    private String prevMaxDebit;

    /*Dung cho chuc nang import file*/
    private String clientFileName;
    private String serverFileName;

    public void resetForm() {
        this.stockId = null;
        this.shopCode = "";
        this.shopName = "";
        this.staffCode = "";
        this.staffName = "";
        this.channelTypeId = null;
        this.maxDebit = "";
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public String getMaxDebit() {
        return maxDebit;
    }

    public void setMaxDebit(String maxDebit) {
        this.maxDebit = maxDebit;
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

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public Long getPrevChannelTypeId() {
        return prevChannelTypeId;
    }

    public void setPrevChannelTypeId(Long prevChannelTypeId) {
        this.prevChannelTypeId = prevChannelTypeId;
    }

    public String getPrevMaxDebit() {
        return prevMaxDebit;
    }

    public void setPrevMaxDebit(String prevMaxDebit) {
        this.prevMaxDebit = prevMaxDebit;
    }

    public String getPrevShopCode() {
        return prevShopCode;
    }

    public void setPrevShopCode(String prevShopCode) {
        this.prevShopCode = prevShopCode;
    }

    public String getPrevStaffCode() {
        return prevStaffCode;
    }

    public void setPrevStaffCode(String prevStaffCode) {
        this.prevStaffCode = prevStaffCode;
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
}
