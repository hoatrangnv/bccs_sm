/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

/**
 *
 * @author User
 */
public class ReportChangeGoodForm {
    //Dieu kien tim kiem

    private String shopCode;
    private String shopName;
    private Long telecomServiceId;
    private Long stockModelId;
    private Long stateId;
    private Long reportType;
    private String fromDate;
    private String toDate;
    //0: Bao cao tong hop
    //1: Bao cao chi tiet
    private Long reportDetail = 0L;
    private Long shopId;
    private Long stockTypeId;
    private boolean channelShop;
    private boolean channelCTV;
    private boolean channelAgent;
    //trung dh3
    private Long subChannelTypeId;

    public ReportChangeGoodForm() {
    }

    public Long getSubChannelTypeId() {
        return subChannelTypeId;
    }

    public void setSubChannelTypeId(Long subChannelTypeId) {
        this.subChannelTypeId = subChannelTypeId;
    }

    public Long getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public Long getReportDetail() {
        return reportDetail;
    }

    public void setReportDetail(Long reportDetail) {
        this.reportDetail = reportDetail;
    }

    public Long getReportType() {
        return reportType;
    }

    public void setReportType(Long reportType) {
        this.reportType = reportType;
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

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public Long getTelecomServiceId() {
        return telecomServiceId;
    }

    public void setTelecomServiceId(Long telecomServiceId) {
        this.telecomServiceId = telecomServiceId;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public boolean isChannelAgent() {
        return channelAgent;
    }

    public void setChannelAgent(boolean channelAgent) {
        this.channelAgent = channelAgent;
    }

    public boolean isChannelCTV() {
        return channelCTV;
    }

    public void setChannelCTV(boolean channelCTV) {
        this.channelCTV = channelCTV;
    }

    public boolean isChannelShop() {
        return channelShop;
    }

    public void setChannelShop(boolean channelShop) {
        this.channelShop = channelShop;
    }
}
